package net.minebo.basalt.listeners

import net.minebo.basalt.BasaltBungeePlugin
import net.minebo.basalt.lockdown.LockdownManager
import net.minebo.basalt.packets.StaffMessagePacket
import net.minebo.basalt.service.expirable.RankGrantService
import net.minebo.basalt.service.profiles.ProfileGameService
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.event.LoginEvent
import net.md_5.bungee.api.event.PlayerDisconnectEvent
import net.md_5.bungee.api.event.ServerConnectEvent
import net.md_5.bungee.api.event.ServerSwitchEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler
import java.util.concurrent.TimeUnit

//no packets actually need to send through redis because bungee has all players already stored
//I am going to do something with redis in the future but this is just a quick solution
class BungeeListener : Listener {

    @EventHandler
    fun switch(event: ServerSwitchEvent) {
        val player = event.player.uniqueId

        val playerRank = ProfileGameService.byId(player)?.getHighestGlobalRank() ?: return
        BasaltBungeePlugin.instance.proxy.scheduler.schedule(BasaltBungeePlugin.instance, {
            if (playerRank.staff && event.from != null) {
                StaffMessagePacket("&b[S] &r" + playerRank.color + event.player.name + " &3joined &b" + event.player.server.info.name + " &3from &b" + event.from.name).action()
            }
        }, 100L, TimeUnit.MILLISECONDS)
    }

    @EventHandler
    fun handlePermissions(event: ServerConnectEvent)
    {
        val profile = ProfileGameService.byId(event.player.uniqueId) ?: return

        for ((a, b) in profile.getPermissionsExclusivelyGlobal())
        {
            if (!b)
            {
                event.player.setPermission(a, true)
            }
        }
    }

    @EventHandler
    fun login(event: LoginEvent) {
        val player = event.connection.uniqueId

        val playerRank = ProfileGameService.byId(player)?.getHighestGlobalRank() ?: return

        BasaltBungeePlugin.instance.proxy.scheduler.schedule(BasaltBungeePlugin.instance, {
            if (playerRank.staff) {
                StaffMessagePacket("&b[S] &r" + playerRank.color + event.connection.name + " &3connected to the network").action()
            }
        }, 100L, TimeUnit.MILLISECONDS)
    }

    @EventHandler
    fun dc(event: PlayerDisconnectEvent)
    {
        val player = event.player

        val profile = ProfileGameService.byId(player.uniqueId) ?: return

        //false fire
        if (player.isConnected) return

        RankGrantService.recalculatePlayer(profile)

        val playerRank = profile.getHighestGlobalRank()

        BasaltBungeePlugin.instance.proxy.scheduler.schedule(BasaltBungeePlugin.instance, {
            if (playerRank.staff) {
                StaffMessagePacket("&b[S] &r" + playerRank.color + player.name + " &3left the network").action()
            }
        }, 100L, TimeUnit.SECONDS)
    }

    @EventHandler
    fun checkClearance(event: ServerConnectEvent) {
        BasaltBungeePlugin.instance.proxy.scheduler.schedule(BasaltBungeePlugin.instance, {
            if (LockdownManager.serverIsOnLockdown()) {
                if (LockdownManager.hasClearance(event.player)) {
                    StaffMessagePacket("&b✓ &a" + event.player.name + " has clearance for " + event.player.server.info.name).action()
                } else {
                    event.player.disconnect(TextComponent(ChatColor.translateAlternateColorCodes('&',"&cServer is on lockdown and you do not have clearance!")))
                }
            }
        }, 100L, TimeUnit.MILLISECONDS)
    }
}
