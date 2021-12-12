package de.leonheuer.skycave.skybeecore.commands

import de.leonheuer.skycave.skybeecore.SkyBeeCore
import de.leonheuer.skycave.skybeecore.enums.Message
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ShowArmorCommand(private val main: SkyBeeCore): CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Message.NO_PLAYER.getString().get(false))
            return true
        }
        if (args.isEmpty()) {
            sender.sendMessage(Message.SHOWARMOR_SYNTAX.getString().get())
            return true
        }
        val other = Bukkit.getPlayer(args[1])
        if (other == null) {
            sender.sendMessage(Message.UNKNOWN_PLAYER.getString().get())
            return true
        }
        val armorContents = Bukkit.createInventory(null, 9, "§8Rüstung von ${args[1]}")
        armorContents.setItem(0, other.inventory.helmet)
        armorContents.setItem(1, other.inventory.chestplate)
        armorContents.setItem(2, other.inventory.leggings)
        armorContents.setItem(3, other.inventory.boots)
        armorContents.setItem(4, other.inventory.itemInOffHand)
        sender.openInventory(armorContents)
        return true
    }

}