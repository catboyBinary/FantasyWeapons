package me.b1n4ry.fwep

import me.b1n4ry.fwep.util.Commands
import me.b1n4ry.fwep.util.ForestBolt
import me.b1n4ry.fwep.util.Items
import me.b1n4ry.fwep.util.Listeners
import org.bukkit.inventory.Inventory
import org.bukkit.plugin.java.JavaPlugin
import java.util.*
import kotlin.collections.HashMap

lateinit var instance : Fwep

class Fwep : JavaPlugin() {
    val projectiles = mutableListOf<ForestBolt>()
    val cooldownMap = HashMap<Pair<UUID, String>, Long>()
    val comboMap = HashMap<Pair<UUID, String>, Int>()
    val inventoryMap = HashMap<UUID, Inventory>()
    override fun onEnable() {
        instance = this
        Items.init()
        Listeners.init()
        Commands.init()
        logger.info("FWep has been successfully initialized!")
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}