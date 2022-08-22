package net.sakuragame.eternal.justlevel.commands.sub;

import com.taylorswiftcn.justwei.commands.sub.SubCommand;
import net.sakuragame.eternal.justlevel.JustLevel;
import net.sakuragame.eternal.justlevel.commands.CommandPerms;
import net.sakuragame.eternal.justlevel.file.sub.ConfigFile;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PropCommand extends SubCommand {
    @Override
    public String getIdentifier() {
        return "prop";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (args.length != 4) return;

        Player player = this.getPlayer();
        int type = Integer.parseInt(args[0]);
        int value = Integer.parseInt(args[1]);
        int amount = Integer.parseInt(args[2]);
        int radius = Integer.parseInt(args[3]);

        JustLevel.getPropGenerate().spawn(type, player.getLocation(), value, amount, radius);
        sender.sendMessage(ConfigFile.Prefix + "已生成道具");
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
