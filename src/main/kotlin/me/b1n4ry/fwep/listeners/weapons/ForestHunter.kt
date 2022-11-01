package me.b1n4ry.fwep.listeners.weapons

import me.b1n4ry.fwep.instance
import me.b1n4ry.fwep.util.ForestBolt
import me.b1n4ry.fwep.util.RepeatingTask
import me.b1n4ry.fwep.util.Util.hasCooldown
import me.b1n4ry.fwep.util.Util.openWeapon
import me.b1n4ry.fwep.util.Util.setCooldown
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.util.Vector
import java.util.*

object ForestHunter {
    private const val id = "foresthunter"

    fun shootAction(e: EntityShootBowEvent) {
        e.setConsumeItem(false)
        (e.entity as Player).updateInventory()
        if(e.force == 1.0f) {
            e.projectile.setMetadata(id, FixedMetadataValue(instance, true))
            e.projectile.velocity = e.projectile.velocity.clone().add(e.entity.location.direction)
            object : RepeatingTask(instance, 2, 0) {
                override fun run() {
                    if (e.projectile.isValid && !e.projectile.isOnGround) {
                        e.projectile.world.spawnParticle(
                            Particle.REDSTONE,
                            e.projectile.location,
                            1,
                            Particle.DustOptions(Color.LIME, 1.5f)
                        )
                        e.projectile.world.spawnParticle(
                            Particle.REDSTONE,
                            e.projectile.location.clone().add(e.projectile.velocity.clone().multiply(-1)),
                            1,
                            Particle.DustOptions(Color.LIME, 1.5f)
                        )
                        e.projectile.world.spawnParticle(
                            Particle.REDSTONE,
                            e.projectile.location.clone().add(e.projectile.velocity.clone().multiply(-0.5)),
                            1,
                            Particle.DustOptions(Color.LIME, 1.5f)
                        )
                        e.projectile.world.spawnParticle(
                            Particle.REDSTONE,
                            e.projectile.location.clone().add(e.projectile.velocity.clone().multiply(-0.25)),
                            1,
                            Particle.DustOptions(Color.LIME, 1.5f)
                        )
                        e.projectile.world.spawnParticle(
                            Particle.REDSTONE,
                            e.projectile.location.clone().add(e.projectile.velocity.clone().multiply(-0.75)),
                            1,
                            Particle.DustOptions(Color.LIME, 1.5f)
                        )
                    } else cancel()
                }

            }
        }
    }

    fun arrowHitAction(e: ProjectileHitEvent) {
        if (e.hitEntity != null && e.hitEntity is LivingEntity) {
            val ent = e.hitEntity as LivingEntity
            val shooter = e.entity.shooter as LivingEntity
            if (!shooter.hasCooldown(id)) {
                shooter.setCooldown(id, 1.0)
                for (i in 0..3) {
                    val r = Random()
                    val x: Double = r.nextDouble()-0.5
                    val z: Double = r.nextDouble()-0.5
                    val y: Double = r.nextDouble()*0.35+0.4
                    val velocity = Vector(x,y,z)
                    ForestBolt(ent.location.add(0.0, 3.0, 0.0), Color.LIME, 0.75f, shooter.uniqueId, ent, 80, velocity, 2.5)
                }
            }
        }
    }

    fun rightClickAction(e: PlayerInteractEvent) {
        if(e.player.isSneaking) {
            e.player.openWeapon()
            e.isCancelled = true
            return
        }
        Bukkit.getScheduler().runTaskLater(instance, Runnable {
            if (e.player.handRaisedTime>=19) e.player.playSound(e.player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.75f, 1.5f)
        }, 20L)
    }
}