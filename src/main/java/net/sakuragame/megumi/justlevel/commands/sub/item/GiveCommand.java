package net.sakuragame.megumi.justlevel.commands.sub.item;

import com.taylorswiftcn.justwei.commands.SubCommand;
import com.taylorswiftcn.justwei.util.MegumiUtil;
import net.sakuragame.megumi.justlevel.file.sub.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveCommand extends SubCommand {
    @Override
    public void perform(CommandSender commandSender, String[] strings) {
        if (strings.length < 5) return;

        String playerID = strings[2];
        String id = strings[3];
        String s = strings[4];

        Player player = Bukkit.getPlayerExact(playerID);
        if (player == null) {
            commandSender.sendMessage(ConfigFile.Prefix + "§c该玩家不在线");
            return;
        }
        if (!MegumiUtil.isNumber(s)) return;

        int amount = Integer.parseInt(s);

        if (!ConfigFile.stone.containsKey(id)) {
            commandSender.sendMessage(ConfigFile.Prefix + "§c没有该物品");
            return;
        }

        ItemStack item = ConfigFile.stone.get(id).clone();
        item.setAmount(amount);

        player.getInventory().addItem(item);
        player.sendMessage(ConfigFile.Prefix + "§a获取物品成功");
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
