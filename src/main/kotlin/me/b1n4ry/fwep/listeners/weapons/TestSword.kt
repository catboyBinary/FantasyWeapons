package me.b1n4ry.fwep.listeners.weapons

import me.b1n4ry.fwep.util.Util.key
import org.bukkit.Particle
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.persistence.PersistentDataType

object TestSword {
    fun rightClickAction(e: PlayerInteractEvent) {
        if(e.player.hasCooldown(e.material)) return
        e.player.setCooldown(e.material, 30)

        val loc = e.player.eyeLocation.add(e.player.location.direction.multiply(5))
        e.player.world.spawnParticle(Particle.DRAGON_BREATH, loc, 10, 0.0,0.0,0.0,0.01)
        e.player.world.spawnParticle(Particle.CLOUD, loc, 10, 0.0,0.0,0.0,0.2)
        e.player.velocity = e.player.velocity.add(e.player.location.direction)
    }
}
