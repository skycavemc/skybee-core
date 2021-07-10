package de.leonheuer.skycave.skybeecore.model

import de.leonheuer.skycave.skybeecore.enums.ChatChannel
import org.bukkit.Location
import java.net.InetAddress
import java.time.Duration
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.HashMap

data class User(

    // basic data
    val uuid: UUID,
    var ipAddress: InetAddress,
    var hasCompletedCaptcha: Boolean,
    var flyEnabled: Boolean,
    var godModeEnabled: Boolean,

    // session stats data
    var lastJoined: LocalDateTime,
    var lastLeft: LocalDateTime?,
    var playTime: Duration,
    var playTimeAfk: Duration,

    // homes data
    val homes: HashMap<String, Location>,

    // chat data
    var channel: ChatChannel,

    // msg data
    val msgIgnored: List<UUID>,
    var msgBlocked: Boolean,
    var msgSpy: Boolean,

    // mail data
    val mailInbox: List<String>,
    var mailLastSent: LocalDateTime?,

    // votes data
    var pollen: ULong,

    // pvp stats data
    var kills: ULong,
    var deaths: ULong,
    )
