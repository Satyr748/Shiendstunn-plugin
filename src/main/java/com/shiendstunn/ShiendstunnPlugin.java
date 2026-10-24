package com.shiendstunn;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class ShiendstunnPlugin extends JavaPlugin implements CommandExecutor {
    @Override
    public void onEnable() {
        if (getCommand("fgt") != null) {
            getCommand("fgt").setExecutor(this);
        }
        getLogger().info("Shiendstunn wurde aktiviert!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Shiendstunn wurde deaktiviert.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Dieser Befehl kann nur von Spielern verwendet werden.");
            return true;
        }

        if (!player.hasPermission("shiendstunn.fgt")) {
            player.sendMessage(ChatColor.RED + "Du hast keine Berechtigung für /fgt.");
            return true;
        }

        GameMode nextMode = player.getGameMode() == GameMode.SURVIVAL
                ? GameMode.CREATIVE
                : GameMode.SURVIVAL;
        player.setGameMode(nextMode);

        String modeName = nextMode == GameMode.CREATIVE ? "Kreativ" : "Überleben";
        player.sendMessage(ChatColor.GREEN + "Shiendstunn "
                + ChatColor.DARK_GRAY + "» "
                + ChatColor.WHITE + "Spielmodus: "
                + ChatColor.YELLOW + modeName);
        return true;
    }
}
