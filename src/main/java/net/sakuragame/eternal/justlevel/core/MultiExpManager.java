package net.sakuragame.eternal.justlevel.core;

import net.sakuragame.eternal.justlevel.JustLevel;
import net.sakuragame.eternal.justmessage.api.MessageAPI;
import net.sakuragame.eternal.justmessage.gains.GainsProperty;
import net.sakuragame.serversystems.manage.api.redis.RedisMessageListener;
import net.sakuragame.serversystems.manage.client.api.ClientManagerAPI;
import org.bukkit.Bukkit;


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
        if (this.isValid()) MessageAPI.removePublicGains(KEY);

        this.addition = addition;
        this.expire = System.currentTimeMillis() + minute * 60 * 1000L;
        this.reason = reason;

        MessageAPI.applyPublicGains(KEY, new GainsProperty("༦" + this.reason, this.expire));

        Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage("⒠ §7已开启全服 §a" + (addition + 1) + " §7倍经验加成§8(§e" + minute + "分钟§8)"));
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


    public void update(double addition, int minute, String reason) {
        ClientManagerAPI.getRedisManager().publishAsync(plugin.getName(), KEY, String.valueOf(addition), String.valueOf(minute), reason);
    }

}
