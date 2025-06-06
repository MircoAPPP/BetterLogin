package org.scala.betterLogin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.scala.betterLogin.BetterLogin;

public class RegisterCommand implements CommandExecutor {
    private final BetterLogin plugin;

    public RegisterCommand(BetterLogin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Solo i giocatori possono eseguire questo comando!");
            return true;
        }

        if (plugin.getDataManager().isRegistered(player)) {
            player.sendMessage("Sei già registrato! Usa /login <password>");
            return true;
        }

        if (args.length != 2) {
            player.sendMessage("Utilizzo: /register <password> <confirmPassword>");
            return true;
        }

        if (!args[0].equals(args[1])) {
            player.sendMessage("Le password non corrispondono!");
            return true;
        }

        if (args[0].length() < 6) {
            player.sendMessage("La password deve essere lunga almeno 6 caratteri!");
            return true;
        }

        plugin.getDataManager().registerPlayer(player, args[0]);
        player.sendMessage("Registrazione completata! Ora puoi fare /login <password>");
        return true;
    }
}