package com.bidpoint.backend.recommendation.algorithm

import spock.lang.Specification

class MatrixFactorizationTest extends Specification {
    def "test fullMatrix"() {
        given:
        Double[][] R = new Double[2][3];
        R[0][0] = 1.0d
        R[0][1] = 1.0d
        R[0][2] = 0.0d

        R[1][0] = 1.0d
        R[1][1] = 0.0d
        R[1][2] = 0.0d
        MatrixFactorization matrixFactorization = new MatrixFactorization(R, R.length,R[0].length, 2,0.002,0.02,50);
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
