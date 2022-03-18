package de.leonheuer.skycave.skybeecore.listener.player

import de.leonheuer.skycave.skybeecore.enums.FarmWorld
import de.leonheuer.skycave.skybeecore.enums.Message
import de.leonheuer.skycave.skybeecore.util.PortalUtils
import net.kyori.adventure.text.Component
import net.kyori.adventure.title.Title
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerPortalEvent
import java.time.Duration
import java.time.LocalDate

class PlayerPortal : Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    fun onPlayerPortal(event: PlayerPortalEvent) {
        event.isCancelled = true
        val player = event.player
        val farmWorld = FarmWorld.ofMaterial(player.location.block.type) ?: return

        if (PortalUtils.isFarmWorldLocked(farmWorld)) {
            player.sendMessage(Message.DIMENSION_LOCKED.getString().get())
            return
        }

        PortalUtils.teleportToFarmWorld(player, farmWorld)
        player.showTitle(Title.title(
            Component.text(farmWorld.title.get(false)),
            Component.text(farmWorld.subTitle
                .replace("%year", LocalDate.now().year.toString()).get(false)),
            Title.Times.times(
                Duration.ofSeconds(1),
                Duration.ofSeconds(3),
                Duration.ofSeconds(1)
            )
        ))
        player.playSound(player.location, farmWorld.sound, 1.0f, 1.0f)
    }

}