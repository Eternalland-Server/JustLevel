package com.taylorswiftcn.megumi.justlevel.file;

import com.taylorswiftcn.justwei.file.IConfiguration;
import com.taylorswiftcn.megumi.justlevel.file.sub.ConfigFile;
import com.taylorswiftcn.megumi.justlevel.file.sub.MessageFile;
import com.taylorswiftcn.megumi.justlevel.JustLevel;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;


public class FileManager extends IConfiguration {

    @Getter private YamlConfiguration config;
    @Getter private YamlConfiguration message;

    public FileManager(JustLevel plugin) {
        super(plugin);
    }

    public void init() {
        config = initFile("config.yml");
        message = initFile("message.yml");

        ConfigFile.init();
        MessageFile.init();
    }
}
