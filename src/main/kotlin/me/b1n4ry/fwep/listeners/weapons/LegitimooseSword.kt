package me.b1n4ry.fwep.listeners.weapons

import me.b1n4ry.fwep.instance
import me.b1n4ry.fwep.util.Util.getCooldown
import me.b1n4ry.fwep.util.Util.hasCooldown
import me.b1n4ry.fwep.util.Util.setCooldown
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.event.player.PlayerInteractEvent

object LegitimooseSword {
    private const val id = "legitimoosesword"
    private const val cooldown = 1.5
    fun rightClickAction(e: PlayerInteractEvent) {
        if(e.player.hasCooldown(id)) { e.player.sendActionBar(Component.text("Cooldown: ${e.player.getCooldown(id)}s"))
            return }
        e.player.setCooldown(id, cooldown)
        e.player.swingMainHand()

        Bukkit.getScheduler().runTaskLater(instance, Runnable { e.player.playSound(e.player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f,1f)
            e.player.sendActionBar(Component.text("Legitimoose Sword is ready!", NamedTextColor.GREEN)) }, (cooldown *20).toLong())

        e.player.world.playSound(e.player.location,Sound.BLOCK_AMETHYST_BLOCK_CHIME,1.0f,1.0f)
        e.player.velocity = e.player.velocity.multiply(5)
    }
}