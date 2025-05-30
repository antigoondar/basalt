package net.minebo.basalt.essentials.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import co.aikar.commands.annotation.Optional
import co.aikar.commands.bukkit.contexts.OnlinePlayer
import net.minebo.basalt.Basalt
import net.minebo.basalt.redis.AsynchronousRedisSender
import net.minebo.basalt.staff.alerts.StaffActionAlertPacket
import net.minebo.basalt.util.Chat
import org.bukkit.entity.Player

class ClearInventoryCommand : BaseCommand()
{

    @CommandAlias("clear|ci|clearinv")
    @CommandPermission("basalt.essentials.clear")
    fun clear(player: Player, @Name("target") @Optional target: OnlinePlayer?)
    {
        if (target == null)
        {
            player.inventory.clear()
            player.inventory.armorContents = arrayOfNulls(4)
            player.sendMessage(Chat.format("&6You have cleared your &finventory&6."))

            AsynchronousRedisSender.send(StaffActionAlertPacket("has cleared their inventory", player.name, Basalt.globalServer.id))
        } else
        {
            if (!player.hasPermission("basalt.essentials.clear.other"))
            {
                player.sendMessage(Chat.format("&cYou do not have permission to clear other people's inventories!"))
                return
            }

            player.sendMessage(Chat.format(target.player.displayName + " &6has gotten their &finventory &6cleared."))
            target.player.inventory.clear()
            target.player.inventory.armorContents = arrayOfNulls(4)
            target.player.sendMessage(Chat.format("&6Your &finventory &6has been cleared."))

            AsynchronousRedisSender.send(StaffActionAlertPacket("has cleared the inventory of ${target.player.name}", player.name, Basalt.globalServer.id))
        }
    }
}