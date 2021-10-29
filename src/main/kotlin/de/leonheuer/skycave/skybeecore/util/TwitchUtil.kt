package de.leonheuer.skycave.skybeecore.util

import org.bukkit.Bukkit
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object TwitchUtil {

    fun isChannelLive(channelName: String): Boolean {
        val url = URL("https://api.twitch.tv/helix/streams?user_login=$channelName")
        val conn = url.openConnection() as HttpURLConnection

        conn.requestMethod = "GET"
        conn.setRequestProperty("Client-Id", "8104q07ko613siz17elh316mk6ppmu")
        conn.setRequestProperty("Authorization", "Bearer e0a6wrskdtzv4s0hygikmy4y3i11zq")
        conn.connect()

        val responseCode = conn.responseCode
        if (responseCode != 200) {
            return false
        }

        val reader = InputStreamReader(conn.content as InputStream)
        val parser = JSONParser()
        val obj = parser.parse(reader) as JSONObject
        val data = obj["data"] as JSONArray

        conn.disconnect()
        return data.isNotEmpty()
    }

    fun setLiveSuffix(playerName: String, channelName: String) {
        val player = Bukkit.getPlayer(playerName) ?: return
        if (player.isOnline) {
            if (isChannelLive(channelName)) {
                LuckPermsUtil.setSuffix(player, "&cLIVE")
            } else {
                LuckPermsUtil.removeSuffix(player, "&cLive")
            }
        }
    }

}