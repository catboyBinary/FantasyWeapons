package me.b1n4ry.fwep.util

import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration

object Rarity {
    var admin = Style.style(TextColor.fromHexString("#fc3f3f"), TextDecoration.ITALIC.withState(false))
    var legendary = Style.style(TextColor.fromHexString("#ea9131"), TextDecoration.ITALIC.withState(false))
    var epic = Style.style(TextColor.fromHexString("#a443e0"), TextDecoration.ITALIC.withState(false))
    var rare = Style.style(TextColor.fromHexString("#3090ff"), TextDecoration.ITALIC.withState(false))
    var uncommon = Style.style(TextColor.fromHexString("#5df45f"), TextDecoration.ITALIC.withState(false))
    var common = Style.style(TextColor.fromHexString("#afafaf"), TextDecoration.ITALIC.withState(false))
    var none = Style.style(TextColor.fromHexString("#666666"), TextDecoration.ITALIC.withState(false))
}