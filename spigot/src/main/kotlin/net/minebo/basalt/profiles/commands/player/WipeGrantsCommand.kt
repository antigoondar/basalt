package net.minebo.basalt.profiles.commands.player

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import net.minebo.basalt.models.profile.GameProfile
import net.minebo.basalt.service.expirable.RankGrantService
import net.minebo.basalt.util.Chat
import org.bukkit.command.CommandSender
import java.util.concurrent.CompletableFuture

/**
 * Class created on 6/13/2023

 * @author 98ping
 * @project Basalt
 * @website https://solo.to/redis
 */
object WipeGrantsCommand : BaseCommand()
{

    @CommandAlias("wipegrants")
    @CommandCompletion("@gameprofile")
    @CommandPermission("basalt.profiles.admin")
    fun wipeGrants(sender: CommandSender, @Name("target") gameProfile: GameProfile): CompletableFuture<Void>
    {
        val ms = System.currentTimeMillis()
        val future = RankGrantService.findByTarget(gameProfile.uuid).thenAccept { collection ->
            for (found in collection) RankGrantService.remove(found)

            RankGrantService.playerGrants.remove(gameProfile.uuid)
            sender.sendMessage(
                Chat.format(
                    "&aYou manually deleted &f${collection.size} &agrants from this player in &f${
                        System.currentTimeMillis().minus(ms)
                    }ms"
                )
            )
        }

        return future
    }
}