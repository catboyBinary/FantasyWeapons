package me.b1n4ry.fwep.commands

import me.b1n4ry.fwep.util.Items
import me.b1n4ry.fwep.util.Util
import me.b1n4ry.fwep.util.Util.generateUUID
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType
import java.util.*

object GiveWeaponCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender is Player) {
            val p = sender
            if(command.name.lowercase() == "giveweapon" && args.size == 1) {
                when(args[0]) {
                    "examplesword" -> {
                        val item = Items.exampleSword
                        item.generateUUID()
                        p.inventory.addItem(item)
                    }
                    "legitimoosesword" -> {
                        val item = Items.legitimooseSword
                        item.generateUUID()
                        p.inventory.addItem(item)
                    }
                    "summitshaper" -> {
                        val item = Items.summitshaper
                        item.generateUUID()
                        p.inventory.addItem(item)
                    }
                    "foresthunter" -> {
                        val item = Items.foresthunter
                        item.generateUUID()
                        p.inventory.addItem(item)
                    }
                }
                return true
            }
        }
        return false
    }
}

object GiveWeaponTabCompletion : TabCompleter {
    private val list = mutableListOf("examplesword", "legitimoosesword", "summitshaper", "foresthunter")
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String>? {
        if(!sender.hasPermission("admin")) {
            return mutableListOf()
        }
        if(command.name == "giveweapon") { if(args.size == 1) return list }
        return null
    }
}