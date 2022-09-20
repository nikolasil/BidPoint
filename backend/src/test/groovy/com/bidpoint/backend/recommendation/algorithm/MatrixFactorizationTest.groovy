package com.bidpoint.backend.recommendation.algorithm


import spock.lang.Specification

import java.util.concurrent.ThreadLocalRandom

class MatrixFactorizationTest extends Specification {
    def "test fullMatrix"() {
        given:
        Double[][] R = new Double[1000][10000];
        for (int i = 0; i < R.length; i++) {
            for (int j = 0; j < R[i].length; j++) {
                R[i][j] = ThreadLocalRandom.current().nextDouble(0.0d, 1.0d) > 0.5d ? 1.0d : 0.0d;
            }
        }
        MatrixFactorization matrixFactorization = new MatrixFactorization(R,R.length,R[0].length, 2,0.002,0.02,50);
        when:
        ArrayList<Double> training_process = matrixFactorization.train();
        Double[][] res = matrixFactorization.fullMatrix()
        Double b = matrixFactorization.b
        Double[] b_u = matrixFactorization.b_u
        Double[] b_i = matrixFactorization.b_i
        then:
        1==1
    }
}
