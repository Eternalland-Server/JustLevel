package net.sakuragame.eternal.justlevel.commands.sub.player;

import com.taylorswiftcn.justwei.commands.sub.SubCommand;
import com.taylorswiftcn.justwei.util.MegumiUtil;
import net.sakuragame.eternal.justlevel.JustLevel;
import net.sakuragame.eternal.justlevel.api.JustLevelAPI;
import net.sakuragame.eternal.justlevel.commands.CommandPerms;
import net.sakuragame.eternal.justlevel.file.sub.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddExpCommand extends SubCommand {

    @Override
    public String getIdentifier() {
        return "addExp";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (args.length < 2) return;

        String s1 = args[0];
        String s2 = args[1];

        Player player = Bukkit.getPlayerExact(s1);
        if (player == null) {
            sender.sendMessage(ConfigFile.Prefix + "§c该玩家不在线");
            return;
        }
        if (!MegumiUtil.isFloat(s2)) {
            sender.sendMessage(ConfigFile.Prefix + "§c无效值: " + s2);
            return;
        }

        double value = Double.parseDouble(s2);
        JustLevelAPI.addExp(player, value);

        sender.sendMessage(ConfigFile.Prefix + "§a已增加玩家经验!");
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
