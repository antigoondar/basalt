package net.minebo.basalt.grants.menu.grant

import net.minebo.basalt.BasaltSpigotPlugin
import net.minebo.basalt.grants.GrantConfigurationService
import net.minebo.basalt.models.profile.GameProfile
import net.minebo.basalt.models.ranks.Rank
import net.minebo.basalt.util.Chat
import net.minebo.basalt.util.TimeUtil
import net.minebo.basalt.util.menu.Button
import net.minebo.basalt.util.menu.Menu
import net.minebo.basalt.util.menu.buttons.SimpleActionButton
import org.bukkit.Material
import org.bukkit.conversations.*
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class DurationMenu(val player: Player, val rank: Rank, val target: GameProfile) : Menu(player)
{

    init
    {
        staticSize = BasaltSpigotPlugin.instance.config.getInt("grants.durationMenuSize")
        placeholder = true
    }


    override fun getButtons(player: Player): MutableMap<Int, Button>
    {
        val buttons = hashMapOf<Int, Button>()

        buttons[4] =
            SimpleActionButton(Material.PAPER, mutableListOf(), "&eNavigate Back", 0).setBody { player, i, clickType ->
                GrantMenu(player, target).updateMenu()
            }

        //in case that redis has a stroke
        if (GrantConfigurationService.grantDurationModels.isEmpty())
        {
            for (duration in GrantConfigurationService.getDefaultGrantDurationModels().values)
            {
                buttons[duration.menuSlot] = DurationButton(
                    duration.duration,
                    duration.data.toShort(),
                    duration.displayName,
                    rank,
                    target,
                    duration.item
                )
            }

            return buttons
        }

        //normal processing
        for (duration in GrantConfigurationService.grantDurationModels.values)
        {
            buttons[duration.menuSlot] = DurationButton(
                duration.duration,
                duration.data.toShort(),
                duration.displayName,
                rank,
                target,
                duration.item
            )
        }

        return buttons
    }

    override fun getTitle(player: Player): String
    {
        return "Select a Duration"
    }


    class DurationButton(
        val time: String,
        val data: Short,
        val displayName: String,
        val rank: Rank,
        val target: GameProfile,
        val material: String
    ) : Button()
    {
        override fun getMaterial(player: Player): Material
        {
            return Material.getMaterial(material) ?: Material.WOOL
        }

        override fun getDescription(player: Player): MutableList<String>
        {
            return mutableListOf()
        }

        override fun getDisplayName(player: Player): String
        {
            return Chat.format(displayName)
        }

        override fun getData(player: Player): Short
        {
            return data
        }

        override fun onClick(player: Player, slot: Int, type: ClickType)
        {
            if (time.equals("Custom", ignoreCase = true))
            {
                player.closeInventory()
                val factory =
                    ConversationFactory(BasaltSpigotPlugin.instance).withModality(true).withPrefix(
                        NullConversationPrefix()
                    )
                        .withFirstPrompt(object : StringPrompt()
                        {
                            override fun getPromptText(context: ConversationContext): String
                            {
                                return Chat.format("&ePlease type a duration for this grant, (\"perm\" for permanent), or type &ccancel &eto cancel.")
                            }

                            override fun acceptInput(context: ConversationContext, input: String): Prompt?
                            {
                                return if (input.equals("cancel", ignoreCase = true))
                                {
                                    context.forWhom.sendRawMessage(Chat.format("&cGrant process aborted."))
                                    END_OF_CONVERSATION
                                } else
                                {
                                    var duration = 0L

                                    duration = if (input == "perm" || input.equals("Permanent", ignoreCase = true))
                                    {
                                        Long.MAX_VALUE
                                    } else
                                    {
                                        TimeUtil.parseTime(input).toLong() * 1000L
                                    }

                                    if (duration <= 0)
                                    {
                                        player.sendMessage(Chat.format("&cInvalid time, grant process aborted."))
                                        return END_OF_CONVERSATION
                                    }

                                    ReasonMenu(player, rank, target, duration).openMenu()

                                    END_OF_CONVERSATION
                                }
                            }
                        }).withEscapeSequence("/no").withLocalEcho(false).withTimeout(10)
                        .thatExcludesNonPlayersWithMessage("Go away evil console!")
                val con: Conversation = factory.buildConversation(player)
                player.beginConversation(con)
            } else if (time.equals("Permanent", ignoreCase = true))
            {
                ReasonMenu(player, rank, target, Long.MAX_VALUE).openMenu()
            } else
            {
                ReasonMenu(player, rank, target, TimeUtil.parseTime(time) * 1000L).openMenu()
            }
        }

    }
}