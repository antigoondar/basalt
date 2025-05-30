package net.minebo.basalt.servers.menu.sub.menus

import net.minebo.basalt.models.ranks.Rank
import net.minebo.basalt.models.server.UniqueServer
import net.minebo.basalt.redis.AsynchronousRedisSender
import net.minebo.basalt.redis.cache.mutate.UpdateGlobalServerPacket
import net.minebo.basalt.redis.cache.refresh.RefreshServersPacket
import net.minebo.basalt.service.ranks.RankService
import net.minebo.basalt.service.server.UniqueServerService
import net.minebo.basalt.util.Chat
import net.minebo.basalt.util.menu.Button
import net.minebo.basalt.util.menu.pagination.PaginatedMenu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class SelectRankMenu(val player: Player, val server: UniqueServer) : PaginatedMenu(27, player)
{

    override fun getPagesButtons(player: Player): MutableMap<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()
        var index = 0

        for (rank in RankService.getAllRanksInOrder())
        {
            buttons[index++] = RankSelectButton(server, rank)
        }

        return buttons
    }

    override fun getTitle(player: Player): String
    {
        return "Select a Rank"
    }

    class RankSelectButton(val server: UniqueServer, val rank: Rank) : Button()
    {
        override fun getMaterial(player: Player): Material
        {
            return Material.WOOL
        }

        override fun getDescription(player: Player): MutableList<String>
        {
            return mutableListOf()
        }

        override fun getDisplayName(player: Player): String
        {
            return Chat.format(rank.color + rank.displayName)
        }

        override fun getData(player: Player): Short
        {
            return Chat.getDyeColor(rank.color).woolData.toShort()
        }

        override fun onClick(player: Player, slot: Int, type: ClickType)
        {
            server.lockRank = rank.id

            UniqueServerService.save(server)
            AsynchronousRedisSender.send(UpdateGlobalServerPacket(server))
            AsynchronousRedisSender.send(RefreshServersPacket())

            player.sendMessage(Chat.format("&aUpdated the lock rank of " + server.id + " to " + rank.color + rank.displayName))
        }

    }
}