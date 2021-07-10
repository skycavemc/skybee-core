package de.leonheuer.skycave.skybeecore.model

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class CustomItem(material: Material, amount: Int) {

    val itemStack = ItemStack(material, amount)

    fun setName(name: String) {
        val meta = itemStack.itemMeta
        meta.displayName(Component.text(name))
        itemStack.itemMeta = meta
    }

    fun setName(name: Component) {
        val meta = itemStack.itemMeta
        meta.displayName(name)
        itemStack.itemMeta = meta
    }

    fun setLore(vararg lore: String) {
        val meta = itemStack.itemMeta
        val lines = ArrayList<Component>()
        lore.forEach { lines.add(Component.text(it)) }
        meta.lore(lines)
        itemStack.itemMeta = meta
    }

    fun setLore(vararg lore: Component) {
        val meta = itemStack.itemMeta
        val lines = ArrayList<Component>()
        lines.addAll(lore)
        meta.lore(lines)
        itemStack.itemMeta = meta
    }

    fun addFlag(flag: ItemFlag) {
        itemStack.addItemFlags(flag)
    }

    fun addFlags(vararg flags: ItemFlag) {
        itemStack.addItemFlags(*flags)
    }

    fun setUnbreakable(unbreakable: Boolean) {
        val meta = itemStack.itemMeta
        meta.isUnbreakable = unbreakable
        itemStack.itemMeta = meta
    }

    fun addEnchant(enchant: Enchantment, level: Int) {
        val meta = itemStack.itemMeta
        meta.addEnchant(enchant, level, true)
        itemStack.itemMeta = meta
    }

}