package me.b1n4ry.fwep.util

import me.b1n4ry.fwep.instance
import me.b1n4ry.fwep.listeners.AbilityListener
import me.b1n4ry.fwep.listeners.misc.DamageListener
import me.b1n4ry.fwep.listeners.weapons.TestSword

object Listeners {
    private val listeners = listOf(DamageListener,AbilityListener)

    fun init() {
        for(listener in listeners) instance.server.pluginManager.registerEvents(listener, instance)
    }
}