package com.bettercontent.bettertinkerslootaffixes

import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.Mob

/**
 * Affix rewards intentionally have their own attribution policy. A direct player source covers
 * normal melee, projectiles, and player-attributed spells; it does not inherit a pet owner or a
 * delayed kill-credit recipient from the spirit economy.
 */
internal object AffixKillEligibility {
    const val SPAWNER_ORIGIN_TAG = "better_tinkers_loot_affixes_spawner_origin"

    fun markSpawnerOrigin(mob: Mob) {
        mob.persistentData.putBoolean(SPAWNER_ORIGIN_TAG, true)
    }

    fun isEligible(mob: Mob, directPlayer: ServerPlayer?): Boolean =
        isEligible(directPlayer != null, mob.persistentData.getBoolean(SPAWNER_ORIGIN_TAG))

    internal fun isEligible(directPlayer: Boolean, spawnerOrigin: Boolean): Boolean = directPlayer && !spawnerOrigin
}
