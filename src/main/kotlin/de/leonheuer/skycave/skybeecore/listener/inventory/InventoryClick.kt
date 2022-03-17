package de.leonheuer.skycave.skybeecore.listener.inventory

import de.leonheuer.skycave.skybeecore.enums.Message
import net.kyori.adventure.text.Component
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.AnvilInventory

class InventoryClick : Listener {

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val player = event.whoClicked
        if (player !is Player) {
            return
        }
        val inv = event.clickedInventory ?: return
        if (inv.type == InventoryType.ANVIL) {
            handleAnvilRename(event, player)
            return
        }
    }

    private fun handleAnvilRename(event: InventoryClickEvent, player: Player) {
        val item = event.currentItem ?: return
        val inv = event.clickedInventory ?: return
        if (inv !is AnvilInventory) {
            return
        }
        if (!player.hasPermission("essentials.anvil.color")) {
            return
        }
        if (item.type == Material.SPAWNER) {
            if (!player.hasPermission("essentials.bypass.spawner.rename")) {
                event.isCancelled = true
                player.sendMessage(Message.SPAWNER_RENAME.getString().get())
                return
            }
        }
        val text = inv.renameText ?: return
        if (event.slotType == InventoryType.SlotType.RESULT) {
            item.editMeta { it.displayName(Component.text(ChatColor.translateAlternateColorCodes('&', text))) }
        }
    }

}