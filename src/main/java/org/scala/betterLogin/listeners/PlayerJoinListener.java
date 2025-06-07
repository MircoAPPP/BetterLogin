package org.scala.betterLogin.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.scala.betterLogin.BetterLogin;
import org.scala.betterLogin.managers.PlayerDataManager;

public class PlayerJoinListener implements Listener {
    private final BetterLogin plugin;

    public PlayerJoinListener(BetterLogin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerDataManager data = plugin.getDataManager();

        // Prova login automatico se premium
        if (data.isPremium(player)) {
            data.autoLoginPremium(player);
            player.sendMessage("§aAccesso premium automatico!");
            return;
        }

        // Se non premium ma registrato
        if (data.isRegistered(player)) {
            player.sendMessage("§eFai /login <password>");
        } else {
            player.sendMessage("§eFai /register <password> <confirm>");
        }

        // Blocca azioni finché non fa login
        player.setWalkSpeed(0);
        player.setFlySpeed(0);
    }
}