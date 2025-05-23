package net.minebo.basalt.staff.mode

import net.minebo.basalt.BasaltSpigotPlugin
import net.minebo.basalt.client.LunarClientExtension
import net.minebo.basalt.client.feature.NameTagFeature
import net.minebo.basalt.client.feature.TeamViewFeature
import net.minebo.basalt.service.profiles.ProfileGameService
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.metadata.FixedMetadataValue
import java.util.*

object StaffSuiteManager
{

    var modInventories = hashMapOf<UUID, Array<ItemStack?>>()
    var modArmor = hashMapOf<UUID, Array<ItemStack?>>()

    var modModePlayers = arrayListOf<UUID>()

    fun isModMode(player: Player): Boolean
    {
        return modModePlayers.contains(player.uniqueId)
    }

    fun removeStaffMode(player: Player)
    {
        player.gameMode = GameMode.SURVIVAL
        player.inventory.clear()

        if (modInventories.containsKey(player.uniqueId))
        {
            val items = modInventories[player.uniqueId]!!

            player.inventory.contents = items
        }

        if (modArmor.containsKey(player.uniqueId))
        {

            val armor = modArmor[player.uniqueId]!!

            player.inventory.armorContents = armor
        }

        player.updateInventory()

        modModePlayers.remove(player.uniqueId)
        modInventories.remove(player.uniqueId)
        modArmor.remove(player.uniqueId)

        StaffSuiteVisibilityHandler.onDisableVisbility(player)


        if (LunarClientExtension.getModularConfigOption())
        {
            TeamViewFeature.clearTeamView(player)
            NameTagFeature.removeNameTag(player)
        }

        player.removeMetadata("modmode", BasaltSpigotPlugin.instance)
        player.removeMetadata("vanish", BasaltSpigotPlugin.instance)

    }

    fun hasStaffchatEnabled(player: Player): Boolean
    {
        val profile = ProfileGameService.byId(player.uniqueId) ?: return true

        return !profile.hasMetadata("toggleSC")
    }

    fun isModModeOnJoin(player: Player): Boolean
    {
        val profile = ProfileGameService.byId(player.uniqueId) ?: return true

        return !profile.hasMetadata("toggleMM")
    }

    fun setStaffMode(player: Player)
    {
        player.gameMode = GameMode.CREATIVE
        player.health = 20.0

        modInventories[player.uniqueId] = player.inventory.contents
        modArmor[player.uniqueId] = player.inventory.armorContents
        modModePlayers.add(player.uniqueId)

        player.inventory.clear()
        player.inventory.armorContents = null

        StaffItems.equip(player)

        player.updateInventory()

        StaffSuiteVisibilityHandler.onEnableVisibility(player)

        player.setMetadata("modmode", FixedMetadataValue(BasaltSpigotPlugin.instance, true))
        player.setMetadata("vanish", FixedMetadataValue(BasaltSpigotPlugin.instance, true))
    }
}