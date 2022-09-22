package me.b1n4ry.fwep.util

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin


abstract class RepeatingTask(plugin: JavaPlugin?, arg1: Int, arg2: Int) : Runnable {
    private val taskId: Int

    init {
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin!!, this, arg1.toLong(), arg2.toLong())
    }

    fun cancel() {
        Bukkit.getScheduler().cancelTask(taskId)
    }
}