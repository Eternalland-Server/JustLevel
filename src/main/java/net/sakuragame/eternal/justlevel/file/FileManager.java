package net.sakuragame.eternal.justlevel.file;

import com.taylorswiftcn.justwei.file.JustConfiguration;
import net.sakuragame.eternal.justlevel.JustLevel;
import net.sakuragame.eternal.justlevel.file.sub.ConfigFile;
import net.sakuragame.eternal.justlevel.file.sub.MessageFile;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;


public class FileManager extends JustConfiguration {

    @Getter private YamlConfiguration config;
    @Getter private YamlConfiguration message;

    public FileManager(JustLevel plugin) {
        super(plugin);
    }

    @Override
    public void init() {
        config = initFile("config.yml");
        message = initFile("message.yml");

        ConfigFile.init();
        MessageFile.init();
    }
}
