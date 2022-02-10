package net.sakuragame.eternal.justlevel.core.level;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Realm {

    private int layer;
    private String name;
    private String prefix;
    private int stageConsume;
    private int realmConsume;
    private int stageBreakPrice;
    private int realmBreakPrice;

}
