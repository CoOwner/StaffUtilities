package me.dreamzy.staff.redis;

import redis.clients.jedis.Jedis;
import me.dreamzy.staff.StaffPlugin;

public class JedisPublisher {
	
	private StaffPlugin plugin;
    
	public JedisPublisher(StaffPlugin plugin) {
		this.plugin = plugin;
	}
    
	public void write(final String message) {
        Jedis jedis = null;

        try {
            jedis = this.plugin.getServerHandler().getPool().getResource();
            if (this.plugin.getConfig().getBoolean("REDIS.PASSWORD_NEEDED")) {
                jedis.auth(this.plugin.getConfig().getString("REDIS.PASSWORD"));
            }
            jedis.publish("staff", message);
        }
        finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}
