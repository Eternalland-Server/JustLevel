package net.sakuragame.eternal.justlevel.core.level;

import lombok.Getter;

@Getter
public class Card {

    private final String name;
    private final String texture;
    private final int duration;
    private final double addition;


    public Card(String name, String texture, int duration, double addition) {
        this.name = name;
        this.texture = texture;
        this.duration = duration;
        this.addition = addition;
    }
}
