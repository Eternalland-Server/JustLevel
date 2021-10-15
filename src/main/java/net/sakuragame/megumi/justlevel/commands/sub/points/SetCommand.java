package net.sakuragame.megumi.justlevel.commands.sub.points;

import com.taylorswiftcn.justwei.commands.SubCommand;
import com.taylorswiftcn.justwei.util.MegumiUtil;
import net.sakuragame.megumi.justlevel.JustLevel;
import net.sakuragame.megumi.justlevel.file.sub.ConfigFile;
import net.sakuragame.megumi.justlevel.level.PlayerLevelData;
import net.sakuragame.megumi.justlevel.level.TierType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetCommand extends SubCommand {

    private JustLevel plugin;

    public SetCommand() {
        this.plugin = JustLevel.getInstance();
    }

    @Override
    public String getIdentifier() {
        return "set";
    }

    @Override
    public void perform(CommandSender commandSender, String[] strings) {
        if (strings.length < 5) return;

        String s1 = strings[2];
        String s2 = strings[3];
        String s3 = strings[4];

        Player player = Bukkit.getPlayerExact(s1);
        TierType type = TierType.getType(s2);

        if (player == null) {
            commandSender.sendMessage(ConfigFile.Prefix + "§c该玩家不在线");
            return;
        }

        if (type == null) {
            commandSender.sendMessage(ConfigFile.Prefix + "§c无效参数: " + s2);
            commandSender.sendMessage(ConfigFile.Prefix + "§c请使用: stage/realm");
            return;
        }

        if (!MegumiUtil.isNumber(s3)) {
            commandSender.sendMessage(ConfigFile.Prefix + "§c无效值: " + s3);
            return;
        }

        int points = Integer.parseInt(s3);

        PlayerLevelData data = plugin.getPlayerData().get(player.getUniqueId());

        switch (type) {
            case Realm:
                data.setRealmPoints(points);
                commandSender.sendMessage(ConfigFile.Prefix + "§a已设置该玩家境界突破点为 " + s3);
                break;
            case Stage:
                data.setStagePoints(points);
                commandSender.sendMessage(ConfigFile.Prefix + "§a已设置该玩家阶段突破点为 " + s3);
                break;
        }
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public String getPermission() {
        return null;
    }
}