package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Lab 10, Part B4: data-flow tests for bonus().
 * Line numbers are the line numbers in DataFlowTargets.java (31-56).
 * Pair IDs (B1..B35) match the DU table in README.md.
 *
 * Stage 1: ALL-DEFS (minimum 2 tests).
 * Stage 2: ALL-DU-PAIRS (stage 1 + du1..du5; du6..du8 are extra checks).
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

    // ---------- Stage 2: all-DU-pairs ----------

    @Test
    @DisplayName("du1 {}, thr=0 -> 5: B12 present35-50T, B26 best37-53F")
    void du1_emptyThresholdZero() {
        assertEquals(5, DataFlowTargets.bonus(new int[]{}, 0));
    }

    @Test
    @DisplayName("du2 {}, thr=1 -> 0: B13 present35-50F, B32 score49-56")
    void du2_emptyThresholdOne() {
        assertEquals(0, DataFlowTargets.bonus(new int[]{}, 1));
    }

    @Test
    @DisplayName("du3 {1,1,1,1,1}, thr=5 -> 8: B33 score51-54 (exact threshold + streak of 5)")
    void du3_fiveInRowExactThreshold() {
        assertEquals(8, DataFlowTargets.bonus(new int[]{1, 1, 1, 1, 1}, 5));
    }

    @Test
    @DisplayName("du4 null, thr=0 -> exception: B1 attendance31-32T")
    void du4_nullThrows() {
        assertThrows(IllegalArgumentException.class, () -> DataFlowTargets.bonus(null, 0));
    }

    @Test
    @DisplayName("du5 {}, thr=-1 -> exception: B5 threshold31-32T (attendance not null)")
    void du5_negThresholdThrows() {
        assertThrows(IllegalArgumentException.class, () -> DataFlowTargets.bonus(new int[]{}, -1));
    }

    // ---------- Extra checks (lab test ideas) ----------

    @Test
    @DisplayName("du6 {0,0,0}, thr=1 -> 0: all absent")
    void du6_allAbsent() {
        assertEquals(0, DataFlowTargets.bonus(new int[]{0, 0, 0}, 1));
    }

    @Test
    @DisplayName("du7 {1,1,1,0,1,1,1,1,1,1}, thr=9 -> 8: long streak broken in the middle, then 6 in a row")
    void du7_longStreakBrokenInMiddle() {
        assertEquals(8, DataFlowTargets.bonus(new int[]{1, 1, 1, 0, 1, 1, 1, 1, 1, 1}, 9));
    }

    @Test
    @DisplayName("du8 {1,1,1,1}, thr=4 -> 5: streak of 4 is one short of the +3 bonus")
    void du8_fourInRowNoStreakBonus() {
        assertEquals(5, DataFlowTargets.bonus(new int[]{1, 1, 1, 1}, 4));
    }
    
    // ---------- Part C: kills mutant M2 (best = present) ----------

    @Test
    @DisplayName("m2kill {1,1,0,1,1,1}, thr=10 -> 0: longest streak is 3, but after the absence "
            + "present (5) != streak (3) when line 43 runs, so best = present would wrongly reach 5")
    void m2kill_absenceThenStreakOfThree() {
        assertEquals(0, DataFlowTargets.bonus(new int[]{1, 1, 0, 1, 1, 1}, 10));
    }
}
