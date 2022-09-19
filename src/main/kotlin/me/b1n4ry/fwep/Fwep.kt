package me.b1n4ry.fwep

import me.b1n4ry.fwep.util.Commands
import me.b1n4ry.fwep.util.Items
import me.b1n4ry.fwep.util.Listeners
import org.bukkit.plugin.java.JavaPlugin

lateinit var instance : Fwep

class Fwep : JavaPlugin() {

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