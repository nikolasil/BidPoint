package com.bidpoint.backend.recommendation.algorithm

import spock.lang.Specification

class MatrixFactorizationTest extends Specification {
    def "test fullMatrix"() {
        given:
        Double[][] R = [   [5, 0],
                [5, 3],
        ]
//        Double[][] b_u = [[5],[3],[0],[1],[0]]
//        Double[] b_i = [5, 3, 0, 1]
        MatrixFactorization matrixFactorization = new MatrixFactorization(R, R.length, R[0].length, 2,0.01,0.1,20);
        when:
        ArrayList<Double> training_process = matrixFactorization.train();
        Double[][] res = matrixFactorization.fullMatrix()
        Double b = matrixFactorization.b
        Double[] b_u = matrixFactorization.b_u
        Double[] b_i = matrixFactorization.b_i
//        Double[][] res = matrixFactorization.addition(b_u,b_i);
        then:
        1==1

    }
}
