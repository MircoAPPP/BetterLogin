package org.scala.betterLogin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.scala.betterLogin.BetterLogin;

public class PremiumCommand implements CommandExecutor {
    private final BetterLogin plugin;

    public PremiumCommand(BetterLogin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cSolo i giocatori");
            return true;
        }

        // Verifica se è già premium
        if (plugin.getDataManager().isPremium(player)) {
            player.sendMessage("§eSei già un utente premium!");
            return true;
        }

        // Imposta come premium
        plugin.getDataManager().setPremium(player, true);
        player.sendMessage("§aOra sei un utente premium! Riconnettiti per applicare i cambiamenti.");
        return true;
    }
}