package net.minebo.basalt.friends.filter.button

import net.minebo.basalt.friends.filter.FriendFilter
import net.minebo.basalt.friends.menus.FriendsListMenu
import net.minebo.basalt.models.profile.GameProfile
import net.minebo.basalt.util.Chat
import net.minebo.basalt.util.menu.Button
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class FilterButton(val currentFilter: FriendFilter, val profile: GameProfile) : Button()
{
    val values = FriendFilter.values()

    override fun getMaterial(player: Player): Material
    {
        return Material.HOPPER
    }

    override fun getDescription(player: Player): MutableList<String>
    {
        val desc = mutableListOf<String>()
        desc.add(" ")
        for (filter in values)
        {
            if (currentFilter == filter)
            {
                desc.add(Chat.format("&7- &a" + currentFilter.displayName))
            } else
            {
                desc.add(Chat.format("&7- &7" + filter.displayName))
            }
        }
        desc.add(" ")
        desc.add(Chat.format("&7Click to move to next filter!"))
        desc.add(" ")

        return desc
    }

    override fun getDisplayName(player: Player): String
    {
        return Chat.format("&eFilter")
    }

    override fun getData(player: Player): Short
    {
        return 0
    }

    override fun onClick(player: Player, slot: Int, type: ClickType)
    {
        val index = values.indexOf(currentFilter)
        val next = (index + 1)
        val limit = values.size - 1

        if (next > limit)
        {
            FriendsListMenu(player, profile, values[0]).updateMenu()

            return
        }

        FriendsListMenu(player, profile, values[next]).updateMenu()
    }
}