package de.leonheuer.skycave.skybeecore.model

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class CustomItem(material: Material, amount: Int) {

    private val itemStack = ItemStack(material, amount)

    fun name(name: String): CustomItem {
        val meta = itemStack.itemMeta ?: return this
        meta.setDisplayName(name)
        itemStack.itemMeta = meta
        return this
    }

    fun lore(vararg lore: String): CustomItem {
        val meta = itemStack.itemMeta ?: return this
        meta.lore = lore.toList()
        itemStack.itemMeta = meta
        return this
    }

    fun flags(vararg flags: ItemFlag): CustomItem {
        val meta = itemStack.itemMeta ?: return this
        meta.addItemFlags(*flags)
        itemStack.itemMeta = meta
        return this
    }

    fun unbreakable(unbreakable: Boolean): CustomItem {
        val meta = itemStack.itemMeta ?: return this
        meta.isUnbreakable = unbreakable
        itemStack.itemMeta = meta
        return this
    }

    fun enchant(enchant: Enchantment, level: Int): CustomItem {
        val meta = itemStack.itemMeta ?: return this
        meta.addEnchant(enchant, level, true)
        itemStack.itemMeta = meta
        return this
    }

    fun getResult(): ItemStack {
        return itemStack
    }

}