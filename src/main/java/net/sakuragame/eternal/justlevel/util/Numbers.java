package net.sakuragame.eternal.justlevel.util;

import java.util.Random;

public class Numbers {

    private final static Random RANDOM = new Random();

    public static double randomDouble() {
        return RANDOM.nextDouble();
    }
}
