package me.b1n4ry.fwep.util

import me.b1n4ry.fwep.instance
import org.bukkit.*
import org.bukkit.entity.LivingEntity
import org.bukkit.util.Vector
import java.util.*

data class ForestBolt(var location: Location, var color: Color, var size: Float, val owner: UUID, val target: LivingEntity?, val destroyAfter: Long, var velocity: Vector, val damage: Double) {
    val uuid = UUID.randomUUID()
    init {
        instance.projectiles.add(this)
        object : RepeatingTask(instance, 0, 0) {
            override fun run() {
                location.world.spawnParticle(Particle.REDSTONE,location,1,Particle.DustOptions(color,size))
                location.world.spawnParticle(Particle.REDSTONE,location.clone().add(velocity.clone().multiply(-0.5)),1,Particle.DustOptions(color,size))
                location.world.spawnParticle(Particle.REDSTONE,location.clone().add(velocity.clone().multiply(-0.75)),1,Particle.DustOptions(color,size))
                location.world.spawnParticle(Particle.REDSTONE,location.clone().add(velocity.clone().multiply(-1)),1,Particle.DustOptions(color,size))
                location.world.spawnParticle(Particle.REDSTONE,location.clone().add(velocity.clone().multiply(-0.25)),1,Particle.DustOptions(color,size))

                //HIT ENTITY
                if(location.getNearbyLivingEntities(0.25).isNotEmpty()) {
                    val ent = location.getNearbyLivingEntities(0.25).first()
                    val damager = Bukkit.getServer().getEntity(owner)
                    if(ent != damager){
                        ent.maximumNoDamageTicks = 0
                        ent.noDamageTicks = 0
                        ent.damage(damage, damager)
                        ent.maximumNoDamageTicks = 20
                        this@ForestBolt.remove()
                    }
                    //location.getNearbyLivingEntities(0.25).first().sendMessage("$uuid is dead")
                }

                // PHYSICS
                if(target != null){
                    val projectile = this@ForestBolt
                    if (projectile in instance.projectiles && !target.isDead) {
                        projectile.location.add(
                            target.location.clone().add(0.0, 1.0, 0.0).add(projectile.location.clone().multiply(-1.0))
                                .multiply(0.05)
                        )
                        projectile.velocity.add(
                            (target.location.clone().add(0.0, 1.0, 0.0).add(projectile.location.clone().multiply(-1.0))
                                .multiply(0.05)).toVector()
                        )
                    } else projectile.remove()
                }

                location.add(velocity)
                if(!instance.projectiles.contains(this@ForestBolt)) cancel()
            }
        }
        //SELFDESTRUCTION
        if(destroyAfter>=0) Bukkit.getScheduler().runTaskLater(instance, Runnable {
            if(this@ForestBolt in instance.projectiles) this@ForestBolt.remove()
        },destroyAfter)
    }

    fun remove() {
        instance.projectiles.remove(this)
        location.world.spawnParticle(Particle.EXPLOSION_NORMAL,location,3,0.0,0.0,0.0,0.1)
        location.world.playSound(location, Sound.ENTITY_ENDER_EYE_DEATH,1f,2f)
    }
}