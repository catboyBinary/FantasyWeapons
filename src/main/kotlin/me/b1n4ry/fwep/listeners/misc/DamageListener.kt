package me.b1n4ry.fwep.listeners.misc

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import kotlin.math.roundToInt

object DamageListener : Listener {
    @EventHandler
    fun checkdmg(e: EntityDamageByEntityEvent) {
        if (e.damager is Player) e.damager.sendMessage("Your damage: ${(e.damage*10000).roundToInt().toDouble()/10000}")
        if (e.entity is Player) e.entity.sendMessage("Enemy's damage: ${(e.finalDamage*10000).roundToInt().toDouble()/10000}")
    }
}