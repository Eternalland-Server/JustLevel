package net.sakuragame.megumi.justlevel.commands.sub.player;

import com.taylorswiftcn.justwei.commands.sub.SubCommand;
import com.taylorswiftcn.justwei.util.MegumiUtil;
import net.sakuragame.megumi.justlevel.JustLevel;
import net.sakuragame.megumi.justlevel.commands.CommandPerms;
import net.sakuragame.megumi.justlevel.file.sub.ConfigFile;
import net.sakuragame.megumi.justlevel.level.PlayerLevelData;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddExpCommand extends SubCommand {

    private JustLevel plugin;

    public AddExpCommand() {
        this.plugin = JustLevel.getInstance();
    }

    @Override
    public String getIdentifier() {
        return "addExp";
    }

    @Override
    public void perform(CommandSender commandSender, String[] strings) {
        if (strings.length < 2) return;

        String s1 = strings[0];
        String s2 = strings[1];

        Player player = Bukkit.getPlayerExact(s1);
        if (player == null) {
            commandSender.sendMessage(ConfigFile.Prefix + "§c该玩家不在线");
            return;
        }
        if (!MegumiUtil.isFloat(s2)) {
            commandSender.sendMessage(ConfigFile.Prefix + "§c无效值: " + s2);
            return;
        }

        double value = Double.parseDouble(s2);
        PlayerLevelData data = plugin.getPlayerData().get(player.getUniqueId());
        data.addExp(value);

        commandSender.sendMessage(ConfigFile.Prefix + "§a已增加玩家经验!");
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
