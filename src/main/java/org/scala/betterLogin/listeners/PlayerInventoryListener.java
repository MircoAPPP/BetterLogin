package org.scala.betterLogin.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.scala.betterLogin.BetterLogin;

public class PlayerInventoryListener implements Listener {
    private final BetterLogin plugin;

    public PlayerInventoryListener(BetterLogin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        // Converti HumanEntity in Player
        if (event.getPlayer() instanceof Player) {
            Player player = (Player) event.getPlayer();
            if (!plugin.getDataManager().isLoggedIn(player)) {
                event.setCancelled(true);
                player.sendMessage("§cDevi fare il login prima di aprire l'inventario!");
            }
        }
    }
}