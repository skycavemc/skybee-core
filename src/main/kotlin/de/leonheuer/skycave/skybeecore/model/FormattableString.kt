package de.leonheuer.skycave.skybeecore.model

import de.leonheuer.skycave.skybeecore.SkyBeeCore
import org.bukkit.ChatColor

class FormattableString(private var result: String) {

    fun replace(from: String, to: String?): FormattableString {
        result = result.replaceFirst(from.toRegex(), to!!)
        return this
    }

    fun replaceAll(from: String, to: String?): FormattableString {
        result = result.replace(from.toRegex(), to!!)
        return this
    }

    fun get(): String {
        result = ChatColor.translateAlternateColorCodes('&', SkyBeeCore.PREFIX + result)
        return result
    }

    operator fun get(prefix: Boolean): String {
        if (prefix) {
            result = SkyBeeCore.PREFIX + result
        }
        result = ChatColor.translateAlternateColorCodes('&', result)
        return result
    }

    operator fun get(prefix: Boolean, formatted: Boolean): String {
        if (prefix) {
            result = SkyBeeCore.PREFIX + result
        }
        if (formatted) {
            result = ChatColor.translateAlternateColorCodes('&', result)
        }
        return result
    }

}