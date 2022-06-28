package net.sakuragame.eternal.justlevel.core.level;

import lombok.Getter;

@Getter
public class Card {

    private final String name;
    private final String icon;
    private final int duration;
    private final double addition;


    public Card(String name, String icon, int duration, double addition) {
        this.name = name;
        this.icon = icon;
        this.duration = duration;
        this.addition = addition;
    }

    public String getDisplay() {
        return this.icon + this.name;
    }
}
