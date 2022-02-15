package net.sakuragame.eternal.justlevel.commands;

import com.taylorswiftcn.justwei.commands.JustCommand;
import net.sakuragame.eternal.justlevel.commands.sub.*;
import net.sakuragame.eternal.justlevel.commands.sub.player.*;

public class MainCommand extends JustCommand {

    public MainCommand() {
        super(new HelpCommand());
        this.register(new InfoCommand());
        this.register(new AddExpCommand());
        this.register(new SetExpCommand());
        this.register(new SetLevelCommand());
        this.register(new SetStageCommand());
        this.register(new SetRealmCommand());
        this.register(new PointsCommand());
        this.register(new MultiExpCommand());
        this.register(new ReloadCommand());
    }
}
