package com.taylorswiftcn.megumi.justlevel;

import com.taylorswiftcn.megumi.justlevel.commands.MainCommand;
import com.taylorswiftcn.megumi.justlevel.file.FileManager;
import com.taylorswiftcn.megumi.justlevel.level.PlayerLevelData;
import com.taylorswiftcn.megumi.justlevel.storage.StorageManager;
import com.taylorswiftcn.megumi.justlevel.util.LevelUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JustLevel extends JavaPlugin {
    @Getter private static JustLevel instance;

    @Getter private FileManager fileManager;
    @Getter private StorageManager storageManager;

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
}
