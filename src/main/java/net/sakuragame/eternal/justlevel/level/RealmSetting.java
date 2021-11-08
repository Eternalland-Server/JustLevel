package net.sakuragame.eternal.justlevel.level;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RealmSetting {

    private int layer;
    private String name;
    private int stageConsume;
    private int realmConsume;
    private int stageBreakPrice;
    private int realmBreakPrice;

}
