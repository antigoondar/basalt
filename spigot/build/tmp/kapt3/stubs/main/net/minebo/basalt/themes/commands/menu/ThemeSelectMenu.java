package net.minebo.basalt.themes.commands.menu;

@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u000eB\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u001c\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t2\u0006\u0010\u0002\u001a\u00020\u0003H\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u0002\u001a\u00020\u0003H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u000f"}, d2 = {"Lnet/minebo/basalt/themes/commands/menu/ThemeSelectMenu;", "Lnet/minebo/basalt/util/menu/Menu;", "player", "Lorg/bukkit/entity/Player;", "<init>", "(Lorg/bukkit/entity/Player;)V", "getPlayer", "()Lorg/bukkit/entity/Player;", "getButtons", "", "", "Lnet/minebo/basalt/util/menu/Button;", "getTitle", "", "ThemeButton", "spigot"})
public final class ThemeSelectMenu extends net.minebo.basalt.util.menu.Menu {
    @org.jetbrains.annotations.NotNull()
    private final org.bukkit.entity.Player player = null;
    
    public ThemeSelectMenu(@org.jetbrains.annotations.NotNull()
    org.bukkit.entity.Player player) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final org.bukkit.entity.Player getPlayer() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.util.Map<java.lang.Integer, net.minebo.basalt.util.menu.Button> getButtons(@org.jetbrains.annotations.NotNull()
    org.bukkit.entity.Player player) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String getTitle(@org.jetbrains.annotations.NotNull()
    org.bukkit.entity.Player player) {
        return null;
    }
    
    @kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\n\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0016\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\n\u001a\u00020\u000bH\u0016J \u0010\u0012\u001a\u00020\u00132\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u0018"}, d2 = {"Lnet/minebo/basalt/themes/commands/menu/ThemeSelectMenu$ThemeButton;", "Lnet/minebo/basalt/util/menu/Button;", "theme", "Lnet/minebo/basalt/themes/Theme;", "<init>", "(Lnet/minebo/basalt/themes/Theme;)V", "getTheme", "()Lnet/minebo/basalt/themes/Theme;", "getMaterial", "Lorg/bukkit/Material;", "player", "Lorg/bukkit/entity/Player;", "getDescription", "", "", "getDisplayName", "getData", "", "onClick", "", "slot", "", "type", "Lorg/bukkit/event/inventory/ClickType;", "spigot"})
    public static final class ThemeButton extends net.minebo.basalt.util.menu.Button {
        @org.jetbrains.annotations.NotNull()
        private final net.minebo.basalt.themes.Theme theme = null;
        
        public ThemeButton(@org.jetbrains.annotations.NotNull()
        net.minebo.basalt.themes.Theme theme) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final net.minebo.basalt.themes.Theme getTheme() {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public org.bukkit.Material getMaterial(@org.jetbrains.annotations.NotNull()
        org.bukkit.entity.Player player) {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.util.List<java.lang.String> getDescription(@org.jetbrains.annotations.NotNull()
        org.bukkit.entity.Player player) {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String getDisplayName(@org.jetbrains.annotations.NotNull()
        org.bukkit.entity.Player player) {
            return null;
        }
        
        @java.lang.Override()
        public short getData(@org.jetbrains.annotations.NotNull()
        org.bukkit.entity.Player player) {
            return 0;
        }
        
        @java.lang.Override()
        public void onClick(@org.jetbrains.annotations.NotNull()
        org.bukkit.entity.Player player, int slot, @org.jetbrains.annotations.NotNull()
        org.bukkit.event.inventory.ClickType type) {
        }
    }
}