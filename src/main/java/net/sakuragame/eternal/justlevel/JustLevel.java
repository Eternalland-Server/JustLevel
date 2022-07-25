package net.sakuragame.eternal.justlevel;

import lombok.Getter;
import net.sakuragame.eternal.justlevel.commands.MainCommand;
import net.sakuragame.eternal.justlevel.core.MultiExpManager;
import net.sakuragame.eternal.justlevel.core.UserManager;
import net.sakuragame.eternal.justlevel.file.FileManager;
import net.sakuragame.eternal.justlevel.hook.LevelPlaceholder;
import net.sakuragame.eternal.justlevel.listener.*;
import net.sakuragame.eternal.justlevel.storage.StorageManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class JustLevel extends JavaPlugin {
    @Getter private static JustLevel instance;

    @Getter private static FileManager fileManager;
    @Getter private static StorageManager storageManager;
    @Getter private static UserManager userManager;
    @Getter private static MultiExpManager multiExpManager;

    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();

        instance = this;

        getLogger().info("初始化文件...");
        fileManager = new FileManager(this);
        fileManager.init();

        getLogger().info("初始化数据库...");
        storageManager = new StorageManager();
        storageManager.init();

        getLogger().info("初始化用户管理...");
        userManager = new UserManager(this);

        getLogger().info("初始化全服多倍经验...");
        multiExpManager = new MultiExpManager();

        getLogger().info("注册PAPI变量...");
        new LevelPlaceholder().register();

        getLogger().info("注册事件...");
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new PropListener(), this);
        Bukkit.getPluginManager().registerEvents(new CardListener(), this);
        Bukkit.getPluginManager().registerEvents(new ExpListener(), this);
        Bukkit.getPluginManager().registerEvents(new MythicMobListener(), this);
        Bukkit.getPluginManager().registerEvents(new UIListener(), this);

        getLogger().info("注册命令...");
        getCommand("jlevel").setExecutor(new MainCommand());

        long end = System.currentTimeMillis();

        getLogger().info("加载成功! 用时 %time% ms".replace("%time%", String.valueOf(end - start)));
    }

    @Override
    public void onDisable() {
        getLogger().info("卸载成功!");
    }

    public String getVersion() {
        String packet = Bukkit.getServer().getClass().getPackage().getName();
        return packet.substring(packet.lastIndexOf('.') + 1);
    }

    public void reload() {
        fileManager.init();
    }
}
