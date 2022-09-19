package me.b1n4ry.fwep.util

import me.b1n4ry.fwep.commands.GiveWeaponCommand
import me.b1n4ry.fwep.commands.GiveWeaponTabCompletion
import me.b1n4ry.fwep.instance

object Commands {
    fun init() {
        instance.getCommand("giveweapon")?.setExecutor(GiveWeaponCommand)
        instance.getCommand("giveweapon")?.tabCompleter = GiveWeaponTabCompletion
    }
}