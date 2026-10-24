
package com.shiendstunn;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class ShiendstunnPlugin extends JavaPlugin
        implements CommandExecutor, Listener {

    private final Set<UUID> protectedPlayers = new HashSet<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();

        for (String uuid : getConfig().getStringList("protected-players")) {
            try {
                protectedPlayers.add(UUID.fromString(uuid));
            } catch (IllegalArgumentException exception) {
                getLogger().warning("Ungültige UUID in der Schutzliste: " + uuid);
            }
        }

        if (getCommand("fgt") != null) {
            getCommand("fgt").setExecutor(this);
        }

        if (getCommand("dwg") != null) {
            getCommand("dwg").setExecutor(this);
        }

        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("Shiendstunn wurde aktiviert!");
    }

    @Override
    public void onDisable() {
        saveProtectedPlayers();
        getLogger().info("Shiendstunn wurde deaktiviert.");
    }

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {
        if (command.getName().equalsIgnoreCase("fgt")) {
            return handleFgt(sender);
        }

        if (command.getName().equalsIgnoreCase("dwg")) {
            return handleDwg(sender, args);
        }

        return false;
    }

    private boolean handleFgt(CommandSender sender) {
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

        String modeName = nextMode == GameMode.CREATIVE
                ? "Kreativ"
                : "Überleben";

        player.sendMessage(ChatColor.GREEN + "Shiendstunn "
                + ChatColor.DARK_GRAY + "» "
                + ChatColor.WHITE + "Spielmodus: "
                + ChatColor.YELLOW + modeName);

        return true;
    }

    private boolean handleDwg(CommandSender sender, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.YELLOW + "Verwendung: /dwg <Spielername>");
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        UUID targetId = target.getUniqueId();

        if (protectedPlayers.remove(targetId)) {
            saveProtectedPlayers();
            sender.sendMessage(ChatColor.GREEN + args[0]
                    + " ist nicht mehr durch Shiendstunn geschützt.");
        } else {
            protectedPlayers.add(targetId);
            saveProtectedPlayers();
            sender.sendMessage(ChatColor.GREEN + args[0]
                    + " ist jetzt durch Shiendstunn geschützt.");
        }

        return true;
    }

    private void saveProtectedPlayers() {
        getConfig().set(
                "protected-players",
                protectedPlayers.stream().map(UUID::toString).toList()
        );
        saveConfig();
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        if (!protectedPlayers.contains(event.getPlayer().getUniqueId())) {
            return;
        }

        if (event.getResult() == PlayerLoginEvent.Result.KICK_BANNED) {
            event.allow();
            event.getPlayer().sendMessage(
                    ChatColor.GREEN + "Shiendstunn: Dein Bann wurde beim Login übergangen."
            );
        }
    }
}
