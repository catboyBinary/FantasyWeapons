package me.b1n4ry.fwep.listeners.weapons

import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.event.player.PlayerInteractEvent

object LegitimooseSword {
    fun rightClickAction(e: PlayerInteractEvent) {
        if(e.player.hasCooldown(e.material)) return
        e.player.setCooldown(e.material, 20)
        e.player.world.playSound(e.player.location,Sound.BLOCK_AMETHYST_BLOCK_CHIME,1.0f,1.0f)
        e.player.velocity = e.player.velocity.multiply(5)
    }
}