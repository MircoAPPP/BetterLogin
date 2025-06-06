package org.scala.betterLogin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerDataManager {
    private final BetterLogin plugin;
    private final Map<UUID, Boolean> loggedInPlayers = new ConcurrentHashMap<>();
    private final Map<UUID, Long> loginTimes = new ConcurrentHashMap<>();

    public PlayerDataManager(BetterLogin plugin) {
        this.plugin = plugin;
    }

    public boolean autoLoginPremium(Player player) {
        if (isPremium(player)) {
            loggedInPlayers.put(player.getUniqueId(), true);
            loginTimes.put(player.getUniqueId(), System.currentTimeMillis());
            return true;
        }
        return false;
    }

    // Registrazione
    public boolean registerPlayer(Player player, String password) {
        if (isRegistered(player)) return false;

        FileConfiguration config = plugin.getConfig();
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        config.set("players." + player.getUniqueId() + ".password", hashedPassword);
        config.set("players." + player.getUniqueId() + ".premium", player.hasPermission("betterlogin.premium"));
        config.set("players." + player.getUniqueId() + ".username", player.getName());

        plugin.saveConfig();
        return true;
    }

    // Login
    public boolean loginPlayer(Player player, String password) {
        // Se è premium, login automatico
        if (isPremium(player)) {
            loggedInPlayers.put(player.getUniqueId(), true);
            loginTimes.put(player.getUniqueId(), System.currentTimeMillis());
            return true;
        }

        // Altrimenti verifica la password
        if (!isRegistered(player)) return false;

        String storedHash = plugin.getConfig().getString("players." + player.getUniqueId() + ".password");
        if (BCrypt.checkpw(password, storedHash)) {
            loggedInPlayers.put(player.getUniqueId(), true);
            loginTimes.put(player.getUniqueId(), System.currentTimeMillis());
            return true;
        }
        return false;
    }
    // Verifica stato
    public boolean isLoggedIn(Player player) {
        // Sessione scaduta dopo 30 minuti
        Long loginTime = loginTimes.get(player.getUniqueId());
        if (loginTime != null && System.currentTimeMillis() - loginTime > 30 * 60 * 1000) {
            logoutPlayer(player);
            return false;
        }
        return loggedInPlayers.containsKey(player.getUniqueId());
    }

    public boolean isRegistered(Player player) {
        return plugin.getConfig().contains("players." + player.getUniqueId());
    }

    public boolean isPremium(Player player) {
        return plugin.getConfig().getBoolean("players." + player.getUniqueId() + ".premium", false);
    }

    // Logout
    public void logoutPlayer(Player player) {
        loggedInPlayers.remove(player.getUniqueId());
        loginTimes.remove(player.getUniqueId());
    }

    public void handlePlayerQuit(Player player) {
        logoutPlayer(player);
    }

    // Controllo sessione
    public void checkSession(Player player) {
        if (!isLoggedIn(player)) {
            player.kickPlayer("§cSessione scaduta. Riconnettiti e fai il login.");
        }
    }

    public void setPremium(Player player, boolean premium) {
        FileConfiguration config = plugin.getConfig();
        config.set("players." + player.getUniqueId() + ".premium", premium);
        plugin.saveConfig();

        // Aggiorna lo stato immediatamente se il giocatore è online
        if (!premium && loggedInPlayers.containsKey(player.getUniqueId())) {
            logoutPlayer(player);
        }
    }



    // Aggiorna stato premium
    public void updatePremiumStatus(Player player) {
        boolean isActuallyPremium = player.hasPermission("betterlogin.premium");
        boolean isRegisteredPremium = isPremium(player);

        if (isActuallyPremium != isRegisteredPremium) {
            setPremium(player, isActuallyPremium);
        }
    }
}