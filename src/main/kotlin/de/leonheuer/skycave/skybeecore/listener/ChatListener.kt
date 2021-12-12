package de.leonheuer.skycave.skybeecore.listener

import de.leonheuer.skycave.skybeecore.SkyBeeCore
import de.leonheuer.skycave.skybeecore.enums.ChatChannel
import de.leonheuer.skycave.skybeecore.enums.Message
import org.apache.commons.lang.StringUtils
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import java.time.Duration
import java.time.LocalDateTime

class ChatListener(private val main: SkyBeeCore): Listener {

    @Suppress("Deprecation")
    @EventHandler
    fun onChat(event: AsyncPlayerChatEvent) {
        val player = event.player
        val user = main.dataManager.getRegisteredUser(player)
        val tempUser = main.playerManager.getRegisteredUser(player)
        event.message.replace("<3", "❤")

        if (user.channel.permission != null && !player.hasPermission(user.channel.permission!!)) {
            user.channel = ChatChannel.GLOBAL
        }

        var color = user.channel.color
        var namecolor = "&7"
        if (player.hasPermission("skybee.chat.staff")) {
            if (!user.channel.overrideTeamColor) {
                color = "&b"
            }
            if (player.name == "caveclown") {
                namecolor = "&a"
            }
            if (!user.channel.overrideTeamColor && player.name == "caveclown") {
                color = "&9"
            }
            event.format = ChatColor.translateAlternateColorCodes('&',
                "&8[${user.channel.prefix}&8] [${main.chat.getPlayerPrefix(player)}&8] $namecolor${player.name} &8» " +
                        "$color${event.message}"
            )
        } else {
            event.format = ChatColor.translateAlternateColorCodes('&',
                "&8[${user.channel.prefix}&8] [${main.chat.getPlayerPrefix(player)}&8] $namecolor${player.name} &8» "
            ) + "$color${event.message}"
        }

        val useTime = tempUser.channelUseTime[user.channel]
        if (user.channel.minutes > 0 && useTime != null) {
            val waited = Duration.between(useTime, LocalDateTime.now()).toMinutes()
            if (waited < user.channel.minutes) {
                event.isCancelled = true
                player.sendMessage(Message.CHAT_COOLDOWN.getString().get()
                    .replace("%until", (user.channel.minutes - waited).toString())
                    .replace("%channel", StringUtils.capitalize(user.channel.toString().lowercase()))
                )
                return
            }
        }

        if (user.channel.range > 0) {
            event.isCancelled = true
            Bukkit.getOnlinePlayers()
                .filter { recipient -> player.location.distance(recipient.location) <= user.channel.range }
                .forEach { recipient -> recipient.sendMessage(event.format)
            }
        }
    }


    @Suppress("Deprecation")
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onChatCaptcha(event: AsyncPlayerChatEvent) {
        TODO("captcha request")
    }

}