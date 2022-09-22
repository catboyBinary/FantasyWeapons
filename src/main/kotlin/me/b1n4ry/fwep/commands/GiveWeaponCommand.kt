package me.b1n4ry.fwep.commands

import me.b1n4ry.fwep.util.Items
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

object GiveWeaponCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if(sender is Player) {
            val p = sender
            if(command.name.lowercase() == "giveweapon" && args?.size == 1) {
                when(args[0]) {
                    "examplesword" -> p.inventory.addItem(Items.exampleSword)
                    "legitimoosesword" -> p.inventory.addItem(Items.legitimooseSword)
                    "summitshaper" -> p.inventory.addItem(Items.summitshaper)
                }
                return true
            }
        }
        return false
    }
}

object GiveWeaponTabCompletion : TabCompleter {
    private val list = mutableListOf("examplesword", "legitimoosesword", "summitshaper")
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>?
    ): MutableList<String>? {
        if(!sender.hasPermission("admin")) {
            return mutableListOf()
        }
        if(command.name == "giveweapon") { if(args?.size == 1) return list }
        return null
    }
}