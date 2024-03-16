package net.gabr3al.apistop;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public final class Main extends JavaPlugin {

    private HttpServer server;
    static FileConfiguration config;
    @Override
    public void onEnable() {
        loadConfig();
        if(config.getBoolean("enabled")) {
            try {
                // Start the HTTP server
                server = HttpServer.create(new InetSocketAddress(config.getInt("port")), 0);
                server.createContext("/", new APIHandler());
                server.setExecutor(null);
                server.start();
                Bukkit.getConsoleSender().sendMessage("§a§lSTOPAPI §8» §7HTTP-Server started on port §a" + config.getInt("port") + " §7with AuthKey §a" + config.getString("authKey"));
            } catch (IOException e) {
                Bukkit.getConsoleSender().sendMessage("§a§lSTOPAPI §8» §cHTTP-Server was not able to start: " + e.getMessage());
            }
        } else {
            Bukkit.getConsoleSender().sendMessage("§a§lSTOPAPI §8» §cThis Plugin is Disabled!");
        }


    }

    @Override
    public void onDisable() {
        if (server != null) {
            server.stop(0);
            Bukkit.getConsoleSender().sendMessage("§a§lSTOPAPI §8» §7HTTP-Server STOPPED!");
        }
    }

    static class APIHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            Map<String, String> params = queryToMap(exchange.getRequestURI().getQuery());

            if (params.containsKey("auth") && params.get("auth").equals(config.getString("authKey"))) {

                String response = "Request received";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();

                Bukkit.getConsoleSender().sendMessage("§a§lSTOPAPI §8» §c§lSERVER STOPPING REQUESTED");
                Bukkit.getServer().shutdown();
            } else {
                exchange.sendResponseHeaders(403, 0);
                exchange.getResponseBody().close();
            }
        }
    }

    private static Map<String, String> queryToMap(String query) {
        if (query == null)
            return new HashMap<>();
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            }
        }
        return result;
    }

    private void loadConfig() {
        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        File configFile = new File(getDataFolder(), "config.yml");
        if(!configFile.exists()) {
            Bukkit.getConsoleSender().sendMessage("§a§lSTOPAPI §8» §c§lEDIT THE CONFIG AND RESTART THE SERVER");
            saveDefaultConfig();
        }
        config = YamlConfiguration.loadConfiguration(configFile);

        config.addDefault("port", 3000);
        config.addDefault("authKey", "CHANGETHIS");
        config.addDefault("enabled","true");

        config.options().copyDefaults(true);
        saveConfig();
    }
}
