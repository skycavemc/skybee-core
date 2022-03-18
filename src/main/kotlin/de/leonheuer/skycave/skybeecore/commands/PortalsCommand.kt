package de.leonheuer.skycave.skybeecore.commands

import de.leonheuer.skycave.skybeecore.enums.FarmWorld
import de.leonheuer.skycave.skybeecore.enums.Message
import de.leonheuer.skycave.skybeecore.util.PortalUtils
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.util.StringUtil
import java.util.function.Consumer

class PortalsCommand : CommandExecutor, TabCompleter {

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        if (args.isEmpty()) {
            sendHelp(sender)
            return true
        }

        when (args[0]) {
            "lock" -> runCommand(sender, args, false, Message.PORTALS_LOCK_HELP, Message.PORTALS_LOCK) {
                PortalUtils.setFarmWorldLocked(it, true)
            }
            "unlock" -> runCommand(sender, args, false, Message.PORTALS_UNLOCK_HELP, Message.PORTALS_UNLOCK) {
                PortalUtils.setFarmWorldLocked(it, true)
            }
            "setspawn" -> runCommand(sender, args, true, Message.PORTALS_SET_SPAWN_HELP, Message.PORTALS_SET_SPAWN) {
                PortalUtils.setFarmWorldSpawn(it, (sender as Player).location)
            }
            "tp" -> runCommand(sender, args, true, Message.PORTALS_TP_HELP, Message.PORTALS_TP) {
                PortalUtils.teleportToFarmWorld(sender as Player, it)
            }
            else -> sendHelp(sender)
        }
        return true
    }

    private fun sendHelp(sender: CommandSender) {
        sender.sendMessage(Message.PORTALS_LOCK_HELP.getString().get(false))
        sender.sendMessage(Message.PORTALS_UNLOCK_HELP.getString().get(false))
        sender.sendMessage(Message.PORTALS_SET_SPAWN_HELP.getString().get(false))
        sender.sendMessage(Message.PORTALS_TP_HELP.getString().get(false))
    }

    private fun runCommand(sender: CommandSender, args: Array<String>, playerOnly: Boolean,
                           help: Message, success: Message, action: Consumer<FarmWorld>
    ) {
        if (playerOnly && sender !is Player) {
            sender.sendMessage("§cDu musst ein Spieler sein.")
            return
        }
        if (args.size < 2) {
            sender.sendMessage(help.getString().get())
            return
        }
        val farmWorld: FarmWorld
        try {
            farmWorld = FarmWorld.valueOf(args[1].uppercase())
        } catch (e: IllegalArgumentException) {
            sender.sendMessage(Message.FARM_WORLD_UNKNOWN.getString().get())
            return
        }
        action.accept(farmWorld)
        sender.sendMessage(success.getString().replace("%world", args[1]).get())
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String> {
        val arguments = ArrayList<String>()
        val completions = ArrayList<String>()

        if (args.size == 1) {
            arguments.add("lock")
            arguments.add("unlock")
            arguments.add("setspawn")
            arguments.add("tp")

            StringUtil.copyPartialMatches(args[0], arguments, completions)
        } else if (args.size == 2) {
            arguments.add("nether")
            arguments.add("end")

            StringUtil.copyPartialMatches(args[1], arguments, completions)
        }

        completions.sort()
        return completions
    }
}