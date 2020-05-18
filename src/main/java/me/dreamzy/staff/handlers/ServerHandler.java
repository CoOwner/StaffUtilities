package me.dreamzy.staff.handlers;

import java.util.HashMap;
import java.util.UUID;

import lombok.Data;
import redis.clients.jedis.JedisPool;
import me.dreamzy.staff.StaffPlugin;
import me.dreamzy.staff.redis.JedisPublisher;
import me.dreamzy.staff.redis.JedisSubscriber;

@Data
public class ServerHandler {

	private StaffPlugin plugin;
	private JedisPool pool;
	private JedisPublisher publisher;
	private JedisSubscriber subscriber;
    private HashMap<UUID, Long> cooldowns;
    
	public ServerHandler(StaffPlugin plugin) {
		this.plugin = plugin;
		cooldowns = new HashMap<UUID, Long>();
		this.setupJedis();
		
	}
	
	private void setupJedis() {
		this.pool = new JedisPool(plugin.getConfig().getString("REDIS.HOST"), plugin.getConfig().getInt("REDIS.PORT"));
		this.publisher = new JedisPublisher(this.getPlugin());
		this.subscriber = new JedisSubscriber(this.getPlugin());
	}

	public void disable() {
		this.subscriber.getJedisPubSub().unsubscribe();
		this.cooldowns.clear();
		
	}
	
}
