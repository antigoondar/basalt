package net.minebo.basalt.profiles.commands.website;

@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0007\u00a8\u0006\b"}, d2 = {"Lnet/minebo/basalt/profiles/commands/website/RegisterCommand;", "Lco/aikar/commands/BaseCommand;", "<init>", "()V", "onRegister", "", "player", "Lorg/bukkit/entity/Player;", "spigot"})
public final class RegisterCommand extends co.aikar.commands.BaseCommand {
    @org.jetbrains.annotations.NotNull()
    public static final net.minebo.basalt.profiles.commands.website.RegisterCommand INSTANCE = null;
    
    private RegisterCommand() {
        super();
    }
    
    @co.aikar.commands.annotation.CommandAlias(value = "register")
    @co.aikar.commands.annotation.CommandPermission(value = "basalt.website.register")
    public final void onRegister(@org.jetbrains.annotations.NotNull()
    org.bukkit.entity.Player player) {
    }
}