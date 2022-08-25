package net.sakuragame.eternal.justlevel.commands.sub;

import com.taylorswiftcn.justwei.commands.sub.SubCommand;
import net.sakuragame.eternal.justlevel.api.JustLevelAPI;
import net.sakuragame.eternal.justlevel.commands.CommandPerms;
import net.sakuragame.eternal.justlevel.core.user.PlayerLevelData;
import net.sakuragame.eternal.justlevel.file.sub.ConfigFile;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand extends SubCommand {
    @Override
    public String getIdentifier() {
        return "test";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (args.length != 1) return;

        int i = Integer.parseInt(args[0]);
        Player player = this.getPlayer();
        PlayerLevelData account = JustLevelAPI.getUserData(player);
        account.setDailyExpLimit(i);

        sender.sendMessage(ConfigFile.Prefix + "done");
    }

    @Override
    public boolean playerOnly() {
        return true;
    }

    @Override
    public String getPermission() {
        return CommandPerms.ADMIN.getNode();
    }
}
