package me.b1n4ry.fwep.util

import me.b1n4ry.fwep.Fwep
import me.b1n4ry.fwep.instance
import me.b1n4ry.fwep.util.Util.hasCooldown
import me.b1n4ry.fwep.util.Util.setCooldown
import me.b1n4ry.fwep.util.Util.setDef
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.NamespacedKey
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType
import java.util.*

import kotlin.math.roundToInt

object Util {
    var key = NamespacedKey("fwep","weapontype")
    private val threehalves = listOf('%','a','b','c','d','e','g','h','j','m','n','o','p','q','r','s','t','u','v','w','x','y','z','F','K','L','T','A','B','C','D','E','G','H','J','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','0','1','2','3','4','5','6','7','8','9')
    private val fivequarters = listOf('f','k')
    private val threequarters = listOf('l')
    private val half = listOf('i','.',':')
    private val one = listOf('I',' ','[',']', '{', '}')
    private val two = listOf('✦','★')
    private val rarities = listOf<Component>(
            Component.text(("[✧✧✧✧✧]"),  Rarity.none),
            Component.text(("[✦✧✧✧✧]"),  Rarity.common),
            Component.text(("[✦✦✧✧✧]"),  Rarity.uncommon),
            Component.text(("[✦✦✦✧✧]"),  Rarity.rare),
            Component.text(("[✦✦✦✦✧]"),  Rarity.epic),
            Component.text(("[✦✦✦✦✦]"),  Rarity.legendary),
            Component.text(("{✦✦✦✦✦✦}"), Rarity.admin)
    )

    private fun countSpaces(s: String): Double {
        var spaces = 0.0
        for(c in s) {
            when (c) {
                in threehalves -> spaces+=1.5
                in fivequarters -> spaces+=1.25
                in threequarters -> spaces+=0.75
                in half -> spaces+=0.5
                in one -> spaces+=1
                in two -> spaces+=2
                else -> spaces+=1.25
            }
        }
        return spaces
    }
    private fun alignString(name: String): String {
        val spaces = "                  ".dropLast((countSpaces(name)/2).roundToInt())
        return spaces+name+spaces
    }
    private fun ItemMeta.setDamage(d: Double) {
        this.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, AttributeModifier(UUID.randomUUID(), "damage", d-1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND))
    }
    private fun ItemMeta.setAtkSpeed(d: Double) {
        this.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, AttributeModifier(UUID.randomUUID(), "attackspeed", d-4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND))
    }
    private fun ItemMeta.setMvSpeed(d: Double) {
        this.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, AttributeModifier(UUID.randomUUID(), "movementspeed", d-0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND))
    }
    private fun ItemMeta.setDef(d: Double) {
        this.addAttributeModifier(Attribute.GENERIC_ARMOR, AttributeModifier(UUID.randomUUID(), "defense", d, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND))
        if(d>0)this.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, AttributeModifier(UUID.randomUUID(), "toughness", 2.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND))
    }
    private fun generateLore(rarity: Int, damage: Double, atkspeed: Double, mvspeed: Double, def: Double, cd: Double) : List<Component> {
        return listOf(
                rarities[rarity],
                Component.empty(),
                Component.text(("   Stats"), Style.style(TextColor.fromHexString("#75e3ff"), TextDecoration.ITALIC.withState(false))),
                Component.text(("\uD83D\uDDE1 Base Damage: $damage"), Style.style(TextColor.fromHexString("#08add6"), TextDecoration.ITALIC.withState(false))),
                Component.text(("☄ Attack Speed: $atkspeed"), Style.style(TextColor.fromHexString("#08add6"), TextDecoration.ITALIC.withState(false))),
                Component.text(("→ Movement Speed: ${mvspeed.roundToInt()}%"), Style.style(TextColor.fromHexString("#08add6"), TextDecoration.ITALIC.withState(false))),
                Component.text(("\uD83D\uDEE1 Defense: +$def"), Style.style(TextColor.fromHexString("#08add6"), TextDecoration.ITALIC.withState(false))),
                Component.text(("⌛ Ability Cooldown: ${cd}s"), Style.style(TextColor.fromHexString("#08add6"), TextDecoration.ITALIC.withState(false)))

        )
    }

    private fun generateBowLore(rarity: Int, mvspeed: Double, def: Double, damage: Double) : List<Component> {
        return listOf(
            rarities[rarity],
            Component.empty(),
            Component.text(("   Stats"), Style.style(TextColor.fromHexString("#75e3ff"), TextDecoration.ITALIC.withState(false))),
            Component.text(("\uD83D\uDDE1 Projectile Damage: $damage"), Style.style(TextColor.fromHexString("#08add6"), TextDecoration.ITALIC.withState(false))),
            Component.text(("→ Movement Speed: ${mvspeed.roundToInt()}%"), Style.style(TextColor.fromHexString("#08add6"), TextDecoration.ITALIC.withState(false))),
            Component.text(("\uD83D\uDEE1 Defense: +$def"), Style.style(TextColor.fromHexString("#08add6"), TextDecoration.ITALIC.withState(false)))
        )
    }

    fun String?.toInternal() : String? {
        return this?.lowercase()?.replace(" ", "")?.replace("`", "")
    }

    fun LivingEntity.setCooldown(id: String, seconds: Double) {
        val cooldown = (seconds*1000).toLong()
        instance.cooldownMap[Pair(this.uniqueId,id)] = System.currentTimeMillis()+cooldown
    }
    fun LivingEntity.hasCooldown(id: String) : Boolean {
        if(instance.cooldownMap[Pair(this.uniqueId,id)] == null) return false
        return this.getCooldown(id) > 0
    }
    fun LivingEntity.getCooldown(id: String) : Double {
        return (instance.cooldownMap[Pair(this.uniqueId,id)]!! - System.currentTimeMillis()) / 1000.0
    }
    fun ItemStack.createWeaponSword(name: String, rarity: Int, damage: Double, atkspeed: Double, mvspeed: Double, def: Double, cmd: Int, cooldown: Double) {
        val weaponMeta = this.itemMeta
        weaponMeta.isUnbreakable = true
        weaponMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE)
        weaponMeta.displayName(Component.text((name), rarities[rarity].style()))
        weaponMeta.lore(generateLore(rarity,damage,atkspeed,mvspeed*100, def, cooldown))
        weaponMeta.setCustomModelData(cmd)
        weaponMeta.persistentDataContainer.set(key, PersistentDataType.STRING, name.toInternal().toString())
        weaponMeta.setDamage(damage)
        weaponMeta.setAtkSpeed(atkspeed)
        weaponMeta.setMvSpeed(mvspeed/10)
        weaponMeta.setDef(def)
        this.itemMeta = weaponMeta
    }

    fun ItemStack.createWeaponBow(name: String, rarity: Int, damage: Double, mvspeed: Double, def: Double, cmd: Int) {
        val weaponMeta = this.itemMeta
        weaponMeta.isUnbreakable = true
        weaponMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE)
        weaponMeta.displayName(Component.text(name, rarities[rarity].style()))
        weaponMeta.lore(generateBowLore(rarity,mvspeed*100, def, damage))
        weaponMeta.setCustomModelData(cmd)
        weaponMeta.persistentDataContainer.set(key, PersistentDataType.STRING, name.toInternal().toString())
        weaponMeta.setMvSpeed(mvspeed/10)
        weaponMeta.setDef(def)
        this.itemMeta = weaponMeta
    }
}