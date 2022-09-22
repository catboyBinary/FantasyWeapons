package me.b1n4ry.fwep.util

import me.b1n4ry.fwep.instance
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import org.bukkit.util.Vector
import java.util.*

data class CustomProjectile(var location: Location, var color: Color, var size: Float, val owner: UUID) {
    val uuid = UUID.randomUUID()
    val r = Random()
    val x: Double = r.nextDouble()-0.5
    val z: Double = r.nextDouble()-0.5
    val y: Double = r.nextDouble()*0.25+0.25
    var velocity = Vector(x,y,z)
    var selfdestruct : BukkitTask
    init {
        instance.projectiles.add(this)
        object : RepeatingTask(instance, 0, 0) {
            override fun run() {
                location.world.spawnParticle(Particle.REDSTONE,location,1,Particle.DustOptions(color,size))

                // PHYSICS
                //if((location.clone().add(velocity).block.isPassable)) {
                //    velocity = velocity.add(Vector(0.0,-0.011,0.0))
                //} else {
                //
                //}

                //HIT ENTITY
                if(location.getNearbyLivingEntities(0.25).isNotEmpty()) {
                    location.getNearbyLivingEntities(0.25).first().maximumNoDamageTicks = 0
                    location.getNearbyLivingEntities(0.25).first().noDamageTicks=0
                    location.getNearbyLivingEntities(0.25).first().damage(2.5, Bukkit.getServer().getEntity(owner))
                    location.getNearbyLivingEntities(0.25).first().maximumNoDamageTicks = 20
                    this@CustomProjectile.remove()
                    //location.getNearbyLivingEntities(0.25).first().sendMessage("$uuid is dead")
                }

                location.add(velocity)
                if(!instance.projectiles.contains(this@CustomProjectile)) cancel()
            }
        }
        //SELFDESTRUCTION
        selfdestruct = Bukkit.getScheduler().runTaskLater(instance, Runnable {
            this@CustomProjectile.remove()
        },80)
    }

    fun remove() {
        selfdestruct.cancel()
        instance.projectiles.remove(this)
        location.world.spawnParticle(Particle.EXPLOSION_NORMAL,location,3,0.0,0.0,0.0,0.1)
    }
}