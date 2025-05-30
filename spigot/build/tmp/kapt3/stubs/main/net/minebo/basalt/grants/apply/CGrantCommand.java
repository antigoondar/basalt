package net.minebo.basalt.grants.apply;

@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0002\u0010\u0003JB\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0001\u0010\b\u001a\u00020\t2\b\b\u0001\u0010\n\u001a\u00020\u000b2\b\b\u0001\u0010\f\u001a\u00020\r2\b\b\u0001\u0010\u000e\u001a\u00020\u000f2\b\b\u0001\u0010\u0010\u001a\u00020\rH\u0007\u00a8\u0006\u0011"}, d2 = {"Lnet/minebo/basalt/grants/apply/CGrantCommand;", "Lco/aikar/commands/BaseCommand;", "<init>", "()V", "ogrant", "", "sender", "Lorg/bukkit/command/CommandSender;", "gameProfile", "Lnet/minebo/basalt/models/profile/GameProfile;", "rank", "Lnet/minebo/basalt/models/ranks/Rank;", "duration", "", "scope", "Lnet/minebo/basalt/models/grant/types/scope/GrantScope;", "reason", "spigot"})
public final class CGrantCommand extends co.aikar.commands.BaseCommand {
    
    public CGrantCommand() {
        super();
    }
    
    @co.aikar.commands.annotation.CommandAlias(value = "cgrant")
    @co.aikar.commands.annotation.CommandPermission(value = "basalt.grants.admin")
    @co.aikar.commands.annotation.CommandCompletion(value = "@gameprofile")
    public final void ogrant(@org.jetbrains.annotations.NotNull()
    org.bukkit.command.CommandSender sender, @co.aikar.commands.annotation.Name(value = "target")
    @org.jetbrains.annotations.NotNull()
    net.minebo.basalt.models.profile.GameProfile gameProfile, @co.aikar.commands.annotation.Name(value = "rank")
    @org.jetbrains.annotations.NotNull()
    net.minebo.basalt.models.ranks.Rank rank, @co.aikar.commands.annotation.Name(value = "duration")
    @org.jetbrains.annotations.NotNull()
    java.lang.String duration, @co.aikar.commands.annotation.Name(value = "scope")
    @org.jetbrains.annotations.NotNull()
    net.minebo.basalt.models.grant.types.scope.GrantScope scope, @co.aikar.commands.annotation.Name(value = "reason")
    @org.jetbrains.annotations.NotNull()
    java.lang.String reason) {
    }
}