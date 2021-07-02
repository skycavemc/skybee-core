package de.leonheuer.skycave.skybeecore.enums

import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemStack

enum class CustomDrop(val entityType: EntityType, val chance: Float, val itemStack: ItemStack, val text: String) {

    // TODO insert drops
    ZOMBIE_1(EntityType.ZOMBIE, 17.5F, ItemStack(Material.ROTTEN_FLESH, 4), "&3+4 &bverrottetes Fleisch"),

}