package net.minebo.basalt.staff.mode.listeners

import net.minebo.basalt.BasaltSpigotPlugin
import net.minebo.basalt.staff.mode.StaffItems
import net.minebo.basalt.staff.mode.StaffSuiteManager
import net.minebo.basalt.util.Chat
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerPickupItemEvent
import org.bukkit.event.player.PlayerQuitEvent

class GenericStaffmodePreventionListener : Listener
{

    @EventHandler
    fun breakBlock(e: BlockBreakEvent)
    {
        val player = e.player

        if (StaffSuiteManager.isModMode(player))
        {
            if (!BasaltSpigotPlugin.instance.config.getBoolean("staffmode.allowPermittedBlockModification") || !player.hasPermission(
                    "basalt.staffmode.edit"
                )
            )
            {
                e.isCancelled = true
            }
        }
    }

    @EventHandler
    fun placeBlock(e: BlockPlaceEvent)
    {
        val player = e.player

        if (StaffSuiteManager.isModMode(player))
        {
            if (!BasaltSpigotPlugin.instance.config.getBoolean("staffmode.allowPermittedBlockModification") || !player.hasPermission(
                    "basalt.staffmode.edit"
                )
            )
            {
                e.isCancelled = true
            }
        }
    }

    @EventHandler
    fun interact(e: PlayerInteractEvent)
    {
        val player = e.player

        if (StaffSuiteManager.isModMode(player))
        {

            if (e.action == Action.PHYSICAL)
            {
                e.isCancelled = true
                return
            }

            if (!player.hasPermission("basalt.staffmode.edit"))
            {
                e.isCancelled = true
            }
        }
    }

    @EventHandler
    fun damage(e: EntityDamageEvent)
    {
        val player = e.entity

        if (player is Player)
        {

            if (StaffSuiteManager.isModMode(player))
            {
                e.isCancelled = true
            }
        }
    }

    @EventHandler
    fun pickup(e: PlayerPickupItemEvent)
    {
        if (StaffSuiteManager.isModMode(e.player))
        {
            e.isCancelled = true
        }
    }

    @EventHandler
    fun drop(e: PlayerDropItemEvent)
    {
        if (StaffSuiteManager.isModMode(e.player))
        {
            val item = e.itemDrop.itemStack
            if (StaffItems.ITEMS_IN_LIST.any { it.isSimilar(item) })
            {
                e.isCancelled = true
                e.player.sendMessage(Chat.format("&cYou cannot drop staffmode related items!"))
                return
            }

            // handle online staff separately
            if (item.hasItemMeta() && item.itemMeta.hasDisplayName() && item.itemMeta.displayName.contains("Online Staff"))
            {
                e.isCancelled = true
                e.player.sendMessage(Chat.format("&cYou cannot drop staffmode related items!"))
                return
            }

            if (!e.player.hasPermission("basalt.staffmode.edit"))
            {
                e.isCancelled = true
            }
        }
    }

    @EventHandler
    fun playerQuit(e: PlayerQuitEvent)
    {
        if (StaffSuiteManager.isModMode(e.player))
        {
            StaffSuiteManager.removeStaffMode(e.player)
        }
    }


    @EventHandler
    fun damagedBy(e: EntityDamageByEntityEvent)
    {
        val player = e.entity
        val damager = e.damager

        if (player is Player && damager is Player)
        {

            if (StaffSuiteManager.isModMode(player))
            {
                e.isCancelled = true
            }
        }
    }

    @EventHandler
    fun damagedFrom(e: EntityDamageByEntityEvent)
    {
        val player = e.entity
        val damager = e.damager

        if (player is Player && damager is Player)
        {

            StaffItems.lastPvP = player.location

            if (StaffSuiteManager.isModMode(damager))
            {
                e.isCancelled = true
            }
        }
    }
}