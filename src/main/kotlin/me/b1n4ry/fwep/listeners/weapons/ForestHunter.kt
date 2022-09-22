package me.b1n4ry.fwep.listeners.weapons

import me.b1n4ry.fwep.instance
import me.b1n4ry.fwep.util.CustomProjectile
import me.b1n4ry.fwep.util.RepeatingTask
import org.bukkit.Color
import org.bukkit.entity.LivingEntity
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.metadata.FixedMetadataValue

object ForestHunter {
    private const val id = "foresthunter"

    fun shootAction(e: EntityShootBowEvent) {
        e.setConsumeItem(false)
        e.projectile.setMetadata(id, FixedMetadataValue(instance,true))
    }

    fun arrowHitAction(e: ProjectileHitEvent) {
        if(e.hitEntity != null && e.hitEntity is LivingEntity) {
            val ent = e.hitEntity as LivingEntity
            val shooter = e.entity.shooter as LivingEntity
            for(i in 0..3){
                val new = CustomProjectile(ent.location.add(0.0, 3.0, 0.0), Color.LIME, 0.75f, shooter.uniqueId)
                object : RepeatingTask(instance, 0, 0) {
                    override fun run() {
                        if (new in instance.projectiles && !ent.isDead) {
                            new.location.add(ent.location.clone().add(0.0, 1.0, 0.0).add(new.location.clone().multiply(-1.0))
                                .multiply(0.05))
                            new.velocity.add(
                                (ent.location.clone().add(0.0, 1.0, 0.0).add(new.location.clone().multiply(-1.0))
                                    .multiply(0.025)).toVector()
                            )
                        } else { new.remove()
                        cancel()}
                    }
                }
                //e.player.sendMessage(new.uuid.toString())
            }
        }
    }
}