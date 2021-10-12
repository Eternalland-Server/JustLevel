package net.sakuragame.megumi.justlevel.commands.sub;

import com.taylorswiftcn.justwei.commands.SubCommand;
import net.sakuragame.megumi.justlevel.commands.MainCommand;
import net.sakuragame.megumi.justlevel.commands.sub.item.GetCommand;
import net.sakuragame.megumi.justlevel.commands.sub.item.GiveCommand;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ItemCommand extends SubCommand implements TabCompleter {

    private Map<String, SubCommand> commands;

    public ItemCommand() {
        this.commands = new HashMap<>();
        this.commands.put("give", new GiveCommand());
        this.commands.put("get", new GetCommand());
    }

    @Override
    public void perform(CommandSender commandSender, String[] strings) {
        SubCommand cmd = MainCommand.getHelp();
        if (strings.length >= 2 && this.commands.containsKey(strings[1])) {
           cmd = commands.get(strings[1]);
        }

        cmd.execute(commandSender, strings);
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public String getPermission() {
        return "justlevel.admin";
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length > 2) return null;

        List<String> keys = new ArrayList<>();
        for (String key : commands.keySet()) {
            SubCommand sub = commands.get(key);
            if (sub.getPermission() == null) continue;
            if (!sender.hasPermission(sub.getPermission())) continue;
            keys.add(key);

        }
        if (args.length == 0) return keys;

        return keys.stream().filter(s -> StringUtils.startsWithIgnoreCase(s, args[0])).collect(Collectors.toList());
    }
}
