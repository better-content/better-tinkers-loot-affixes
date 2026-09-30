package com.bettercontent.bettertinkerslootaffixes.api.event
import net.minecraft.server.level.ServerPlayer
import net.minecraftforge.eventbus.api.Event
/** Notification after the validated mutation consumed currency and committed its actual result. */
class RuinousFluxEvent(val player: ServerPlayer, val operation: Long, val destroyed: Boolean, val part: String) : Event()
