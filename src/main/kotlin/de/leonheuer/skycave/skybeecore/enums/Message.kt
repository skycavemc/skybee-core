package de.leonheuer.skycave.skybeecore.enums

import de.leonheuer.skycave.skybeecore.model.ColoredStringBuilder

@Suppress("unused")
enum class Message(private val message: String) {

    UNKNOWN_PLAYER("&cDer Spieler %player ist nicht bekannt."),
    NO_PLAYER("&cDu musst ein Spieler sein."),

    // join messages
    JOIN_FLY("&7Dein Fly ist noch aktiviert."),
    JOIN_GOD("&7Dein GodMode ist noch aktiviert."),

    // wiki command messages
    WIKI_RAW("&aMinecraft-Wiki: &7https://minecraft.fandom.com/de/wiki/"),
    WIKI_SEARCH("&aSuchergebnisse für '%search' im Minecraft-Wiki: &7https://minecraft.fandom.com/de/wiki/?search=%wiki"),

    // command blocker messages
    COMMAND_BLOCKED("&cDieser Befehl wurde blockiert."),

    // help command messages
    HELP_HEADER("&eDie wichtigsten Grundbefehle:"),
    HELP_HUB("&a/hub &8» &7Bringt dich zurück in die Lobby."),
    HELP_IS("&a/is &8» &7Teleportiert dich auf deine Insel. Falls du keine hast, wird eine neue erstellt."),
    HELP_SPAWN("&a/spawn &8» &7Teleportiert dich zum Spawn."),
    HELP_MSG("&a/msg <spieler> <nachricht> &8» &7Versendet eine private Nachricht."),
    HELP_WIKI("&eFür eine ausführliche Befehlsübersicht siehe: &b&nhttps://skycave.gitbook.io/skyblock/"),

    // general block messages
    SPAWNER_RENAME("&cDu darfst keine Spawner umbenennen."),

    // back command
    COMMAND_BACK_SUCCESS("&7Du hast dich zu deiner letzten Position teleportiert."),
    COMMAND_BACK_ERROR("&cDu hast keine letzte Position."),
    ;

    fun getString(): ColoredStringBuilder {
        return ColoredStringBuilder(message)
    }

}