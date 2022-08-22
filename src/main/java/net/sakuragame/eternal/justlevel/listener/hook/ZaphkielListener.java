package net.sakuragame.eternal.justlevel.listener.hook;

import ink.ptms.zaphkiel.api.event.ItemReleaseEvent;
import ink.ptms.zaphkiel.taboolib.module.nms.ItemTag;
import ink.ptms.zaphkiel.taboolib.module.nms.ItemTagData;
import net.sakuragame.eternal.justlevel.core.PropGenerate;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ZaphkielListener implements Listener {

    @EventHandler
    public void onDisplay(ItemReleaseEvent.Display e) {
        String display = e.getItemStream().getZaphkielItem().getDisplay();
        if (!display.equals("PARKOUR_DISPLAY")) return;

        ItemTag itemTag = e.getItemStream().getZaphkielData();
        ItemTagData tagData = itemTag.getDeep("parkour.type");
        if (tagData == null) return;

        int type = tagData.asInt();
        int value = itemTag.getDeep("parkour.value").asInt();

        PropGenerate.ItemEnum itemEnum = PropGenerate.ItemEnum.match(type);
        if (itemEnum == null) return;

        e.addName("VALUE", itemEnum.getColor() + value);
    }
}
