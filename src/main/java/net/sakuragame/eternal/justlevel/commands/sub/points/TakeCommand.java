package net.sakuragame.eternal.justlevel.commands.sub.points;

import com.taylorswiftcn.justwei.commands.sub.SubCommand;
import com.taylorswiftcn.justwei.util.MegumiUtil;
import net.sakuragame.eternal.justlevel.api.JustLevelAPI;
import net.sakuragame.eternal.justlevel.core.level.Define;
import net.sakuragame.eternal.justlevel.file.sub.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TakeCommand extends SubCommand {

    @Override
    public String getIdentifier() {
        return "take";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (args.length < 3) return;

        String s1 = args[0];
        String s2 = args[1];
        String s3 = args[2];

        Player player = Bukkit.getPlayerExact(s1);
        Define define = Define.match(s2);

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

        switch (define) {
            case Realm:
                JustLevelAPI.takeRealmPoints(player, points);
                sender.sendMessage(ConfigFile.Prefix + "§a已减少该玩家 " + s3 + " 境界突破点");
                break;
            case Stage:
                JustLevelAPI.takeStagePoints(player, points);
                sender.sendMessage(ConfigFile.Prefix + "§a已减少该玩家 " + s3 + " 阶段突破点");
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
