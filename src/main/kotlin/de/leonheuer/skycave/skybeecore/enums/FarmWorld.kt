package de.leonheuer.skycave.skybeecore.enums

import de.leonheuer.skycave.skybeecore.model.ColoredStringBuilder
import org.bukkit.Material
import org.bukkit.Sound

enum class FarmWorld(
    val configName: String,
    val title: ColoredStringBuilder,
    val subTitle: ColoredStringBuilder,
    val sound: Sound,
    val material: Material
    ) {

    NETHER(
        "nether",
        ColoredStringBuilder("&8» &4Der Nether &8«"),
        ColoredStringBuilder("&eFarmwelt"),
        Sound.MUSIC_NETHER_BASALT_DELTAS,
        Material.NETHER_PORTAL
    ),
    END(
        "end",
        ColoredStringBuilder("&8» &dDas End &8«"),
        ColoredStringBuilder("&6Halloween Event &e%year"),
        Sound.MUSIC_END,
        Material.END_PORTAL
    );

    companion object {
        fun ofMaterial(material: Material) : FarmWorld? {
            for (farmWorld in values()) {
                if (farmWorld.material == material) {
                    return farmWorld
                }
            }
            return null
        }
    }

}