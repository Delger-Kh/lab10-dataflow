package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Lab 10, Part B4: data-flow tests for bonus().
 * Line numbers are the line numbers in DataFlowTargets.java (31-56).
 * Pair IDs (B1..B35) match the DU table in README.md.
 *
 * Stage 1: ALL-DEFS (minimum 2 tests).
 */
class BonusTest {

    // ---------- Stage 1: all-defs ----------

    @Test
    @DisplayName("ad1 {1,0,1,1,1,1,1}, thr=10 -> 3: every def except score@51 reaches a use "
            + "(present 6 < 10 -> 50F, so score@49 reaches 54; streak of 5 -> 53T)")
    void ad1_mixedStreak() {
        assertEquals(3, DataFlowTargets.bonus(new int[]{1, 0, 1, 1, 1, 1, 1}, 10));
    }

    @Test
    @DisplayName("ad2 {1}, thr=1 -> 5: score@51 reaches 56 (50T, 53F)")
    void ad2_oneDayMeetsThreshold() {
        assertEquals(5, DataFlowTargets.bonus(new int[]{1}, 1));
    }
}
