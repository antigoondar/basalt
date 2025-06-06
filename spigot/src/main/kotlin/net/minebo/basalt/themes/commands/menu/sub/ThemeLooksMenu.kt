package net.minebo.basalt.themes.commands.menu.sub

import net.minebo.basalt.service.profiles.ProfileGameService
import net.minebo.basalt.themes.Theme
import net.minebo.basalt.themes.ThemeLoader
import net.minebo.basalt.themes.commands.menu.sub.module.GrantThemeButton
import net.minebo.basalt.themes.commands.menu.sub.module.GrantsThemeButton
import net.minebo.basalt.themes.commands.menu.sub.module.HistoryPlaceholderButton
import net.minebo.basalt.themes.commands.menu.sub.module.PunishmentsButton
import net.minebo.basalt.util.Chat
import net.minebo.basalt.util.menu.Button
import net.minebo.basalt.util.menu.Menu
import net.minebo.basalt.util.menu.buttons.SimpleActionButton
import org.bukkit.Material
import org.bukkit.entity.Player

class ThemeLooksMenu(val player: Player, val theme: Theme) : Menu(player)
{

    init
    {
        staticSize = 27
        placeholder = true
    }


    override fun getButtons(player: Player): MutableMap<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()

        buttons[4] = SimpleActionButton(
            Material.NETHER_STAR,
            mutableListOf(),
            Chat.format("&aClick to set this as your theme!"),
            0
        ).setBody { player, i, clickType ->

            ThemeLoader.setFallbackTheme(theme)

            player.closeInventory()
            player.sendMessage(Chat.format("&aYou have set a new theme!"))
        }

        buttons[10] = GrantsThemeButton(theme, player)
        buttons[11] = PunishmentsButton(theme, player)
        buttons[12] = HistoryPlaceholderButton(theme, player, ProfileGameService.byId(player.uniqueId)!!)
        buttons[13] = GrantThemeButton(theme, player)
        return buttons
    }

    override fun getTitle(player: Player): String
    {
        return "Observe Looks"
    }
}