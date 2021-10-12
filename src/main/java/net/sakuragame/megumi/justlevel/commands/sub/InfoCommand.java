package net.sakuragame.megumi.justlevel.commands.sub;

import com.taylorswiftcn.justwei.commands.SubCommand;
import net.sakuragame.megumi.justlevel.JustLevel;
import net.sakuragame.megumi.justlevel.file.sub.ConfigFile;
import net.sakuragame.megumi.justlevel.file.sub.MessageFile;
import net.sakuragame.megumi.justlevel.level.PlayerLevelData;
import net.sakuragame.megumi.justlevel.util.LevelUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InfoCommand extends SubCommand {

    private JustLevel plugin;

    public InfoCommand() {
        this.plugin = JustLevel.getInstance();
    }

    @Override
    public void perform(CommandSender commandSender, String[] strings) {
        if (strings.length >= 2) {
            String s = strings[1];
            Player player = Bukkit.getPlayerExact(s);
            if (player == null) {
                commandSender.sendMessage(ConfigFile.Prefix + "§c该玩家不在线");
                return;
            }
            sendInfo(player);
        }

        if (strings.length == 1 && commandSender instanceof Player) {
            Player player = getPlayer();
            sendInfo(player);
        }
    }

    private void sendInfo(Player player) {
        PlayerLevelData data = plugin.getPlayerData().get(player.getUniqueId());
        MessageFile.playerInfo.forEach(s -> player.sendMessage(s
                .replace("%level%", String.valueOf(data.getLevel()))
                .replace("%current_exp%", String.valueOf(data.getExp()))
                .replace("%total_exp%", String.valueOf(LevelUtil.getUpgradeRequireExp(data.getLevel())))
                .replace("%stage%", String.valueOf(data.getStage()))
                .replace("%realm%", String.valueOf(data.getRealm()))
                .replace("%stage_points%", String.valueOf(data.getStagePoint()))
                .replace("%realm_points%", String.valueOf(data.getRealmPoint()))
        ));
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public String getPermission() {
        return "justlevel.use";
    }
}
