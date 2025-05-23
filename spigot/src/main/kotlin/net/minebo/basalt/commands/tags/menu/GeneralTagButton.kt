package net.minebo.basalt.commands.tags.menu

import net.minebo.basalt.api.BasaltAPI
import net.minebo.basalt.models.tags.Tag
import net.minebo.basalt.service.profiles.ProfileGameService
import net.minebo.basalt.util.Chat
import net.minebo.basalt.util.menu.Button
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class GeneralTagButton(var tag: Tag) : Button()
{

    override fun getMaterial(player: Player): Material
    {
        if (tag.displayItem == null)
        {
            return Material.NAME_TAG
        }

        return Material.getMaterial(tag.displayItem!!.uppercase())
    }

    override fun getDescription(player: Player): MutableList<String>
    {
        val desc = arrayListOf<String>()
        val profile = BasaltAPI.syncFindProfile(player.uniqueId)!!
        val rank = profile.getCurrentRank()

        desc.add(Chat.format(" "))
        desc.add(Chat.format("&eCategory:"))
        desc.add(Chat.format("&e│ &f" + (tag.category ?: "Text")))
        desc.add(" ")
        desc.add(Chat.format("&eShows as:"))
        desc.add(Chat.format("&e│ &f" + (tag.prefix) + (rank.prefix) + (player.displayName)))
        desc.add(Chat.format(" "))
        if (profile.canUse(tag))
        {
            desc.add(Chat.format("&aClick to apply tag"))
        } else
        {
            desc.add(Chat.format("&cYou don't own this tag"))
        }

        return desc
    }

    override fun getDisplayName(player: Player): String
    {
        return Chat.format("&e" + tag.menuName)
    }

    override fun getData(player: Player): Short
    {
        return 0
    }

    override fun onClick(player: Player, slot: Int, type: ClickType)
    {
        val playerProfile = ProfileGameService.byId(player.uniqueId)

        if (playerProfile == null)
        {
            player.sendMessage(Chat.format("&cNull profile. Cannot equip prefixes"))
            return
        }

        if (playerProfile.canUse(tag))
        {
            if (!playerProfile.activePrefix.equals(tag.id, ignoreCase = true))
            {
                playerProfile.activePrefix = tag.id
                ProfileGameService.save(playerProfile)
                player.sendMessage(Chat.format("&aSet your tag to " + tag.menuName))
            } else
            {
                playerProfile.activePrefix = null
                ProfileGameService.save(playerProfile)
                player.sendMessage(Chat.format("&cYou have removed your current tag"))
            }
        } else
        {
            player.sendMessage(Chat.format("&cYou are unable to use this tag. " + (if (tag.purchasable) "&cYou may purchase it on our store to use it" else "&cThis prefix is unable to be purchased")))
        }
    }
}