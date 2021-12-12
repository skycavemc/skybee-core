package de.leonheuer.skycave.skybeecore.commands

import de.leonheuer.skycave.skybeecore.enums.Message
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import java.util.*

class WikiCommand: CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            sender.sendMessage(Message.WIKI_RAW.getString().get())
            return true
        }

        val search = StringJoiner(" ")
        args.forEach(search::add)
        val searchWiki = StringJoiner("+")
        args.forEach(searchWiki::add)

        sender.sendMessage(Message.WIKI_SEARCH.getString().get()
            .replace("%search", search.toString())
            .replace("%wiki", searchWiki.toString())
        )
        return true
    }

}