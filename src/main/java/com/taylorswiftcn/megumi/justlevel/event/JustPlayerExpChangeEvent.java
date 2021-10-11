package com.taylorswiftcn.megumi.justlevel.event;

import com.taylorswiftcn.megumi.justlevel.level.PlayerLevelData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class JustPlayerExpChangeEvent extends Event {

    private final Player player;
    private double increase;
    private PlayerLevelData data;

    public JustPlayerExpChangeEvent(Player player, double increase, PlayerLevelData data) {
        this.player = player;
        this.increase = increase;
        this.data = data;
    }

    private static HandlerList handlerList = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return null;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
