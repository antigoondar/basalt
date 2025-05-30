package net.minebo.basalt.convert.luckperms

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import net.minebo.basalt.util.Chat
import org.bukkit.Bukkit
import org.bukkit.entity.Player

/**
 * Class created on 6/13/2023

 * @author 98ping
 * @project Basalt
 * @website https://solo.to/redis
 */
class LuckPermsConverterCommand : BaseCommand()
{

    @CommandAlias("convertluckperms")
    @CommandPermission("basalt.owner")
    fun convert(sender: Player)
    {
        if (Bukkit.getPluginManager().isPluginEnabled("LuckPerms"))
        {
            LuckpermsRankConverter.convert(sender)
        } else
        {
            sender.sendMessage(Chat.format("&cYou must be running &aLuck&2Perms &cto execute this!"))
        }
    }
}