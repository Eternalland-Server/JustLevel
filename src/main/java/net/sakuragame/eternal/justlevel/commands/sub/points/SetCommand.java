package net.sakuragame.eternal.justlevel.commands.sub.points;

import com.taylorswiftcn.justwei.commands.sub.SubCommand;
import com.taylorswiftcn.justwei.util.MegumiUtil;
import net.sakuragame.eternal.justlevel.level.LevelDefine;
import net.sakuragame.eternal.justlevel.level.PlayerLevelData;
import net.sakuragame.eternal.justlevel.JustLevel;
import net.sakuragame.eternal.justlevel.file.sub.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetCommand extends SubCommand {

    private final JustLevel plugin;

    public SetCommand() {
        this.plugin = JustLevel.getInstance();
    }

    @Override
    public String getIdentifier() {
        return "set";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (args.length < 3) return;

        String s1 = args[0];
        String s2 = args[1];
        String s3 = args[2];

        Player player = Bukkit.getPlayerExact(s1);
        LevelDefine define = LevelDefine.get(s2);

        if (player == null) {
            sender.sendMessage(ConfigFile.Prefix + "§c该玩家不在线");
            return;
        }

        if (define == null) {
            sender.sendMessage(ConfigFile.Prefix + "§c无效参数: " + s2);
            sender.sendMessage(ConfigFile.Prefix + "§c请使用: stage/realm");
            return;
        }

        if (!MegumiUtil.isNumber(s3)) {
            sender.sendMessage(ConfigFile.Prefix + "§c无效值: " + s3);
            return;
        }

        int points = Integer.parseInt(s3);

        PlayerLevelData data = plugin.getPlayerData().get(player.getUniqueId());

        switch (define) {
            case Realm:
                data.setRealmPoints(points);
                sender.sendMessage(ConfigFile.Prefix + "§a已设置该玩家境界突破点为 " + s3);
                break;
            case Stage:
                data.setStagePoints(points);
                sender.sendMessage(ConfigFile.Prefix + "§a已设置该玩家阶段突破点为 " + s3);
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
