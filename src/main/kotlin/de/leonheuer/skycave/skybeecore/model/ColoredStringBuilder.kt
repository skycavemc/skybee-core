package de.leonheuer.skycave.skybeecore.model

import de.leonheuer.skycave.skybeecore.SkyBeeCore
import org.bukkit.ChatColor

@Suppress("unused")
class ColoredStringBuilder(private var result: String) {

    fun replace(from: String, to: String?): ColoredStringBuilder {
        result = result.replaceFirst(from.toRegex(), to!!)
        return this
    }

    fun replaceAll(from: String, to: String?): ColoredStringBuilder {
        result = result.replace(from.toRegex(), to!!)
        return this
    }

    fun get(prefix: Boolean = false, formatted: Boolean = false): String {
        if (prefix) {
            result = SkyBeeCore.PREFIX + result
        }
        if (formatted) {
            result = ChatColor.translateAlternateColorCodes('&', result)
        }
        return result
    }

}