package net.minebo.basalt.essentials.listener

import net.minebo.basalt.essentials.commands.EntityCommands
import net.minebo.basalt.essentials.menus.entity.EntityEditorMenu
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEntityEvent

class EntityEditorListener : Listener
{

    @EventHandler
    fun onEntityClick(e: PlayerInteractEntityEvent)
    {
        val entity = e.rightClicked ?: return
        val item = e.player.itemInHand

        if (item.isSimilar(EntityCommands.item))
        {
            if (e.player.hasPermission("basalt.essentials.editentity"))
            {
                EntityEditorMenu(e.player, entity).openMenu()
            }
        }
    }
}