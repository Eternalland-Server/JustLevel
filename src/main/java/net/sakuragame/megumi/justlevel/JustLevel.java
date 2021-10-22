package net.sakuragame.megumi.justlevel;

import net.milkbowl.vault.economy.Economy;
import net.sakuragame.megumi.justlevel.commands.MainCommand;
import net.sakuragame.megumi.justlevel.file.FileManager;
import net.sakuragame.megumi.justlevel.hook.LevelPlaceholder;
import net.sakuragame.megumi.justlevel.level.PlayerLevelData;
import net.sakuragame.megumi.justlevel.listener.ExpListener;
import net.sakuragame.megumi.justlevel.listener.MythicMobListener;
import net.sakuragame.megumi.justlevel.listener.PlayerListener;
import net.sakuragame.megumi.justlevel.listener.StoneListener;
import net.sakuragame.megumi.justlevel.storage.StorageManager;
import net.sakuragame.megumi.justlevel.util.LevelUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
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
        storageManager.updateAllPlayerData();
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
