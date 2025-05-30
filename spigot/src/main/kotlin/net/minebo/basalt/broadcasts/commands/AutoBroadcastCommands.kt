package net.minebo.basalt.broadcasts.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.CommandHelp
import co.aikar.commands.ConditionFailedException
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.HelpCommand
import co.aikar.commands.annotation.Name
import co.aikar.commands.annotation.Subcommand
import co.aikar.commands.annotation.Syntax
import net.minebo.basalt.broadcasts.BroadcastContainer
import net.minebo.basalt.broadcasts.BroadcastService
import net.minebo.basalt.broadcasts.menu.BroadcastsEditMenu
import net.minebo.basalt.broadcasts.model.BroadcastMessage
import net.minebo.basalt.util.Chat
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@CommandAlias("auto-broadcasts")
@CommandPermission("basalt.autobroadcasts")
object AutoBroadcastCommands : BaseCommand()
{

    @HelpCommand
    @Syntax("[page]")
    fun onHelp(command: CommandHelp)
    {
        command.showHelp()
    }

    @Subcommand("create")
    fun onCreate(sender: CommandSender, @Name("id") id: String)
    {
        if (BroadcastService.cached() == null)
        {
            BroadcastService.cache(BroadcastContainer())
        }
        
        val cached = BroadcastService.cached()!!

        if (cached.getBroadcastMessage(id) != null)
        {
            throw ConditionFailedException(
                "A broadcast with this id already exists"
            )
        } else
        {
            cached.broadcasts[id] = BroadcastMessage(id)

            BroadcastService.cache(cached)
            sender.sendMessage(Chat.format("&aYou have just created a new auto-broadcast with the id &f${id}&a."))
        }
    }

    @Subcommand("editor")
    fun onEditor(sender: Player)
    {
        BroadcastsEditMenu(sender).updateMenu()
    }
}