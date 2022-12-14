package me.b1n4ry.fwep.util

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin


abstract class RepeatingTask(plugin: JavaPlugin?, delay: Int, period: Int) : Runnable {
    private val taskId: Int

    init {
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin!!, this, delay.toLong(), period.toLong())
    }

    fun cancel() {
        Bukkit.getScheduler().cancelTask(taskId)
    }
}