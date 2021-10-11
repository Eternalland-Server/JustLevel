package com.taylorswiftcn.megumi.justlevel.event;

import com.taylorswiftcn.megumi.justlevel.level.PlayerLevelData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class JustPlayerUpgradeEvent extends Event {

    private final Player player;
    private int originLevel;
    private PlayerLevelData data;

    public JustPlayerUpgradeEvent(Player player, int originLevel, PlayerLevelData data) {
        this.player = player;
        this.originLevel = originLevel;
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
