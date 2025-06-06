package org.scala.betterLogin.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.scala.betterLogin.BetterLogin;

public class PlayerBlockListener implements Listener {
    private final BetterLogin plugin;

    public PlayerBlockListener(BetterLogin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location from = event.getFrom();
        Location to = event.getTo();

        if (to != null && (!plugin.getDataManager().isLoggedIn(player))) {
            if (from.getX() != to.getX() || from.getZ() != to.getZ()) {
                event.setTo(from);
            }
        }

        if (!plugin.getDataManager().isLoggedIn(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!plugin.getDataManager().isLoggedIn(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!plugin.getDataManager().isLoggedIn(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!plugin.getDataManager().isLoggedIn(event.getPlayer())) {
            event.setCancelled(true);
        }
    }
}