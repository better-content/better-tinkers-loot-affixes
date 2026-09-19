package com.bettercontent.tinkersconstructaffixes

import net.minecraft.nbt.CompoundTag
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class AffixCraftingTest {
    private fun affix(id: String) = CompoundTag().apply { putString("id", id); putInt("tier", 1) }

    @Test
    fun `shearing draws once and removes only selected affix`() {
        val original = listOf(affix("first"), affix("second"), affix("third"))
        var draws = 0
        val survivors = AffixCrafting.removeOneAffix(original) { size ->
            draws++
            assertEquals(3, size)
            1
        }

        assertEquals(1, draws)
        assertEquals(listOf("first", "third"), survivors.map { it.getString("id") })
        assertEquals(listOf("first", "second", "third"), original.map { it.getString("id") })
    }

    @Test
    fun `invalid random index does not silently mutate affixes`() {
        assertFailsWith<IllegalArgumentException> {
            AffixCrafting.removeOneAffix(listOf(affix("only"))) { 1 }
        }
    }
}
