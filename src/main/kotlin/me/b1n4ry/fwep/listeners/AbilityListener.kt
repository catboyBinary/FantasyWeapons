package me.b1n4ry.fwep.listeners

import me.b1n4ry.fwep.listeners.weapons.ExampleSword
import me.b1n4ry.fwep.listeners.weapons.LegitimooseSword
import me.b1n4ry.fwep.listeners.weapons.SummitShaper
import me.b1n4ry.fwep.util.Util
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
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
            }
        }
    }
}