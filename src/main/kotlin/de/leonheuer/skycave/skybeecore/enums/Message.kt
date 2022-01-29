package de.leonheuer.skycave.skybeecore.enums

import de.leonheuer.skycave.skybeecore.model.FormattableString

enum class Message(private val message: String) {

    UNKNOWN_PLAYER("&cDer Spieler %player ist nicht bekannt."),
    NO_PLAYER("&cDu musst ein Spieler sein."),

    // join messages
    JOIN_FLY("&7Dein Fly ist noch aktiviert."),
    JOIN_GOD("&7Dein GodMode ist noch aktiviert."),

    // wiki command messages
    WIKI_RAW("&aMinecraft-Wiki: &7https://minecraft.fandom.com/de/wiki/"),
    WIKI_SEARCH("&aSuchergebnisse für '%search' im Minecraft-Wiki: &7https://minecraft.fandom.com/de/wiki/?search=%wiki"),

    SHOWARMOR_SYNTAX("&e/showarmor <Spieler>"),

    // command blocker messages
    COMMAND_BLOCKED("&cDieser Befehl wurde blockiert."),

    // help command messages
    HELP_HEADER("&eDie wichtigsten Grundbefehle:"),
    HELP_HUB("&a/hub &8» &7Bringt dich zurück in die Lobby."),
    HELP_IS("&a/is &8» &7Teleportiert dich auf deine Insel. Falls du keine hast, wird eine neue erstellt."),
    HELP_SPAWN("&a/spawn &8» &7Teleportiert dich zum Spawn."),
    HELP_MSG("&a/msg <spieler> <nachricht> &8» &7Versendet eine private Nachricht."),
    HELP_WIKI("&eFür eine ausführliche Befehlsübersicht siehe: &b&nhttps://skycave.gitbook.io/skyblock/"),

    // captcha messages
    CAPTCHA_NOT_DONE("&cDu hast die Bestätigung noch nicht vollendet. Dein Bestätigungs-Code lautet &b&l%captcha\n" +
            "&7Gib den Code im Chat ein und bestätige mit Enter:"),

    // chat messages
    CHAT_COOLDOWN("&cDu musst noch %until Minuten warten, bis du den Channel %channel wieder nutzen darfst."),

    // stats command messages
    STATS_LINE("&a%property&8: &7%value"),
    STATS_LINE_TEAM("&c%property&8: &7%value"),

    // general block messages
    SPAWNER_RENAME("&cDu darfst keine Spawner umbenennen."),
    ;

    fun getString(): FormattableString {
        return FormattableString(message)
    }

}