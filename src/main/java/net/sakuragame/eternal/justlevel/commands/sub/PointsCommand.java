package net.sakuragame.eternal.justlevel.commands.sub;

import com.taylorswiftcn.justwei.commands.sub.SubTabCompleter;
import eos.moe.dragoncore.commands.sub.HelpCommand;
import net.sakuragame.eternal.justlevel.commands.CommandPerms;
import net.sakuragame.eternal.justlevel.commands.sub.points.AddCommand;
import net.sakuragame.eternal.justlevel.commands.sub.points.SetCommand;
import net.sakuragame.eternal.justlevel.commands.sub.points.TakeCommand;

public class PointsCommand extends SubTabCompleter {

    public PointsCommand() {
        super(new HelpCommand());
        this.register(new AddCommand());
        this.register(new SetCommand());
        this.register(new TakeCommand());
    }

    @Override
    public String getIdentifier() {
        return "points";
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public String getPermission() {
        return CommandPerms.ADMIN.getNode();
    }
}
