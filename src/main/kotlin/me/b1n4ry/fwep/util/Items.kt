package me.b1n4ry.fwep.util

import me.b1n4ry.fwep.util.Util.createWeaponBow
import me.b1n4ry.fwep.util.Util.createWeaponSword
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object Items {
    var exampleSword = ItemStack(Material.IRON_SWORD, 1)
    var legitimooseSword = ItemStack(Material.DIAMOND_SWORD, 1)
    var summitshaper = ItemStack(Material.GOLDEN_SWORD, 1)
    var foresthunter = ItemStack(Material.BOW,1)
    fun init() {
        exampleSword.createWeaponSword("Example Sword", 3, 8.35, listOf(), 1.3, 0.85, 5.0, 1, 1.0)
        legitimooseSword.createWeaponSword("Legitimoose Sword", 6, 6.9, listOf(), 1.75, 1.0, 0.0, 1, 1.5)
        summitshaper.createWeaponSword("Summit Shaper", 5, 8.0, listOf(1f,1.1f,1.25f), 1.8, 1.0, 4.0, 1, 15.0)
        foresthunter.createWeaponBow("Forest Hunter", 5, 2.5, 1.15, 0.0, 1)

    }
}