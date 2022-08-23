package net.sakuragame.eternal.justlevel.commands.sub;

import com.taylorswiftcn.justwei.commands.sub.SubCommand;
import net.sakuragame.eternal.justlevel.commands.CommandPerms;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class PropItemCommand extends SubCommand {
    @Override
    public String getIdentifier() {
        return "propItem";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (args.length != 3) return;

        Player player = this.getPlayer();
        int type = Integer.parseInt(args[0]);
        int value = Integer.parseInt(args[1]);
        int amount = Integer.parseInt(args[2]);

        ItemStack item = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(Arrays.asList(type + "", value + "", amount + ""));
        meta.setDisplayName("Prop Edit");
        item.setItemMeta(meta);

        player.getInventory().addItem(item);
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
