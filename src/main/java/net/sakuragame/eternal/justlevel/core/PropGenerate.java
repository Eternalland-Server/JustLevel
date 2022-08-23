package net.sakuragame.eternal.justlevel.core;

import ink.ptms.zaphkiel.ZaphkielAPI;
import ink.ptms.zaphkiel.api.ItemStream;
import ink.ptms.zaphkiel.taboolib.module.nms.ItemTag;
import lombok.Getter;
import net.sakuragame.eternal.dragoncore.util.Scheduler;
import net.sakuragame.eternal.justlevel.util.Numbers;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class PropGenerate {

    @Getter
    public enum ItemEnum {

        EXP(0, "exp_item", "&e"),
        LEVEL(1, "level_item", "&e"),
        MONEY(2, "money_item", "&a"),
        COINS(3, "coins_item", "&b");

        private final int index;
        private final String ID;
        private final String color;

        ItemEnum(int index, String ID, String color) {
            this.index = index;
            this.ID = ID;
            this.color = color;
        }

        public static ItemEnum match(int index) {
            for (ItemEnum itemEnum : values()) {
                if (itemEnum.getIndex() != index) continue;
                return itemEnum;
            }

            return null;
        }
    }

    public void spawnExpProp(Location location, int value, int amount) {
        this.spawn(0, location, value, amount);
    }

    public void spawnLevelProp(Location location, int value, int amount) {
        this.spawn(1, location, value, amount);
    }

    public void spawnMoneyProp(Location location, int value, int amount) {
        this.spawn(2, location, value, amount);
    }

    public void spawnCoinsProp(Location location, int value, int amount) {
        this.spawn(3, location, value, amount);
    }

    public void spawn(int type, Location location, int value, int amount) {
        ItemStack itemStack = this.generate(type, value, amount);
        Item item = location.getWorld().dropItem(location, itemStack);

        Scheduler.runLater(() -> item.teleport(location), 10);
    }

    public void spawn(int type, Location location, int value, int amount, double radius) {
        ItemStack item = this.generate(type, value, amount);
        if (radius <= 0) {
            this.spawn(type, location, value, amount);
            return;
        }
        this.itemSpray(location, item, amount, radius);
    }

    public ItemStack generate(int type, int value, int amount) {
        ItemEnum itemEnum = ItemEnum.match(type);
        if (itemEnum == null) return null;

        ItemStream itemStream = ZaphkielAPI.INSTANCE.getItem(itemEnum.getID(), null);
        if (itemStream == null) return null;
        ItemTag itemTag = itemStream.getZaphkielData();
        itemTag.putDeep("parkour.type", type);
        itemTag.putDeep("parkour.value", value);

        ItemStack itemStack = itemStream.rebuildToItemStack(null);
        itemStack.setAmount(amount);

        return itemStack;
    }

    public void itemSpray(Location location, ItemStack item, int amount, double radius) {
        item.setAmount(1);
        for (int i = 0; i < amount; i++) {
            Location loc = location.clone();
            loc.setX(loc.getX() - radius + Numbers.randomDouble() * radius * 2);
            loc.setZ(loc.getZ() - radius + Numbers.randomDouble() * radius * 2);
            Item entityItem = loc.getWorld().dropItem(loc, item);
            entityItem.setVelocity(new Vector(
                    Numbers.randomDouble() - 0.5,
                    Numbers.randomDouble() - 0.5,
                    Numbers.randomDouble() - 0.5
                    )
            );
        }
    }
}
