package net.sakuragame.eternal.justlevel.commands.sub;

import com.taylorswiftcn.justwei.commands.sub.SubCommand;
import net.sakuragame.eternal.justlevel.api.JustLevelAPI;
import net.sakuragame.eternal.justlevel.commands.CommandPerms;
import net.sakuragame.eternal.justlevel.core.user.PlayerLevelData;
import net.sakuragame.eternal.justlevel.JustLevel;
import net.sakuragame.eternal.justlevel.file.sub.ConfigFile;
import net.sakuragame.eternal.justlevel.file.sub.MessageFile;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class InfoCommand extends SubCommand {

    private final DecimalFormat format;

    public InfoCommand() {
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
        PlayerLevelData account = JustLevelAPI.getUserData(player);
        MessageFile.playerInfo.forEach(s -> player.sendMessage(s
                .replace("%player%", player.getName())
                .replace("%level%", String.valueOf(account.getLevel()))
                .replace("%current_exp%", format.format(account.getExp()))
                .replace("%total_exp%", format.format(account.getUpgradeExp()))
                .replace("%stage%", String.valueOf(account.getStage()))
                .replace("%realm%", String.valueOf(account.getRealm()))
                .replace("%stage_points%", String.valueOf(account.getStagePoints()))
                .replace("%realm_points%", String.valueOf(account.getRealmPoints()))
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
