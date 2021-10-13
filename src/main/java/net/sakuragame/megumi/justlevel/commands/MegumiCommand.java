package net.sakuragame.megumi.justlevel.commands;

import com.taylorswiftcn.justwei.commands.SubCommand;
import net.sakuragame.megumi.justlevel.commands.sub.HelpCommand;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class MegumiCommand extends SubCommand implements TabCompleter {

    protected Map<String, SubCommand> commands;

    public MegumiCommand() {
        this.commands = new HashMap<>();
    }

    @Override
    public void perform(CommandSender commandSender, String[] strings) {
        SubCommand cmd = new HelpCommand();
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
