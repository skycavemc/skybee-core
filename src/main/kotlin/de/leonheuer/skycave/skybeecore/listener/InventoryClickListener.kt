package de.leonheuer.skycave.skybeecore.listener

import de.leonheuer.skycave.skybeecore.enums.Message
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.AnvilInventory

class InventoryClickListener : Listener {

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

        /*val title = player.openInventory.title
        if (!player.hasPermission("skybee.command.showarmor") ||
            event.currentItem == null ||
            !title.startsWith("§8Rüstung von ") ||
            event.action != InventoryAction.PICKUP_ALL
        ) {
            return
        }

        val other = Bukkit.getPlayer(title.substring(12)) ?: return
        when (event.rawSlot) {
            0 -> {
                other.inventory.helmet = ItemStack(Material.AIR, 0)
            }
            1 -> {
                other.inventory.chestplate = ItemStack(Material.AIR, 0)
            }
            2 -> {
                other.inventory.leggings = ItemStack(Material.AIR, 0)
            }
            3 -> {
                other.inventory.boots = ItemStack(Material.AIR, 0)
            }
            4 -> {
                other.inventory.setItemInOffHand(ItemStack(Material.AIR, 0))
            }
        }
        player.inventory.addItem(event.currentItem)
        player.setItemOnCursor(null)*/
    }

    private fun handleAnvilRename(event: InventoryClickEvent, player: Player) {
        if (event.currentItem!!.type == Material.SPAWNER) {
            if (!player.hasPermission("essentials.bypass.spawner.rename")) {
                event.isCancelled = true
                player.sendMessage(Message.SPAWNER_RENAME.getString().get())
                return
            }
        }
        if (!player.hasPermission("essentials.anvil.color")) {
            return
        }
        val inv = event.clickedInventory ?: return
        if (inv !is AnvilInventory) {
            return
        }
        val text = inv.renameText ?: return
        if (event.slotType == InventoryType.SlotType.RESULT) {
            val item = event.currentItem ?: return
            val meta = item.itemMeta ?: return
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', text))
            item.itemMeta = meta
        }
    }

}