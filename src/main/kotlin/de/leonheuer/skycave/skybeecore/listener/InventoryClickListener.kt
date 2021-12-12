package de.leonheuer.skycave.skybeecore.listener

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.AnvilInventory
import org.bukkit.inventory.ItemStack

class InventoryClickListener : Listener {

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {

        val player = event.whoClicked as Player
        if (event.clickedInventory!!.type == InventoryType.ANVIL) {
            handleAnvilRename(event, player)
            return
        }

        if (!player.hasPermission("skybee.command.showarmor")) {
            event.isCancelled = true
            return
        }
        if (event.currentItem == null) {
            return
        }
        val title = player.openInventory.title
        if (!title.startsWith("§8Rüstung von ")) {
            return
        }
        if (event.action == InventoryAction.MOVE_TO_OTHER_INVENTORY && event.clickedInventory != player.inventory) {
            event.isCancelled = true
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
    }

    private fun handleAnvilRename(event: InventoryClickEvent, player: Player) {
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