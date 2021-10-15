package net.sakuragame.megumi.justlevel.commands;

import com.taylorswiftcn.justwei.commands.ICommand;
import net.sakuragame.megumi.justlevel.commands.sub.*;
import net.sakuragame.megumi.justlevel.commands.sub.player.*;

public class MainCommand extends ICommand {

    public MainCommand() {
        this.setHelpCommand(new HelpCommand());
        this.register(new InfoCommand());
        this.register(new ItemCommand());
        this.register(new AddExpCommand());
        this.register(new SetExpCommand());
        this.register(new SetLevelCommand());
        this.register(new SetStageCommand());
        this.register( new SetRealmCommand());
        this.register(new PointsCommand());
        this.register(new ReloadCommand());
    }
}
