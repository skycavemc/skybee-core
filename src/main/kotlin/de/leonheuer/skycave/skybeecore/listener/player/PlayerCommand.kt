package de.leonheuer.skycave.skybeecore.listener.player

import de.leonheuer.skycave.skybeecore.SkyBeeCore
import de.leonheuer.skycave.skybeecore.enums.BlockedCommand
import de.leonheuer.skycave.skybeecore.enums.Message
import org.bukkit.command.SimpleCommandMap
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent

class PlayerCommand(private val main: SkyBeeCore): Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onCommand(event: PlayerCommandPreprocessEvent) {
        val player = event.player
        val cmd = event.message.split(" ")[0]
        val field = main.server::class.java.getDeclaredField("commandMap")
        field.isAccessible = true
        val cmdMap = field.get(main.server) as SimpleCommandMap

        /*if (!main.dataManager.getRegisteredUser(player).hasCompletedCaptcha) {
            event.isCancelled = true
            val captcha = main.playerManager.getRegisteredUser(player).captcha
            player.sendMessage(Message.CAPTCHA_NOT_DONE.getString().replace("%captcha", captcha!!).get())
            return
        }*/

        if (cmd.lowercase().startsWith("/help")) {
            event.isCancelled = true
            player.sendMessage("")
            player.sendMessage(Message.HELP_HEADER.getString().get(false))
            player.sendMessage(Message.HELP_HUB.getString().get(false))
            player.sendMessage(Message.HELP_SPAWN.getString().get(false))
            player.sendMessage(Message.HELP_IS.getString().get(false))
            player.sendMessage(Message.HELP_MSG.getString().get(false))
            player.sendMessage(Message.HELP_WIKI.getString().get(false))
            player.sendMessage("")
            return
        }

        if (player.hasPermission("skybee.bypass.blockedcommands")) {
            return
        }
        if (cmdMap.getCommand(cmd.replaceFirst("/", "")) == null) {
            return
        }

        if (cmd.contains(":")) {
            val partial = cmd.split(":").toTypedArray()
            if (!partial[0].contains(" ")) {
                event.isCancelled = true
                player.sendMessage(Message.COMMAND_BLOCKED.getString().get())
            }
        } else {
            for (blocked in BlockedCommand.values()) {
                if (cmd.lowercase().startsWith("/" + blocked.content)) {
                    event.isCancelled = true
                    player.sendMessage(Message.COMMAND_BLOCKED.getString().get())
                }
            }
        }
    }

}