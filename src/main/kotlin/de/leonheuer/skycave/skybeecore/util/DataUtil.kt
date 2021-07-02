package de.leonheuer.skycave.skybeecore.util

import de.leonheuer.skycave.skybeecore.SkyBeeCore
import de.leonheuer.skycave.skybeecore.enums.ChatChannel
import de.leonheuer.skycave.skybeecore.models.User
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.net.InetAddress
import java.time.Duration
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

object DataUtil {

    private val main = JavaPlugin.getPlugin(SkyBeeCore::class.java)
    private val parser = JSONParser()

    fun getUser(uuid: UUID): User? {
        val userFile = File("${main.dataFolder.path}/users/$uuid.json")
        if (!userFile.exists()) {
            return null
        }

        try {
            val reader = FileReader(userFile)
            val userObject = parser.parse(reader) as JSONObject

            val homesObject = userObject["homes"] as JSONObject
            val homes = HashMap<String, Location>()
            homesObject.keys.forEach {
                val locationObject = homesObject[it] as JSONObject
                homes[it as String] = Location(
                    Bukkit.getWorld(UUID.fromString(locationObject["worldUuid"] as String)),
                    locationObject["x"] as Double,
                    locationObject["y"] as Double,
                    locationObject["z"] as Double,
                    (locationObject["yaw"] as Double).toFloat(),
                    (locationObject["pitch"] as Double).toFloat()
                )
            }

            val msgIgnoredArray = userObject["msgIgnored"] as JSONArray
            val msgIgnored = msgIgnoredArray.map { UUID.fromString(it as String) }.toList()

            val mailInboxArray = userObject["mailInbox"] as JSONArray
            val mailInbox = mailInboxArray.map { it as String }.toList()

            return User(
                uuid,
                InetAddress.getByName(userObject["ipAddress"] as String),
                (userObject["hasCompletedCaptcha"] as String).toBooleanStrict(),
                (userObject["flyEnabled"] as String).toBooleanStrict(),
                (userObject["godModeEnabled"] as String).toBooleanStrict(),
                LocalDateTime.parse(userObject["lastJoined"] as String),
                LocalDateTime.parse(userObject["lastLeft"] as String),
                Duration.parse(userObject["playTime"] as String),
                Duration.parse(userObject["playTimeAfk"] as String),
                homes,
                ChatChannel.valueOf(userObject["channel"] as String),
                msgIgnored,
                (userObject["msgBlocked"] as String).toBooleanStrict(),
                (userObject["msgSpy"] as String).toBooleanStrict(),
                mailInbox,
                LocalDateTime.parse(userObject["mailLastSent"] as String),
                (userObject["pollen"] as Long).toULong(),
                (userObject["kills"] as Long).toULong(),
                (userObject["deaths"] as Long).toULong(),
            )
        } catch (e: IOException) {
            return null
        } catch (e: ParseException) {
            return null
        }
    }

    fun createAndGetUser(player: Player): User {
        val uuid = player.uniqueId

        val user = User(uuid, player.address.address, hasCompletedCaptcha = false, flyEnabled = false,
            godModeEnabled = false, LocalDateTime.now(), null, Duration.ZERO, Duration.ZERO, HashMap(),
            ChatChannel.GLOBAL, ArrayList(), msgBlocked = false, msgSpy = false, ArrayList(), null,
            0UL, 0UL, 0UL
        )

        saveUser(user)
        return user
    }

    fun saveUser(user: User) {
        val uuid = user.uuid

        val homesObject = JSONObject()
        user.homes.keys.forEach {
            val loc = user.homes[it]!!
            val locationObject = JSONObject()
            locationObject["world"] = loc.world.uid.toString()
            locationObject["x"] = loc.x
            locationObject["y"] = loc.y
            locationObject["z"] = loc.z
            locationObject["yaw"] = loc.yaw
            locationObject["pitch"] = loc.pitch
            homesObject[it] = locationObject
        }

        val msgIgnoredArray = JSONArray()
        user.msgIgnored.forEach { msgIgnoredArray.add(it.toString()) }

        val mailInboxArray = JSONArray().addAll(user.mailInbox)

        val userObject = JSONObject()
        userObject["uuid"] = user.uuid.toString()
        userObject["ipAddress"] = user.ipAddress.toString()
        userObject["hasCompletedCaptcha"] = user.hasCompletedCaptcha.toString()
        userObject["flyEnabled"] = user.flyEnabled.toString()
        userObject["godModeEnabled"] = user.godModeEnabled.toString()
        userObject["lastJoined"] = user.lastJoined.toString()
        if (user.lastLeft == null) {
            userObject["lastLeft"] = null
        } else {
            userObject["lastLeft"] = user.lastLeft!!.toString()
        }
        userObject["playTime"] = user.playTime.toString()
        userObject["playTimeAfk"] = user.playTimeAfk.toString()
        userObject["homes"] = homesObject
        userObject["channel"] = user.channel.toString()
        userObject["msgIgnored"] = msgIgnoredArray
        userObject["msgBlocked"] = user.msgBlocked.toString()
        userObject["msgSpy"] = user.msgSpy.toString()
        userObject["mailInbox"] = mailInboxArray
        if (user.mailLastSent == null) {
            userObject["mailLastSent"] = null
        } else {
            userObject["mailLastSent"] = user.mailLastSent!!.toString()
        }
        userObject["pollen"] = user.pollen
        userObject["kills"] = user.kills
        userObject["deaths"] = user.deaths

        try {
            val userFile = File("${main.dataFolder.path}/users/$uuid.json")
            if (!userFile.exists()) {
                userFile.createNewFile()
            }
            val writer = FileWriter(userFile)
            writer.write(userObject.toJSONString())
            writer.flush()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}