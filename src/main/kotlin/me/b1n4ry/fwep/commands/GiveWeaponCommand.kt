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
            val p = sender as Player
            if(command.name.lowercase() == "giveweapon" && args?.size == 1) {
                when(args[0]) {
                    "testWeapon" -> p.inventory.addItem(Items.testWeapon)
                    "legitimooseSword" -> p.inventory.addItem(Items.legitimooseSword)
                }
                return true
            }
        }
        return false
    }
}

object GiveWeaponTabCompletion : TabCompleter {
    val list = mutableListOf("testWeapon","legitimooseSword")
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>?
    ): MutableList<String>? {
        if(!sender.hasPermission("admin")) {
            return mutableListOf()
        }
        if(command.name == "giveweapon") {
            if(args?.size == 1) return list
        }
        return null
    }
}