package de.leonheuer.skycave.skybeecore.listener.player

import de.leonheuer.skycave.skybeecore.SkyBeeCore
import de.leonheuer.skycave.skybeecore.enums.Message
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

@Suppress("DEPRECATION")
class PlayerChat(private val main: SkyBeeCore) : Listener {

    @EventHandler
    fun onPlayerChat(event: AsyncPlayerChatEvent) {
        if (!event.message.startsWith("7")) {
            return
        }
        val args = event.message.substring(1).split(" ")
        main.server.commandMap.getCommand(args[0]) ?: return
        event.isCancelled = true
        main.server.scheduler.runTask(main, Runnable {
            if (main.server.dispatchCommand(event.player, args.joinToString(" "))) {
                event.player.sendMessage(Message.COMMAND_CORRECTION.getString()
                    .replace("%input", "7" + args[0])
                    .replace("%result", "/" + args[0])
                    .get())
            }
        })
    }

}