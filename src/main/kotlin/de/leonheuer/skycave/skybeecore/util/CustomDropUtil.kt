package de.leonheuer.skycave.skybeecore.util

import de.leonheuer.skycave.skybeecore.enums.CustomDrop
import org.bukkit.entity.EntityType

object CustomDropUtil {

    fun getRandomCustomDrop(entityType: EntityType): CustomDrop? {
        // TODO random drop
        return getPossibleCustomDrops(entityType).firstOrNull()
    }

    private fun getPossibleCustomDrops(entityType: EntityType): List<CustomDrop> {
        return CustomDrop.values().filter { drop -> drop.entityType == entityType }.toList()
    }

}