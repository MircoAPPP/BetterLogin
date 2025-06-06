package org.scala.betterLogin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.scala.betterLogin.BetterLogin;

public class LoginCommand implements CommandExecutor {
    private final BetterLogin plugin;

    public LoginCommand(BetterLogin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Solo i giocatori possono eseguire questo comando!");
            return true;
        }

        if (plugin.getDataManager().isLoggedIn(player)) {
            player.sendMessage("Sei già loggato!");
            return true;
        }

        if (plugin.getDataManager().isPremium(player)) {
            player.sendMessage("§aSei già loggato come utente premium!");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage("Utilizzo: /login <password>");
            return true;
        }

        if (!plugin.getDataManager().isRegistered(player)) {
            player.sendMessage("Non sei registrato! Usa /register <password> <confirmPassword>");
            return true;
        }

        if (plugin.getDataManager().loginPlayer(player, args[0])) {
            player.sendMessage("Login effettuato con successo!");
            player.setWalkSpeed(0.2f);
            player.setFlySpeed(0.1f);
            player.setAllowFlight(false);

        } else {
            player.sendMessage("Password errata!");
        }

        return true;
    }
}