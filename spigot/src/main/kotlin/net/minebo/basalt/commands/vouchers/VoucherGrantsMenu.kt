package net.minebo.basalt.commands.vouchers

import net.minebo.basalt.api.BasaltAPI
import net.minebo.basalt.models.vouchers.VoucherGrant
import net.minebo.basalt.util.Chat
import net.minebo.basalt.util.menu.Button
import net.minebo.basalt.util.menu.pagination.PaginatedMenu
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import java.util.*

class VoucherGrantsMenu(val player: Player, val vouchers: List<VoucherGrant>) : PaginatedMenu(18, player)
{
    override fun getPagesButtons(player: Player): MutableMap<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()
        var i = 0

        for (voucher in vouchers)
        {
            buttons[i++] = VoucherButton(voucher)
        }

        return buttons
    }

    override fun getTitle(player: Player): String
    {
        return "Viewing Your Vouchers"
    }

    class VoucherButton(val voucher: VoucherGrant) : Button()
    {
        override fun getMaterial(player: Player): Material
        {
            return Material.WOOL
        }

        override fun getDescription(player: Player): MutableList<String>
        {
            val desc = mutableListOf<String>()
            desc.add(Chat.format("&6&m-------------------------------------"))
            desc.add(Chat.format("&eFor: &f" + voucher.template.whatFor))
            desc.add(Chat.format("&eWill Expire: &f" + if (voucher.mustRedeemByTime) "&aYes" else "&cNo"))
            if (voucher.mustRedeemByTime)
            {
                desc.add(Chat.format("&eExpires At: &f" + Date(voucher.redeemByDuration).toString()))
            }
            desc.add(Chat.format("&eGiven By: &f" + BasaltAPI.getRankWithPrefix(voucher.executedBy)))
            desc.add(Chat.format("&eCompleted: &f" + if (voucher.completed) "&aYes" else "&cNo"))
            desc.add(" ")
            desc.add(Chat.format("&aLeft-Click to complete this voucher and claim it"))
            desc.add(Chat.format("&6&m-------------------------------------"))

            return desc
        }

        override fun getDisplayName(player: Player): String
        {
            return Chat.format(voucher.template.whatFor)
        }

        override fun getData(player: Player): Short
        {
            if (System.currentTimeMillis() >= voucher.redeemByDuration) {
                return 7
            }

            if (voucher.completed)
            {
                return 14
            }

            return 13
        }

        override fun onClick(player: Player, slot: Int, type: ClickType)
        {
            val completed = voucher.completed
            if (!completed)
            {
                val command = voucher.template.commandToExecute

                Bukkit.dispatchCommand(
                    Bukkit.getConsoleSender(),
                    command.replace("/", "").replace("<target>", player.name)
                )
                voucher.completed = true
                player.sendMessage(Chat.format("&eYou have redeemed a ${voucher.template.whatFor} &evoucher!"))
            } else
            {
                player.sendMessage(Chat.format("&cYou have already completed this voucher or it has expired!"))
            }
        }

    }
}