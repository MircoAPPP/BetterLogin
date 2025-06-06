package org.scala.betterLogin.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.scala.betterLogin.BetterLogin;

public class PlayerQuitListener implements Listener {
    private final BetterLogin plugin;

    public PlayerQuitListener(BetterLogin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        plugin.getDataManager().handlePlayerQuit(event.getPlayer());
    }
}