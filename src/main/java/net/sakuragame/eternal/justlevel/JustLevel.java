package net.sakuragame.eternal.justlevel;

import net.milkbowl.vault.economy.Economy;
import net.sakuragame.eternal.justlevel.commands.MainCommand;
import net.sakuragame.eternal.justlevel.hook.LevelPlaceholder;
import net.sakuragame.eternal.justlevel.level.PlayerLevelData;
import net.sakuragame.eternal.justlevel.listener.ExpListener;
import net.sakuragame.eternal.justlevel.listener.MythicMobListener;
import net.sakuragame.eternal.justlevel.storage.StorageManager;
import net.sakuragame.eternal.justlevel.util.LevelUtil;
import net.sakuragame.eternal.justlevel.file.FileManager;
import net.sakuragame.eternal.justlevel.listener.PlayerListener;
import net.sakuragame.eternal.justlevel.listener.StoneListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JustLevel extends JavaPlugin {
    @Getter private static JustLevel instance;

    @Getter private FileManager fileManager;
    @Getter private StorageManager storageManager;

    @Getter private Economy economy;

    @Getter
    private Map<UUID, PlayerLevelData> playerData;

    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();

        instance = this;

        playerData = new HashMap<>();

        fileManager = new FileManager(this);
        storageManager = new StorageManager(this);
        fileManager.init();
        storageManager.init();
        LevelUtil.conversionExp();
        loadOnlinePlayer();

        new LevelPlaceholder().register();
        setupEconomy();

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new StoneListener(), this);
        Bukkit.getPluginManager().registerEvents(new ExpListener(), this);
        Bukkit.getPluginManager().registerEvents(new MythicMobListener(), this);
        getCommand("jlevel").setExecutor(new MainCommand());

        long end = System.currentTimeMillis();

        getLogger().info("加载成功! 用时 %time% ms".replace("%time%", String.valueOf(end - start)));
    }

    @Override
    public void onDisable() {
        for (PlayerLevelData data : playerData.values()) {
            data.save();
        }
        getLogger().info("卸载成功!");
    }

    public String getVersion() {
        String packet = Bukkit.getServer().getClass().getPackage().getName();
        return packet.substring(packet.lastIndexOf('.') + 1);
    }

    private void setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
        if (rsp != null) {
            economy = rsp.getProvider();
            getLogger().info("Hook vault success!");
        }
        else {
            getLogger().info("Hook vault failed!");
        }
    }

    private void loadOnlinePlayer() {
        Bukkit.getOnlinePlayers().forEach(player -> playerData.put(player.getUniqueId(), storageManager.getPlayerData(player)));
    }

    public void reload() {
        fileManager.init();
        /*LevelUtil.conversionExp();*/
    }
}
