package net.minebo.basalt.themes.commands.menu

import net.minebo.basalt.themes.Theme
import net.minebo.basalt.themes.ThemeLoader
import net.minebo.basalt.themes.commands.menu.sub.ThemeLooksMenu
import net.minebo.basalt.util.Chat
import net.minebo.basalt.util.menu.Button
import net.minebo.basalt.util.menu.Menu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class ThemeSelectMenu(val player: Player) : Menu(player)
{

    init
    {
        staticSize = 27
        placeholder = true
    }

    override fun getButtons(player: Player): MutableMap<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()

        buttons[10] = ThemeButton(ThemeLoader.themes["MMC"]!!)
        buttons[11] = ThemeButton(ThemeLoader.themes["hydrogen"]!!)
        buttons[12] = ThemeButton(ThemeLoader.themes["neutron"]!!)
        buttons[13] = ThemeButton(ThemeLoader.themes["mcore"]!!)
        buttons[14] = ThemeButton(ThemeLoader.themes["custom"]!!)

        return buttons
    }

    override fun getTitle(player: Player): String
    {
        return "Select a Theme"
    }

    class ThemeButton(val theme: Theme) : Button()
    {
        override fun getMaterial(player: Player): Material
        {
            return theme.material
        }

        override fun getDescription(player: Player): MutableList<String>
        {
            return theme.lore.map { Chat.format(it) }.toMutableList()
        }

        override fun getDisplayName(player: Player): String
        {
            return Chat.format(theme.displayName)
        }

        override fun getData(player: Player): Short
        {
            return theme.data
        }

        override fun onClick(player: Player, slot: Int, type: ClickType)
        {
            ThemeLooksMenu(player, theme).openMenu()
        }


    }
}