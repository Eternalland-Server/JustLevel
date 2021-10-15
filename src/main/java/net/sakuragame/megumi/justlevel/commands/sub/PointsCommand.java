package net.sakuragame.megumi.justlevel.commands.sub;

import net.sakuragame.megumi.justlevel.commands.MegumiCommand;
import net.sakuragame.megumi.justlevel.commands.sub.points.AddCommand;
import net.sakuragame.megumi.justlevel.commands.sub.points.SetCommand;
import net.sakuragame.megumi.justlevel.commands.sub.points.TakeCommand;

public class PointsCommand extends MegumiCommand {

    public PointsCommand() {
        this.commands.put("add", new AddCommand());
        this.commands.put("set", new SetCommand());
        this.commands.put("take", new TakeCommand());
    }

    @Override
    public String getIdentifier() {
        return "points";
    }
}
