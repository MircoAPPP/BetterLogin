package org.scala.betterLogin;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.scala.betterLogin.commands.LoginCommand;
import org.scala.betterLogin.commands.PremiumCommand;
import org.scala.betterLogin.commands.RegisterCommand;
import org.scala.betterLogin.listeners.*;
import org.scala.betterLogin.managers.PlayerDataManager;
import org.scala.betterLogin.managers.PremiumVerifier;

public class BetterLogin extends JavaPlugin {
    private PlayerDataManager dataManager;
    private PremiumVerifier premiumVerifier;

    @Override
    public void onEnable() {
        // Inizializza il data manager e il verifier
        this.dataManager = new PlayerDataManager(this);
        this.premiumVerifier = new PremiumVerifier();

        // Registra i comandi
        getCommand("login").setExecutor(new LoginCommand(this));
        getCommand("register").setExecutor(new RegisterCommand(this));
        getCommand("premium").setExecutor(new PremiumCommand(this));

        // Registra i listener
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerBlockListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerInventoryListener(this), this);

        // Task periodico per controllare le sessioni
        getServer().getScheduler().runTaskTimer(this, () -> {
            for (Player player : getServer().getOnlinePlayers()) {
                getDataManager().checkSession(player);
            }
        }, 20 * 60 * 5, 20 * 60 * 5); // Controlla ogni 5 minuti

        // Carica la configurazione
        saveDefaultConfig();

        getLogger().info("BetterLogin abilitato con successo!");
    }

    @Override
    public void onDisable() {
        getLogger().info("BetterLogin disabilitato!");
    }

    public PlayerDataManager getDataManager() {
        return dataManager;
    }

    public PremiumVerifier getPremiumVerifier() {
        return premiumVerifier;
    }
}
