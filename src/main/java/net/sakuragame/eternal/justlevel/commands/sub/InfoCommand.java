package net.sakuragame.eternal.justlevel.commands.sub;

import com.taylorswiftcn.justwei.commands.sub.SubCommand;
import net.sakuragame.eternal.justlevel.commands.CommandPerms;
import net.sakuragame.eternal.justlevel.level.PlayerLevelData;
import net.sakuragame.eternal.justlevel.util.LevelUtil;
import net.sakuragame.eternal.justlevel.JustLevel;
import net.sakuragame.eternal.justlevel.file.sub.ConfigFile;
import net.sakuragame.eternal.justlevel.file.sub.MessageFile;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class InfoCommand extends SubCommand {

    private final JustLevel plugin;
    private final DecimalFormat format;

    public InfoCommand() {
        this.plugin = JustLevel.getInstance();
        this.format = new DecimalFormat("#.#");
    }

    @Override
    public String getIdentifier() {
        return "info";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (args.length >= 1) {
            String s = args[0];
            Player player = Bukkit.getPlayerExact(s);
            if (player == null) {
                sender.sendMessage(ConfigFile.Prefix + "§c该玩家不在线");
                return;
            }
            sendInfo(player);
            return;
        }

        if (sender instanceof Player) {
            Player player = getPlayer();
            sendInfo(player);
        }
    }

    private void sendInfo(Player player) {
        PlayerLevelData data = plugin.getPlayerData().get(player.getUniqueId());
        MessageFile.playerInfo.forEach(s -> player.sendMessage(s
                .replace("%player%", player.getName())
                .replace("%level%", String.valueOf(data.getLevel()))
                .replace("%current_exp%", format.format(data.getExp()))
                .replace("%total_exp%", String.valueOf(LevelUtil.getUpgradeRequireExp(data.getLevel())))
                .replace("%stage%", String.valueOf(data.getStage()))
                .replace("%realm%", String.valueOf(data.getRealm()))
                .replace("%stage_points%", String.valueOf(data.getStagePoints()))
                .replace("%realm_points%", String.valueOf(data.getRealmPoints()))
        ));
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public String getPermission() {
        return CommandPerms.USER.getNode();
    }
}
