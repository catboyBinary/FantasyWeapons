package me.b1n4ry.fwep.util

import me.b1n4ry.fwep.util.Util.createWeaponSword
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object Items {
    var testWeapon = ItemStack(Material.IRON_SWORD, 1)
    var legitimooseSword = ItemStack(Material.DIAMOND_SWORD, 1)

    fun init() {
        testWeapon.createWeaponSword("Test Sword", 5, 8.35, 1.3, 0.85, 5.0)
        legitimooseSword.createWeaponSword("Legitimoose Sword", 6, 6.9, 1.75, 1.0, 0.0)
    }
}