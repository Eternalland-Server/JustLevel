package net.sakuragame.eternal.justlevel.core;

import net.sakuragame.eternal.justlevel.JustLevel;
import net.sakuragame.eternal.justlevel.file.sub.MessageFile;
import net.sakuragame.eternal.justmessage.api.MessageAPI;
import net.sakuragame.eternal.justmessage.icon.IconProperty;
import net.sakuragame.serversystems.manage.api.redis.RedisMessageListener;
import net.sakuragame.serversystems.manage.client.api.ClientManagerAPI;
import org.bukkit.Bukkit;

import java.util.Calendar;

public class MultiExpManager extends RedisMessageListener {

    private final JustLevel plugin;

    private double addition = 0;
    private long expire = 0;
    private String reason;

    private final String KEY = "server_multi_exp";

    public MultiExpManager() {
        this.plugin = JustLevel.getInstance();
        ClientManagerAPI.getRedisManager().subscribe(plugin.getName());
        ClientManagerAPI.getRedisManager().registerListener(this);
    }

    @Override
    public void onMessage(String serviceName, String sourceServer, String channel, String[] messages) {
        if (sourceServer.equals(ClientManagerAPI.getServerID())) return;
        if (!serviceName.equals(plugin.getName())) return;

        if (channel.equals(KEY)) {
            double addition = Double.parseDouble(messages[0]);
            int minute = Integer.parseInt(messages[1]);
            String reason = messages[2];

            this.set(addition, minute, reason);
        }
    }

    public void set(double addition, int minute, String reason) {
        if (this.isValid()) MessageAPI.unregisterIcon(KEY);

        this.addition = addition;
        this.expire = getExpire(minute);
        this.reason = reason;
        this.registerIcon(minute * 60);

        Bukkit.getOnlinePlayers().forEach(player -> MessageFile.multiExp.forEach(s -> player.sendMessage(s
                .replace("<addition>", String.valueOf(addition + 1))
                .replace("<minute>", String.valueOf(minute))
        )));
    }

    public boolean isValid() {
        return this.expire > System.currentTimeMillis();
    }

    public double getAddition() {
        return this.addition;
    }

    public String getReason() {
        return this.reason;
    }

    public void registerIcon(int second) {
        MessageAPI.registerIcon(KEY, new IconProperty(KEY, "icon/system/1.png", this.reason, second));
    }

    public void update(double addition, int minute, String reason) {
        ClientManagerAPI.getRedisManager().publishAsync(plugin.getName(), KEY, String.valueOf(addition), String.valueOf(minute), reason);
    }

    private long getExpire(int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(minute, minute);
        return calendar.getTimeInMillis();
    }


}
