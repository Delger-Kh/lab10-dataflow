package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Lab 10, Part A: all-DU-pairs coverage for gcd().
 * Line numbers are the line numbers in DataFlowTargets.java
 * (13 = def x,y; 15 = while; 16, 17, 18; 20 = return).
 * Pair numbers (#1..#13) match the DU table in README.md.
 */
class GcdTest {

    @Test
    @DisplayName("T1 gcd(12,8)=4, 2 iterations: #1 x13-16, #3 x17-16, #4 x17-20, "
            + "#5 y13-15T, #7 y13-16, #8 y13-17, #9 y18-15T, #10 y18-15F, "
            + "#11 y18-16, #12 y18-17, #13 tmp16-18")
    void t1_gcd_12_8_twoIterations() {
        assertEquals(4, DataFlowTargets.gcd(12, 8));
    }

    @Test
    @DisplayName("T2 gcd(7,0)=7, loop never runs: #2 x13-20, #6 y13-15F")
    void t2_gcd_7_0_noIteration() {
        assertEquals(7, DataFlowTargets.gcd(7, 0));
    }

    @Test
    @DisplayName("T3 gcd(0,5)=5, 1 iteration (extra check): #1, #4, #5, #7, #8, #10, #13")
    void t3_gcd_0_5_oneIteration() {
        assertEquals(5, DataFlowTargets.gcd(0, 5));
    }
}
