package net.sakuragame.megumi.justlevel.commands.sub.player;

import com.taylorswiftcn.justwei.commands.SubCommand;
import com.taylorswiftcn.justwei.util.MegumiUtil;
import net.sakuragame.megumi.justlevel.JustLevel;
import net.sakuragame.megumi.justlevel.file.sub.ConfigFile;
import net.sakuragame.megumi.justlevel.level.PlayerLevelData;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetRealmCommand extends SubCommand {

    private JustLevel plugin;

    public SetRealmCommand() {
        this.plugin = JustLevel.getInstance();
    }

    @Override
    public void perform(CommandSender commandSender, String[] strings) {
        if (strings.length < 3) return;

        String s1 = strings[1];
        String s2 = strings[2];

        Player player = Bukkit.getPlayerExact(s1);
        if (player == null) {
            commandSender.sendMessage(ConfigFile.Prefix + "§c该玩家不在线");
            return;
        }
        if (!MegumiUtil.isInteger(s2)) return;

        int value = Integer.parseInt(s2);
        PlayerLevelData data = plugin.getPlayerData().get(player.getUniqueId());
        data.setRealm(value);

        commandSender.sendMessage(ConfigFile.Prefix + "§a设置玩家境界成功!");
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public String getPermission() {
        return "justlevel.admin";
    }
}