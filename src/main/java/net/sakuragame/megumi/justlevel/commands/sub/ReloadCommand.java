package net.sakuragame.megumi.justlevel.commands.sub;

import com.taylorswiftcn.justwei.commands.sub.SubCommand;
import net.sakuragame.megumi.justlevel.JustLevel;
import net.sakuragame.megumi.justlevel.commands.CommandPerms;
import net.sakuragame.megumi.justlevel.file.sub.ConfigFile;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends SubCommand {

    private JustLevel plugin;

    public ReloadCommand() {
        this.plugin = JustLevel.getInstance();
    }

    @Override
    public String getIdentifier() {
        return "reload";
    }

    @Override
    public void perform(CommandSender CommandSender, String[] Strings) {
        plugin.reload();
        CommandSender.sendMessage(ConfigFile.Prefix + "§a配置文件已重载");
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public String getPermission() {
        return CommandPerms.ADMIN.getNode();
    }
}
