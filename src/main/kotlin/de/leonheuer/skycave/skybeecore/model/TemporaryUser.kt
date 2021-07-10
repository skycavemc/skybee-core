package de.leonheuer.skycave.skybeecore.model

import de.leonheuer.skycave.skybeecore.enums.ChatChannel
import org.bukkit.entity.Player
import java.time.LocalDateTime

data class TemporaryUser(
    val channelUseTime: HashMap<ChatChannel, LocalDateTime>,
    var msgPartner: Player?,
    val captcha: String?,
    var afk: Boolean,
)
