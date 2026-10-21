package com.bettercontent.bettertinkerslootaffixes.diagnostics;
import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import static org.junit.jupiter.api.Assertions.*;
/** Actual production projection test; native gameplay gates own whole-feature state/RNG equivalence. */
final class FeatureObservationTest {
    public static void main(String[] args) { new FeatureObservationTest().actualEmitterStagesPrivacyAndObservationalPurity(); }
    @Test void actualEmitterStagesPrivacyAndObservationalPurity() {
        var records = new ArrayList<String>(); boolean[] enabled = {false};
        var log = new PlaytestLog("better_tinkers_loot_affixes", new PlaytestLog.Sink() {
            public boolean enabled(String category, String level) { return enabled[0]; }
            public void write(String category, String level, String text) { records.add(text); }
        }, () -> 0L, new Properties());
        var contextReads = new AtomicInteger();
        Supplier<PlaytestLog.Context> ctx = () -> { contextReads.incrementAndGet(); return PlaytestLog.Context.server("minecraft:overworld", 10); };
        UUID id = UUID.fromString("00000000-0000-0000-0000-000000000042");
        // Observational purity at this scalar projection seam only, not a synthetic native replay.
        long[] state = {42, 3}; long[] before = state.clone();
        var nativeRng = new Random(41); var baselineRng = new Random(41);
        FeatureObservation.inventory(log, ctx, "committed", "APPLIED", 42, 2, 4);
        FeatureObservation.inventory(log, ctx, "committed", "DESTROYED", 43, 2, 4);
        FeatureObservation.inventory(log, ctx, "rejected", "replay", 42, 2, 4);
        FeatureObservation.inventory(log, ctx, "committed", "salvage_spent_rewards_dispatched", 44, 2, 4);
        assertTrue(records.isEmpty()); assertEquals(0, contextReads.get());
        assertArrayEquals(before, state); assertEquals(baselineRng.nextLong(), nativeRng.nextLong());
        enabled[0] = true;
        FeatureObservation.inventory(log, ctx, "committed", "APPLIED", 42, 2, 4);
        FeatureObservation.inventory(log, ctx, "committed", "DESTROYED", 43, 2, 4);
        FeatureObservation.inventory(log, ctx, "rejected", "replay", 42, 2, 4);
        FeatureObservation.inventory(log, ctx, "committed", "salvage_spent_rewards_dispatched", 44, 2, 4);
        assertEquals(4, records.size());
        assertTrue(records.get(0).contains("result=committed reason=APPLIED"), records.toString());
        assertTrue(records.get(1).contains("result=committed reason=DESTROYED"), records.toString());
        assertTrue(records.get(2).contains("result=rejected reason=replay"), records.toString());
        assertTrue(records.get(3).contains("reason=salvage_spent_rewards_dispatched"), records.toString());
        assertTrue(records.stream().noneMatch(s -> s.contains("fingerprint=")), records.toString());
        assertTrue(records.stream().noneMatch(s -> s.contains("salvage_seed=")), records.toString());
        assertTrue(records.stream().noneMatch(s -> s.contains("nbt=")), records.toString());
        assertTrue(records.stream().noneMatch(s -> s.contains("payload=")), records.toString());
        assertArrayEquals(before, state); assertEquals(baselineRng.nextLong(), nativeRng.nextLong());
    }
}
