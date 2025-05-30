package net.minebo.basalt.commands.coins.category.editor.specific

/**
 * Class created on 8/23/2023

 * @author 98ping
 * @project Basalt
 * @website https://solo.to/redis
 */
import net.minebo.basalt.commands.coins.CoinShopManager
import net.minebo.basalt.commands.coins.category.CoinShopCategory
import net.minebo.basalt.commands.coins.category.editor.EditCategoryAttributesMenu
import net.minebo.basalt.models.server.UniqueServer
import net.minebo.basalt.service.server.UniqueServerService
import net.minebo.basalt.util.Chat
import net.minebo.basalt.util.menu.Button
import net.minebo.basalt.util.menu.buttons.SimpleActionButton
import net.minebo.basalt.util.menu.pagination.PaginatedMenu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class SelectCategoryServers(val player: Player, val coinShopItem: CoinShopCategory) : PaginatedMenu(18, player)
{

    override fun getHeaderItems(player: Player): MutableMap<Int, Button>
    {
        return mutableMapOf(
            4 to SimpleActionButton(Material.PAPER, mutableListOf(), "&cGo Back", 0).setBody { player, i, clickType ->
                EditCategoryAttributesMenu(player, coinShopItem).openMenu()
            }
        )
    }

    override fun getPagesButtons(player: Player): MutableMap<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()
        var i = 0

        for (server in UniqueServerService.servers.values)
        {
            buttons[i++] = ServerButton(server, coinShopItem)
        }

        return buttons
    }

    override fun getTitle(player: Player): String
    {
        return "Select a Server"
    }


    class ServerButton(val server: UniqueServer, val item: CoinShopCategory) : Button()
    {
        override fun getMaterial(player: Player): Material
        {
            return Material.BOOK
        }

        override fun getDescription(player: Player): MutableList<String>
        {
            return mutableListOf()
        }

        override fun getDisplayName(player: Player): String
        {
            return Chat.format((if (item.activeOn.contains(server.id)) "&a&l" else "&c&l") + server.displayName)
        }

        override fun getData(player: Player): Short
        {
            return 0
        }

        override fun onClick(player: Player, slot: Int, type: ClickType)
        {
            if (item.activeOn.contains(server.id))
            {
                item.activeOn.remove(server.id)
            } else
            {
                item.activeOn.add(server.id)
            }

            CoinShopManager.saveCategory(item)
            player.sendMessage(Chat.format("&eUpdated active servers of ${item.displayName}"))
            SelectCategoryServers(player, item).updateMenu()
        }

    }
}