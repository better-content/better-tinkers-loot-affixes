package com.bettercontent.tinkersconstructaffixes

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AffixKillEligibilityTest {
    @Test
    fun `spawner origin rejects both part and currency reward path before loot mutation`() {
        assertFalse(AffixKillEligibility.isEligible(directPlayer = true, spawnerOrigin = true))
    }

    @Test
    fun `direct player source is the only non-spawner affix attribution`() {
        assertTrue(AffixKillEligibility.isEligible(directPlayer = true, spawnerOrigin = false))
        assertFalse(AffixKillEligibility.isEligible(directPlayer = false, spawnerOrigin = false))
    }
}
