package com.bettercontent.bettertinkerslootaffixes.diagnostics;
import java.util.function.Supplier;
/** Pure owner-specific projection of already computed native outcomes; no gameplay writes or RNG. */
public final class FeatureObservation {
    private FeatureObservation() {}
    public static void inventory(PlaytestLog log, Supplier<PlaytestLog.Context> context, String result, String reason, long request, int menu, int slot) {
        if (!log.enabled("transaction")) return;
        log.debug("transaction", "affix.inventory", context.get(), result, reason, () -> PlaytestLog.fields("request", request, "menu", menu, "target_slot", slot));
    }
    public static void decision(PlaytestLog log, String category, String event, String result, String reason) {
        if (!log.enabled(category)) return;
        log.event(category, event, "server", result, reason, () -> "");
    }
}
