package net.minebo.basalt.vault.chat

import net.minebo.basalt.service.profiles.ProfileGameService
import net.minebo.basalt.service.ranks.RankService
import net.milkbowl.vault.chat.Chat
import net.milkbowl.vault.permission.Permission
import org.bukkit.plugin.Plugin
import java.util.*

class VaultChatExtension(perms: Permission, var plugin: Plugin) : Chat(perms)
{

    override fun getName(): String
    {
        return "Basalt"
    }

    override fun isEnabled(): Boolean
    {
        return true
    }

    override fun getPlayerPrefix(p0: String?, p1: String?): String
    {
        var prefix = RankService.FALLBACK_RANK.prefix
        ProfileGameService.byUsernameWithList(p1!!.lowercase(Locale.getDefault())).whenComplete { p, t ->
            val user = p.firstOrNull()
                ?: return@whenComplete

            val curr = user.getCurrentRank()
            prefix = curr.prefix
        }

        return prefix
    }

    override fun setPlayerPrefix(p0: String?, p1: String?, p2: String?)
    {
        throw UnsupportedOperationException("Basalt cannot forcefully set a player prefix!")
    }

    override fun getPlayerSuffix(p0: String?, p1: String?): String
    {
        return ""
    }

    override fun setPlayerSuffix(p0: String?, p1: String?, p2: String?)
    {
        throw UnsupportedOperationException("Basalt cannot handle suffixes!")
    }

    override fun getGroupPrefix(p0: String?, p1: String?): String
    {
        return ""
    }

    override fun setGroupPrefix(p0: String?, p1: String?, p2: String?)
    {
        throw UnsupportedOperationException("Basalt cannot handle group information!")
    }

    override fun getGroupSuffix(p0: String?, p1: String?): String
    {
        throw UnsupportedOperationException("Basalt cannot handle group information!")
    }

    override fun setGroupSuffix(p0: String?, p1: String?, p2: String?)
    {
        throw UnsupportedOperationException("Basalt cannot handle group information!")
    }

    override fun getPlayerInfoInteger(p0: String?, p1: String?, p2: String?, p3: Int): Int
    {
        throw UnsupportedOperationException("Basalt cannot handle information in this type!")
    }

    override fun setPlayerInfoInteger(p0: String?, p1: String?, p2: String?, p3: Int)
    {
        throw UnsupportedOperationException("Basalt cannot handle information in this type!")
    }

    override fun getGroupInfoInteger(p0: String?, p1: String?, p2: String?, p3: Int): Int
    {
        throw UnsupportedOperationException("Basalt cannot handle information in this type!")
    }

    override fun setGroupInfoInteger(p0: String?, p1: String?, p2: String?, p3: Int)
    {
        throw UnsupportedOperationException("Basalt cannot handle information in this type!")
    }

    override fun getPlayerInfoDouble(p0: String?, p1: String?, p2: String?, p3: Double): Double
    {
        throw UnsupportedOperationException("Basalt cannot handle information in this type!")
    }

    override fun setPlayerInfoDouble(p0: String?, p1: String?, p2: String?, p3: Double)
    {
        throw UnsupportedOperationException("Basalt cannot handle information in this type!")
    }

    override fun getGroupInfoDouble(p0: String?, p1: String?, p2: String?, p3: Double): Double
    {
        throw UnsupportedOperationException("Basalt cannot handle information in this type!")
    }

    override fun setGroupInfoDouble(p0: String?, p1: String?, p2: String?, p3: Double)
    {
        throw UnsupportedOperationException("Basalt cannot handle information in this type!")
    }

    override fun getPlayerInfoBoolean(p0: String?, p1: String?, p2: String?, p3: Boolean): Boolean
    {
        throw UnsupportedOperationException("Basalt cannot handle information in this type!")
    }

    override fun setPlayerInfoBoolean(p0: String?, p1: String?, p2: String?, p3: Boolean)
    {
        throw UnsupportedOperationException("Basalt cannot handle information in this type!")
    }

    override fun getGroupInfoBoolean(p0: String?, p1: String?, p2: String?, p3: Boolean): Boolean
    {
        throw UnsupportedOperationException("Basalt cannot handle information in this type!")
    }

    override fun setGroupInfoBoolean(p0: String?, p1: String?, p2: String?, p3: Boolean)
    {
        throw UnsupportedOperationException("Basalt cannot handle information in this type!")
    }

    override fun getPlayerInfoString(p0: String?, p1: String?, p2: String?, p3: String?): String
    {
        throw UnsupportedOperationException("Basalt cannot handle information in this type!")
    }

    override fun setPlayerInfoString(p0: String?, p1: String?, p2: String?, p3: String?)
    {
        throw UnsupportedOperationException("Basalt cannot handle information in this type!")
    }

    override fun getGroupInfoString(p0: String?, p1: String?, p2: String?, p3: String?): String
    {
        throw UnsupportedOperationException("Basalt cannot handle information in this type!")
    }

    override fun setGroupInfoString(p0: String?, p1: String?, p2: String?, p3: String?)
    {
        throw UnsupportedOperationException("Basalt cannot handle information in this type!")
    }
}