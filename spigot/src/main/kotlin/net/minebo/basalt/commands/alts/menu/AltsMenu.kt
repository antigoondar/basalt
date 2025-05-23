package net.minebo.basalt.commands.alts.menu

import net.minebo.basalt.api.BasaltAPI
import net.minebo.basalt.models.profile.GameProfile
import net.minebo.basalt.util.Chat
import net.minebo.basalt.util.menu.Button
import net.minebo.basalt.util.menu.pagination.PaginatedMenu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import java.util.*

class AltsMenu(var player: Player, var target: GameProfile, var alts: MutableList<GameProfile>) :
    PaginatedMenu(18, player)
{
    override fun getPagesButtons(player: Player): MutableMap<Int, Button>
    {
        var buttons = hashMapOf<Int, Button>()

        var index = 0

        alts.remove(target)

        for (alt in alts)
        {
            buttons[index++] = AltButton(alt, target)
        }

        return buttons
    }

    override fun getTitle(player: Player): String
    {
        return Chat.format(BasaltAPI.getRankDisplay(target.uuid) + "&6's Alts")
    }


    class AltButton(val gameProfile: GameProfile, val target: GameProfile) : Button()
    {
        override fun getMaterial(player: Player): Material
        {
            return Material.SKULL_ITEM
        }

        override fun getDescription(player: Player): MutableList<String>
        {
            val desc = mutableListOf<String>()
            desc.add(Chat.format("&eName: &r" + BasaltAPI.getRankDisplay(gameProfile.uuid)))
            desc.add(Chat.format("&eLast Seen: &f" + Date(gameProfile.lastSeenAt)))
            desc.add(Chat.format("&6&m-------------------------------"))
            if (target.ip == gameProfile.ip)
            {
                desc.add(Chat.format("&aCurrently matching ${BasaltAPI.getRankDisplay(target.uuid)}"))
            } else
            {
                desc.add(Chat.format("&cNot Currently Matching ${BasaltAPI.getRankDisplay(target.uuid)}"))
            }

            val punishments = target.getActivePunishmentsFilteredByImportance()
            if (!punishments.isEmpty())
            {
                val firstPunishment = punishments.first()
                desc.add(
                    Chat.format(
                        "${firstPunishment.getGrantable().color}${firstPunishment.getGrantable().niceName} &ais currently matching ${
                            BasaltAPI.getRankDisplay(
                                target.uuid
                            )
                        }"
                    )
                )
            }
            desc.add(Chat.format("&6&m-------------------------------"))
            desc.add(Chat.format(BasaltAPI.getRankDisplay(gameProfile.uuid) + "&e's Current IP data:"))
            desc.add(Chat.format("  &eLast Login: &f" + Date(gameProfile.lastSeenAt)))
            desc.add(" ")
            desc.add(Chat.format(BasaltAPI.getRankDisplay(target.uuid) + "&e's Current IP data:"))
            desc.add(Chat.format("  &eLast Login: &f" + Date(target.lastSeenAt)))

            return desc
        }

        override fun getDisplayName(player: Player): String
        {
            return Chat.format(BasaltAPI.getRankDisplay(gameProfile.uuid))
        }

        override fun getData(player: Player): Short
        {
            return 3
        }

        override fun onClick(player: Player, slot: Int, type: ClickType)
        {

        }

    }
}