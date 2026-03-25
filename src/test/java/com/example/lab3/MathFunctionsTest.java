package com.example.lab3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MathFunctionsTest {

    private static final double DELTA = 1e-9;

    @Test
    void sinh_zero_returnsZero() {
        assertEquals(0.0, MathFunctions.sinh(0), DELTA);
    }

    @Test
    void sinh_one_returnsExpectedValue() {
        assertEquals(Math.sinh(1), MathFunctions.sinh(1), DELTA);
    }

    @Test
    void sinh_negativeOne_returnsNegativeSinhOne() {
        assertEquals(-Math.sinh(1), MathFunctions.sinh(-1), DELTA);
    }

    @Test
    void sinh_isOddFunction() {
        assertEquals(MathFunctions.sinh(-2), -MathFunctions.sinh(2), DELTA);
    }

    @Test
    void sinh_largeValue_returnsLargePositive() {
        assertTrue(MathFunctions.sinh(10) > 11013.0);
    }

    @Test
    void cosh_zero_returnsOne() {
        assertEquals(1.0, MathFunctions.cosh(0), DELTA);
    }

    @Test
    void cosh_one_returnsExpectedValue() {
        assertEquals(Math.cosh(1), MathFunctions.cosh(1), DELTA);
    }

    @Test
    void cosh_isEvenFunction() {
        assertEquals(MathFunctions.cosh(-3), MathFunctions.cosh(3), DELTA);
    }

    @Test
    void cosh_alwaysGreaterOrEqualOne() {
        assertTrue(MathFunctions.cosh(5) >= 1.0);
    }

    @Test
    void cosh_largeValue_returnsLargePositive() {
        assertTrue(MathFunctions.cosh(10) > 11013.0);
    }

    @Test
    void tanh_zero_returnsZero() {
        assertEquals(0.0, MathFunctions.tanh(0), DELTA);
    }

    @Test
    void tanh_one_returnsExpectedValue() {
        assertEquals(Math.tanh(1), MathFunctions.tanh(1), DELTA);
    }

    @Test
    void tanh_largeNegative_approachesMinusOne() {
        assertTrue(MathFunctions.tanh(-1000) < -0.999999);
    }

    @Test
    void tanh_isOddFunction() {
        assertEquals(MathFunctions.tanh(-2), -MathFunctions.tanh(2), DELTA);
    }

    @Test
    void tanh_negativeInfinity_returnsMinusOne() {
        assertTrue(Math.abs(MathFunctions.tanh(-1000) + 1) < 1e-6);
    }
}
