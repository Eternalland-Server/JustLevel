package net.sakuragame.megumi.justlevel.commands.sub.item;

import com.taylorswiftcn.justwei.commands.sub.SubCommand;
import com.taylorswiftcn.justwei.util.MegumiUtil;
import net.sakuragame.megumi.justlevel.file.sub.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveCommand extends SubCommand {

    @Override
    public String getIdentifier() {
        return "give";
    }

    @Override
    public void perform(CommandSender commandSender, String[] strings) {
        if (strings.length < 3) return;

        String playerID = strings[0];
        String id = strings[1];
        String s = strings[2];

        Player player = Bukkit.getPlayerExact(playerID);
        if (player == null) {
            commandSender.sendMessage(ConfigFile.Prefix + "§c该玩家不在线");
            return;
        }
        if (!MegumiUtil.isNumber(s)) {
            commandSender.sendMessage(ConfigFile.Prefix + "§c无效值: " + s);
            return;
        }

        int amount = Integer.parseInt(s);

        if (!ConfigFile.stone.containsKey(id)) {
            commandSender.sendMessage(ConfigFile.Prefix + "§c没有该物品");
            return;
        }

        ItemStack item = ConfigFile.stone.get(id).clone();
        item.setAmount(amount);

        player.getInventory().addItem(item);
        player.sendMessage(ConfigFile.Prefix + "§a物品已发放到玩家背包");
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
