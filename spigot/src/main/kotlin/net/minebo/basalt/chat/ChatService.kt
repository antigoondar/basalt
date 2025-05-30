package net.minebo.basalt.chat

import net.minebo.basalt.BasaltSpigotPlugin
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.TimeUnit

object ChatService
{

    val SLOW_MESSAGE = BasaltSpigotPlugin.instance.config.getString("chat.slowChatMessage")
    val MUTE_MESSAGE = BasaltSpigotPlugin.instance.config.getString("chat.muteChatMessage")
    val MINIMUM_LINK_SEND_RANK = BasaltSpigotPlugin.instance.config.getString("chat.rankToSendLinks.minimum_rank")
    val LINK_LIMIT_ENABLED = BasaltSpigotPlugin.instance.config.getBoolean("chat.rankToSendLinks.enabled")

    val cooldownMap = hashMapOf<UUID, Long>()

    var slowed = false
    var slowDuration = 3

    var muted = false

    fun addCooldown(player: Player)
    {
        cooldownMap[player.uniqueId] = System.currentTimeMillis()
    }

    fun isOnCooldown(player: Player): Boolean
    {
        val contains = cooldownMap.containsKey(player.uniqueId)

        return if (contains && System.currentTimeMillis()
                .minus(cooldownMap[player.uniqueId]!!) < TimeUnit.SECONDS.toMillis((slowDuration + 1).toLong())
        )
        {
            true
        } else
        {
            cooldownMap.remove(player.uniqueId)

            false
        }
    }

    fun getCooldownRemaining(player: Player): Int
    {
        if (!cooldownMap.containsKey(player.uniqueId)) return 0

        val cooldown = cooldownMap[player.uniqueId]!!
        val removingAt = cooldown + TimeUnit.SECONDS.toMillis((slowDuration + 1).toLong())

        return (removingAt.minus(System.currentTimeMillis()) / 1000L).toInt()
    }

}