package net.minebo.basalt.util

import com.google.gson.JsonObject
import net.minebo.basalt.Basalt
import net.minebo.basalt.api.BasaltAPI
import net.minebo.basalt.models.profile.GameProfile
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.net.URLConnection
import java.util.*
import java.util.concurrent.CompletableFuture


object WebUtil
{

    fun playerHasLiked(uuid: UUID): CompletableFuture<Boolean>
    {
        return CompletableFuture.supplyAsync {
            val serverLikes = URL("https://api.namemc.com/server/${BasaltAPI.SERVER_NAME}/likes")
            val urlConn: URLConnection = serverLikes.openConnection()
            urlConn.setRequestProperty(
                "User-Agent",
                "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2"
            )
            urlConn.doOutput
            BufferedReader(InputStreamReader(urlConn.getInputStream())).use { reader ->
                val obj = Basalt.gson.fromJson(reader, Array<String>::class.java)

                if (obj.contains(uuid.toString()))
                {
                    return@supplyAsync true
                }
            }

            return@supplyAsync false
        }
    }

    fun requestMojangService(name: String): CompletableFuture<UUID?>
    {
        return CompletableFuture.supplyAsync {
            val urlConn: URLConnection = URL("https://api.mojang.com/users/profiles/minecraft/$name").openConnection()
            urlConn.setRequestProperty(
                "User-Agent",
                "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2"
            )
            urlConn.doOutput
            BufferedReader(InputStreamReader(urlConn.getInputStream())).use { reader ->
                val obj = Basalt.gson.fromJson(reader, JsonObject::class.java)
                if (obj.has("id")) return@supplyAsync UUID.fromString(obj["id"].asString)
            }

            return@supplyAsync null
        }
    }

    fun evaluateMojangUser(sender: CommandSender, name: String): CompletableFuture<GameProfile?>
    {
        return requestMojangService(name).thenApply { uuid ->
            println("evaluate mojang user ${Bukkit.isPrimaryThread()}")
            if (uuid == null)
            {
                sender.sendMessage(Chat.format("&cThe username &e${name} &cwas not found in Mojang or the database"))
                return@thenApply null
            }

            return@thenApply GameProfile(
                UUID.randomUUID(), name, name.lowercase(Locale.getDefault()),
                JsonObject(), "", arrayListOf(), arrayListOf(),
                null, null, null, mutableListOf(),
                System.currentTimeMillis()
            )
        }
    }
}
