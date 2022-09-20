package com.bidpoint.backend.recommendation.algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class MatrixFactorization {
    Double[][] R;
    Double[][] P;
    Double[][] Q;
    Double[] b_u;
    Double[] b_i;
    Double b;
    int num_users;
    int num_items;
    int K;
    double alpha;
    double beta;
    int iterations;
    ArrayList<Double[]> samples;

    public MatrixFactorization(Double[][] r, int num_users, int num_items, int k, Double alpha, Double beta, int iterations) {
        R = r;
        this.num_users = num_users;
        this.num_items = num_items;
        K = k;
        this.alpha = alpha;
        this.beta = beta;
        this.iterations = iterations;
        this.samples = new ArrayList<>();
    }

    public ArrayList<Double> train () {
        this.P = new Double[this.num_users][this.K];
        for (int i=0; i< this.num_users; i++)
            for (int j=0; j< this.K; j++)
                this.P[i][j] = ThreadLocalRandom.current().nextDouble(0.0d, 1.0d);


        this.Q = new Double[this.num_items][this.K];
        for (int i=0; i< this.num_items; i++)
            for (int j=0; j< this.K; j++)
                this.Q[i][j] =  ThreadLocalRandom.current().nextDouble(0.0d, 1.0d);


        this.b_u = new Double[this.num_users];
        Arrays.fill(b_u,0.0d);
        this.b_i = new Double[this.num_items];
        Arrays.fill(b_i,0.0d);

        this.b = mean(R);

        for (int i=0; i< this.num_users; i++)
            for (int j=0; j< this.num_items; j++)
                if(R[i][j] > 0)
                    samples.add(new Double[]{(double) i, (double) j, R[i][j]});

        ArrayList<Double> training_process = new ArrayList<>();
        for(int i = 0; i< iterations; i++){
            Collections.shuffle(samples);
            sgd();
            Double mse = mse();
            training_process.add(mse);
            log.info("Iteration: {} ; error = {}", i + 1, mse);
        }

        return training_process;
    }

    private Double mse(){
        IndexesList R_0 = nonzero(R);
        Double[][] predicted = fullMatrix();
        double error = 0.0d;
        for(int i = 0; i < R_0.getRowIndexes().size(); i++)
            error += Math.pow(
                    R[R_0.getRowIndexesElement(i)][R_0.getColumnIndexesElement(i)] - predicted[R_0.getRowIndexesElement(i)][R_0.getColumnIndexesElement(i)],
                    2
            );

        return Math.sqrt(error);
    }

    private void sgd() {
        this.samples.forEach(sample ->{
            int i = sample[0].intValue();
            int j = sample[1].intValue();
            int r = sample[2].intValue();

            Double prediction = this.getRating(i, j);
            double error = (r - prediction);

            this.b_u[i] += this.alpha * (error - this.beta * this.b_u[i]);
            this.b_i[j] += this.alpha * (error - this.beta * this.b_i[j]);

            Double[] P_i = this.P[i];

            this.P[i] = addition(multiplication(subtract(multiplication(Q[j], error), multiplication(P[i],beta)),alpha), P[i]);
            this.Q[j] = addition(multiplication(subtract(multiplication(P_i, error), multiplication(Q[j], beta)),alpha), Q[j]);
        });

    }

    private Double getRating(int i, int j){
        return b + b_u[i] + b_i[j] + dotProduct(P[i], Q[j]);

    }

    public Double[][] fullMatrix(){
        return additionWithTransposeQ(b , b_u , b_i , P, Q);
    }

    private double mean(Double[][] array) {
        ArrayList<Double> sum = new ArrayList<>();
        for (Double[] row : array)
            for (Double column : row)
                if(column != 0)
                    sum.add(column);
        return sum.stream().mapToDouble(Double::doubleValue).sum() / sum.size();
    }

    private Double[] subtract(Double[] x, Double[] y){
        Double[] res = new Double[x.length];
        for(int i=0; i < x.length; i++){
            res[i] = x[i] - y[i];
        }
        return res;
    }

    private Double[] addition(Double[] x, Double[] y){
        Double[] res = new Double[x.length];
        for(int i = 0; i < x.length; i++)
            res[i] = x[i] + y[i];
        return res;
    }

    public Double[][] addition(Double number , Double [] b_u , Double [] b_i , Double[][] array){
        Double[][] res = new Double[array.length][array[0].length];
        for(int user = 0; user < array.length; user++){
            for(int item = 0; item < array[user].length; item++){
                res[user][item] = array[user][item] + b_u[user] + b_i[item] + number;
            }
        }
        return res;
    }

    public Double[][] addition(Double number , Double [] b_u , Double [] b_i , Double[][] P, Double[][] Q){
        Q = transpose(Q);
        Double[][] res = new Double[P.length][Q[0].length];
        for (Double[] row: res)
            Arrays.fill(row, 0.0d);
        for(int user = 0; user < P.length; user++){
            for(int item = 0; item < Q[0].length; item++){
                for(int k = 0; k < Q.length; k++)
                    res[user][item] = res[user][item] + P[user][k] * Q[k][item];
                res[user][item] += b_u[user] + b_i[item] + number;
            }
        }
        return res;
    }

    public Double[][] additionWithTransposeQ(Double number , Double [] b_u , Double [] b_i , Double[][] P, Double[][] Q){
        Double[][] res = new Double[P.length][Q.length];
        for (Double[] row: res)
            Arrays.fill(row, 0.0d);
        for(int user = 0; user < P.length; user++){
            for(int item = 0; item < Q.length; item++){
                for(int k = 0; k < Q[0].length; k++)
                    res[user][item] = res[user][item] + P[user][k] * Q[item][k];
                res[user][item] += b_u[user] + b_i[item] + number;
            }
        }
        return res;
    }

    private Double[][] transpose(Double[][] array) {
        Double[][] transposedArray = new Double[array[0].length][array.length];
        for(int i = 0;i < array[0].length;i++){
            for(int j = 0; j < array.length;j++){
                transposedArray[i][j] = array[j][i];
            }
        }
        return transposedArray;
    }

    private Double[][] dotProduct(Double[][] P, Double[][] Q){
        Double[][] sum = new Double[P.length][Q[0].length];
        for (Double[] row: sum)
            Arrays.fill(row, 0.0d);
        for(int i = 0; i < P.length; i++)
            for(int j = 0; j < Q[0].length; j++)
                for(int k = 0; k < Q.length; k++)
                    sum[i][j] = sum[i][j] + P[i][k] * Q[k][j];
        return sum;
    }

    private Double dotProduct(Double[] x, Double[] y){
        double sum = 0;
        for (int i = 0; i < x.length; i++)
            sum += x[i] * y[i];
        return sum;
    }


    private Double[] multiplication(Double[] array, double number){
        Double[] newArray = new Double[array.length];
        for(int i = 0; i < array.length; i++)
            newArray[i] = array[i] * number;
        return newArray;
    }

    @AllArgsConstructor
    @Getter
    private static class IndexesList {
        ArrayList<Integer> rowIndexes;
        ArrayList<Integer> columnIndexes;

        public Integer getRowIndexesElement(int i){
            return rowIndexes.get(i);
        }
        public Integer getColumnIndexesElement(int i){
            return columnIndexes.get(i);
        }
    }
    private IndexesList nonzero(Double[][] array){
        ArrayList<Integer> rowIndexes = new ArrayList<Integer>();
        ArrayList<Integer> columnIndexes = new ArrayList<Integer>();
        for(int i = 0; i< array.length; i++){
            for(int j = 0; j < array[i].length; j++){
                if(array[i][j] != 0){
                    rowIndexes.add(i);
                    columnIndexes.add(j);
                }
            }
        }

        return new IndexesList(rowIndexes, columnIndexes);
    }
}
