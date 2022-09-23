package me.b1n4ry.fwep.listeners.weapons

import me.b1n4ry.fwep.instance
import me.b1n4ry.fwep.util.ForestBolt
import me.b1n4ry.fwep.util.Util.hasCooldown
import me.b1n4ry.fwep.util.Util.setCooldown
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
            if(!shooter.hasCooldown(id)) {
                shooter.setCooldown(id, 1.0)
                for (i in 0..3) {
                    ForestBolt(ent.location.add(0.0, 3.0, 0.0), Color.LIME, 0.75f, shooter.uniqueId, ent, 80)
                }
            }
        }
    }
}