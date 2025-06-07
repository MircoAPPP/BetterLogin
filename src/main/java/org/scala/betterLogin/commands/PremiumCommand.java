package org.scala.betterLogin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.scala.betterLogin.BetterLogin;
import org.scala.betterLogin.managers.PremiumVerifier;

public class PremiumCommand implements CommandExecutor {
    private final BetterLogin plugin;

    public PremiumCommand(BetterLogin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cSolo i giocatori possono eseguire questo comando.");
            return true;
        }

        PremiumVerifier verifier = plugin.getPremiumVerifier();

        player.sendMessage("§eVerifica in corso...");

        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            boolean isPremium = verifier.isPremium(player.getName());

            plugin.getServer().getScheduler().runTask(plugin, () -> {
                if (isPremium) {
                    plugin.getDataManager().setPremium(player, true);
                    player.sendMessage("§aSei un utente premium. Accesso consentito!");
                } else {
                    player.kickPlayer("§cNon sei un utente premium. Accesso negato.");
                }
            });
        });

        return true;
    }
}
