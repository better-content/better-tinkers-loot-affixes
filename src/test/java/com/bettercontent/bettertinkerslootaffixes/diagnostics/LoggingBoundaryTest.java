package com.bettercontent.bettertinkerslootaffixes.diagnostics;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/** Supplementary wiring/privacy invariant, not the actual emitter or native behavior test. */
final class LoggingBoundaryTest {
    @Test void authoritativeFeatureBoundaryAndPrivacyRemainExplicit() throws Exception {
        String source = Files.readString(Path.of("src/main/kotlin/com/bettercontent/bettertinkerslootaffixes/AffixNetwork.kt")) +
            Files.readString(Path.of("src/main/java/com/bettercontent/bettertinkerslootaffixes/diagnostics/FeatureObservation.java"));
        assertTrue(source.contains("FeatureObservation.inventory"), "FeatureObservation.inventory");
        assertTrue(source.contains("affix.inventory"), "affix.inventory");
        assertTrue(source.contains("replay"), "replay");
        assertTrue(source.contains("salvage_spent_rewards_dispatched"), "salvage_spent_rewards_dispatched");
        assertFalse(source.contains("fields(\"nbt\""));
        assertFalse(source.contains("fields(\"payload\""));
        assertFalse(source.contains("fields(\"seed\""));
        assertTrue(source.contains("enabled("));
    }
}
