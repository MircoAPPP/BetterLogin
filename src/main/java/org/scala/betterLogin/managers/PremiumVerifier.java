package org.scala.betterLogin.managers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class PremiumVerifier {

    /**
     * Verifica se un nome utente Minecraft è premium tramite l'API ufficiale Mojang.
     * @param username il nome utente da verificare
     * @return true se l'utente è premium, false altrimenti
     */
    public boolean isPremium(String username) {
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);

            int responseCode = connection.getResponseCode();
            return responseCode == 200;
        } catch (IOException e) {
            e.printStackTrace(); // Puoi loggare nel logger del plugin se vuoi
            return false;
        }
    }
}
