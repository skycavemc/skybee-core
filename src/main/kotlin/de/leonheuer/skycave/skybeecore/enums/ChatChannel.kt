package de.leonheuer.skycave.skybeecore.enums

enum class ChatChannel(val prefix: String, val range: Int, val minutes: Int, val permission: String?, val color: String, val overrideTeamColor: Boolean) {

    GLOBAL("&7G", -1,-1, null, "&7", false),
    LOCAL("&3L", 200, -1, null, "&3", false),
    TRADE("&eH", -1, 15, null, "&e", true),
    EVENT("&dE", -1, 30, null, "&d", true),
    STAFF("&9S", -1, -1, "skybee.chat.channel.staff", "&9", true)

}