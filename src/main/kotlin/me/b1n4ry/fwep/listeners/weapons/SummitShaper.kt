package me.b1n4ry.fwep.listeners.weapons

import me.b1n4ry.fwep.instance
import me.b1n4ry.fwep.util.Items
import me.b1n4ry.fwep.util.Util.getCooldown
import me.b1n4ry.fwep.util.Util.hasCooldown
import me.b1n4ry.fwep.util.Util.setCooldown
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.*
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object SummitShaper {
    private const val id = "summitshaper"
    private const val cooldown = 15.0

    fun rightClickAction(e: PlayerInteractEvent) {
        if(e.player.hasCooldown(id)) { e.player.sendActionBar(Component.text("Cooldown: ${e.player.getCooldown(id)}s"))
            return }
        e.player.setCooldown(id, cooldown)
        e.player.swingMainHand()

        Bukkit.getScheduler().runTaskLater(instance, Runnable { e.player.playSound(e.player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f,1f)
            e.player.sendActionBar(Component.text("Summit Shaper is ready!", NamedTextColor.GREEN)) }, (cooldown *20).toLong())

        val loc = e.player.location
        e.player.world.playSound(loc, Sound.BLOCK_END_PORTAL_FRAME_FILL, SoundCategory.MASTER, 1f, 1f)
        e.player.world.playSound(loc, Sound.BLOCK_END_PORTAL_FRAME_FILL, SoundCategory.MASTER, 1f, 2f)
        e.player.world.playSound(loc, Sound.ITEM_TOTEM_USE, SoundCategory.MASTER, 0.5f, 2f)
        val data = Particle.DustTransition(Color.fromRGB(255,198,66),Color.fromRGB(56,47,28),3f)
        for(a in -10..10) {
            val location = loc.clone().add(0.0,0.5,0.0).add(loc.clone().direction.setY(0).rotateAroundY(a/10.0).multiply(5))
            e.player.world.spawnParticle(Particle.DUST_COLOR_TRANSITION,location, 3,0.0,0.0, 0.0, 0.0,data)
            e.player.world.spawnParticle(Particle.EXPLOSION_NORMAL,location,1,0.0,0.0,0.0,0.1)
            location.getNearbyLivingEntities(1.0).forEach {
                if (it != e.player) {
                    it.velocity = it.velocity.add(loc.clone().direction.rotateAroundY(a/10.0).multiply(0.15).setY(0.075))
                    it.damage(2.5, e.player)
                }
            }
        }
        e.player.addPotionEffect(PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,100,0,false,false,true))
        e.player.velocity = e.player.velocity.add(loc.direction.multiply(-0.75).setY(0.5))
    }
}
