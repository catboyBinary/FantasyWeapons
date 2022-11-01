package me.b1n4ry.fwep.listeners.weapons

import me.b1n4ry.fwep.instance
import me.b1n4ry.fwep.util.ForestBolt
import me.b1n4ry.fwep.util.Util.getCooldown
import me.b1n4ry.fwep.util.Util.hasCooldown
import me.b1n4ry.fwep.util.Util.openWeapon
import me.b1n4ry.fwep.util.Util.setCooldown
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Sound
import org.bukkit.event.player.PlayerInteractEvent

object ExampleSword {
    private const val id = "examplesword"
    private const val cooldown = 0.05

    fun rightClickAction(e: PlayerInteractEvent) {
        if(e.player.isSneaking) {
            e.player.openWeapon()
            return
        }

        if(e.player.hasCooldown(id)) { e.player.sendActionBar(Component.text("Cooldown: ${e.player.getCooldown(id)}s"))
            return }
        e.player.setCooldown(id, cooldown)
        e.player.swingMainHand()

        Bukkit.getScheduler().runTaskLater(instance, Runnable { e.player.playSound(e.player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f,1f)
            e.player.sendActionBar(Component.text("Example Sword is ready!", NamedTextColor.GREEN)) }, (cooldown*20).toLong())


        ForestBolt(e.player.eyeLocation, Color.FUCHSIA, 1f, e.player.uniqueId, null, 20, e.player.location.direction.multiply(0.5),2.0)
        // CODE
    }
}