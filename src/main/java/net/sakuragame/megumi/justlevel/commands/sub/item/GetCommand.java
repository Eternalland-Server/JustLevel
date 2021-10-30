package net.sakuragame.megumi.justlevel.commands.sub.item;

import com.taylorswiftcn.justwei.commands.sub.SubCommand;
import com.taylorswiftcn.justwei.util.MegumiUtil;
import net.sakuragame.megumi.justlevel.file.sub.ConfigFile;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GetCommand extends SubCommand {

    @Override
    public String getIdentifier() {
        return "get";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (args.length < 2) return;

        String id = args[0];
        String s = args[1];

        if (!MegumiUtil.isNumber(s)) {
            sender.sendMessage(ConfigFile.Prefix + "§c无效值: " + s);
            return;
        }

        int amount = Integer.parseInt(s);

        if (!ConfigFile.stone.containsKey(id)) {
            sender.sendMessage(ConfigFile.Prefix + "§c没有该物品");
            return;
        }

        ItemStack item = ConfigFile.stone.get(id).clone();
        item.setAmount(amount);

        Player player = getPlayer();
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
