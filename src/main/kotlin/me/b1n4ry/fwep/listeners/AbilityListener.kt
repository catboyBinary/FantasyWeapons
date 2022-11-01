package me.b1n4ry.fwep.listeners

import me.b1n4ry.fwep.listeners.weapons.ExampleSword
import me.b1n4ry.fwep.listeners.weapons.ForestHunter
import me.b1n4ry.fwep.listeners.weapons.LegitimooseSword
import me.b1n4ry.fwep.listeners.weapons.SummitShaper
import me.b1n4ry.fwep.util.Util
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.persistence.PersistentDataType

object AbilityListener : Listener {
    @EventHandler
    fun rightClick(e: PlayerInteractEvent) {
        if ((e.action == Action.RIGHT_CLICK_BLOCK || e.action == Action.RIGHT_CLICK_AIR) && e.hand == EquipmentSlot.HAND) {
            when(e.item?.itemMeta?.persistentDataContainer?.get(Util.key, PersistentDataType.STRING)) {
                "examplesword" -> ExampleSword.rightClickAction(e)
                "legitimoosesword" -> LegitimooseSword.rightClickAction(e)
                "summitshaper" -> SummitShaper.rightClickAction(e)
                "foresthunter" -> ForestHunter.rightClickAction(e)
            }
        }
    }

    @EventHandler
    fun bowShoot(e: EntityShootBowEvent) {
        when(e.bow?.itemMeta?.persistentDataContainer?.get(Util.key, PersistentDataType.STRING)) {
            "foresthunter" -> ForestHunter.shootAction(e)
        }
    }

    @EventHandler
    fun projectileHit(e: ProjectileHitEvent) {
        if(e.entity.hasMetadata("foresthunter")) ForestHunter.arrowHitAction(e)
    }
}