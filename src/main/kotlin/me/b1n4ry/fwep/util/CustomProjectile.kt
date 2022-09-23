package me.b1n4ry.fwep.util

import me.b1n4ry.fwep.instance
import org.bukkit.*
import org.bukkit.entity.LivingEntity
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import org.bukkit.util.Vector
import java.util.*

data class CustomProjectile(var location: Location, var color: Color, var size: Float, val owner: UUID, val target: LivingEntity?, val destroyAfter: Long) {
    val uuid = UUID.randomUUID()
    val r = Random()
    val x: Double = r.nextDouble()-0.5
    val z: Double = r.nextDouble()-0.5
    val y: Double = r.nextDouble()*0.25+0.25
    var velocity = Vector(x,y,z)
    init {
        instance.projectiles.add(this)
        object : RepeatingTask(instance, 0, 0) {
            override fun run() {
                location.world.spawnParticle(Particle.REDSTONE,location,1,Particle.DustOptions(color,size))

                //HIT ENTITY
                if(location.getNearbyLivingEntities(0.25).isNotEmpty()) {
                    val ent = location.getNearbyLivingEntities(0.25).first()
                    ent.maximumNoDamageTicks = 0
                    ent.noDamageTicks=0
                    ent.damage(2.5, Bukkit.getServer().getEntity(owner))
                    ent.maximumNoDamageTicks = 20
                    this@CustomProjectile.remove()
                    //location.getNearbyLivingEntities(0.25).first().sendMessage("$uuid is dead")
                }

                // PHYSICS
                if(target != null){
                    val projectile = this@CustomProjectile
                    if (projectile in instance.projectiles && !target.isDead) {
                        projectile.location.add(
                            target.location.clone().add(0.0, 1.0, 0.0).add(projectile.location.clone().multiply(-1.0))
                                .multiply(0.05)
                        )
                        projectile.velocity.add(
                            (target.location.clone().add(0.0, 1.0, 0.0).add(projectile.location.clone().multiply(-1.0))
                                .multiply(0.025)).toVector()
                        )
                    } else projectile.remove()
                }

                location.add(velocity)
                if(!instance.projectiles.contains(this@CustomProjectile)) cancel()
            }
        }
        //SELFDESTRUCTION
        if(destroyAfter>=0) Bukkit.getScheduler().runTaskLater(instance, Runnable {
            if(this@CustomProjectile in instance.projectiles) this@CustomProjectile.remove()
        },80)
    }

    fun remove() {
        instance.projectiles.remove(this)
        location.world.spawnParticle(Particle.EXPLOSION_NORMAL,location,3,0.0,0.0,0.0,0.1)
        location.world.playSound(location, Sound.ENTITY_ENDER_EYE_DEATH,1f,2f)
    }
}