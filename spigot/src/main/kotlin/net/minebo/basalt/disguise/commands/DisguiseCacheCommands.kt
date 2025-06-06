package net.minebo.basalt.disguise.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.CommandHelp
import co.aikar.commands.ConditionFailedException
import co.aikar.commands.annotation.*
import net.minebo.basalt.disguise.DisguiseService
import net.minebo.basalt.util.Chat
import net.pinger.disguise.DisguiseAPI
import net.pinger.disguise.exception.UserNotFoundException
import net.pinger.disguise.skin.Skin
import org.bukkit.command.CommandSender

@CommandAlias("disguisecache")
@CommandPermission("basalt.disguise.admin")
object DisguiseCacheCommands : BaseCommand()
{
    @HelpCommand
    fun help(helpCommand: CommandHelp)
    {
        helpCommand.showHelp()
    }

    @Subcommand("skin clear")
    @Description("Remove all skins from the disguise skin cache")
    fun clearSkins(sender: CommandSender)
    {
        DisguiseService.commonSkins = mutableMapOf()
        DisguiseService.saveAll()
        sender.sendMessage(Chat.format("&aYou have just wiped every skin from the skin cache!"))
    }

    @Subcommand("skin add")
    @Description("Add a skin to the disguise skin cache")
    fun addSkin(sender: CommandSender, @Name("skin") skinName: String)
    {
        val skin: Skin?
        try
        {
            skin = DisguiseAPI.getSkinManager().getFromMojang(skinName)
        } catch (e: UserNotFoundException)
        {
            throw ConditionFailedException(
                "This user does not have a Minecraft account!"
            )
        }

        if (skin!!.value == null)
        {
            throw ConditionFailedException(
                "Contents of this skin is blank!"
            )
        }

        DisguiseService.commonSkins[skinName] = skin
        DisguiseService.saveAll()
        sender.sendMessage(Chat.format("&aYou have added the &e${skinName} &askin to the skin cache!"))
    }
}