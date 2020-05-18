package me.dreamzy.staff.redis;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import me.dreamzy.staff.StaffPlugin;

public class JedisSubscriber {
	
    private JedisPubSub jedisPubSub;
    private Jedis jedis;
    private StaffPlugin plugin;
    
    public JedisSubscriber(StaffPlugin plugin) {
        this.plugin = plugin;
        this.jedis = new Jedis(plugin.getConfig().getString("REDIS.HOST"), plugin.getConfig().getInt("REDIS.PORT"));
        if (plugin.getConfig().getBoolean("REDIS.PASSWORD_NEEDED")) {
            this.jedis.auth(plugin.getConfig().getString("REDIS.PASSWORD"));
        }
        this.subscribe();
    }
    
    public void subscribe() {
        this.jedisPubSub = this.get();
        new Thread() {
            @Override
            public void run() {
                JedisSubscriber.this.jedis.subscribe(JedisSubscriber.this.jedisPubSub, "staff");
            }
        }.start();
    }
    
    private JedisPubSub get() {
    	return new JedisPubSub() {
    		@Override
    		public void onMessage(String channel, String message) {
    			if (channel.equalsIgnoreCase("staff")) {
                    String[] args = message.split(";");
                    if (args.length > 2) {
                        String command = args[0];
                        String subCommand = args[1];
                        if (command.equalsIgnoreCase("staffchat")) {
                            String player = subCommand;
                            String server = args[2];
                            String staffMessage = args[3];
                            for (Player other : Bukkit.getOnlinePlayers()) {
                                if (other.hasPermission(plugin.getConfig().getString("LANGUAGE.STAFFCHAT.PERMISSION"))) {
                                	other.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("LANGUAGE.STAFFCHAT.MESSAGE")).replace("%PLAYER%", player).replace("%SERVER%", server).replace("%MESSAGE%", staffMessage));
                                }
                            }
                        }
                        if (command.equalsIgnoreCase("adminchat")) {
                            String player = subCommand;
                            String server = args[2];
                            String staffMessage = args[3];
                            for (Player other : Bukkit.getOnlinePlayers()) {
                                if (other.hasPermission(plugin.getConfig().getString("LANGUAGE.ADMINCHAT.PERMISSION"))) {
                                	other.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("LANGUAGE.ADMINCHAT.MESSAGE")).replace("%PLAYER%", player).replace("%SERVER%", server).replace("%MESSAGE%", staffMessage));
                                }
                            }
                        }                   
                        if (command.equalsIgnoreCase("staffquit")) {
                        	String playerName = subCommand;
                        	String server = args[2];
                            for (Player other : Bukkit.getOnlinePlayers()) {
                            	 if (other.hasPermission(plugin.getConfig().getString("LANGUAGE.QUIT.PERMISSION"))) {
                            		 other.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("LANGUAGE.QUIT.MESSAGE")).replace("%PLAYER%", playerName).replace("%SERVER%", server));
                                }
                            }
                        }
                        if (command.equalsIgnoreCase("staffjoin")) {
                        	String playerName = subCommand;
                        	String server = args[2];
                            for (Player other : Bukkit.getOnlinePlayers()) {
                            	if (other.hasPermission(plugin.getConfig().getString("LANGUAGE.JOIN.PERMISSION"))) {
                            		other.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("LANGUAGE.JOIN.MESSAGE")).replace("%PLAYER%", playerName).replace("%SERVER%", server));
                               }
                            }
                        }
                        if (command.equalsIgnoreCase("request")) {
                            if (subCommand.equalsIgnoreCase("request")) {
                                String playerName = args[2];
                                String serverName = args[3];
                                String reason = args[4];
                                for (Player player2 : Bukkit.getOnlinePlayers()) {
                                    if (player2.hasPermission(plugin.getConfig().getString("LANGUAGE.REQUEST.PERMISSION"))) {
                                    	player2.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("LANGUAGE.REQUEST.MESSAGE")).replace("%PLAYER%", playerName).replace("%SERVER%", serverName).replace("%MESSAGE%", reason));
                                    }
                                }
                            }
                            else {
                                String playerName = args[2];
                                String reportedName = args[3];
                                String serverName2 = args[4];
                                String reason2 = args[5];
                                for (Player player3 : Bukkit.getOnlinePlayers()) {
                                    if (player3.hasPermission(plugin.getConfig().getString("LANGUAGE.REPORT.PERMISSION"))) {
                                    	player3.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("LANGUAGE.REPORT.MESSAGE")).replace("%PLAYER%", playerName).replace("%TARGET%", reportedName).replace("%SERVER%", serverName2).replace("%MESSAGE%", reason2));
                                    }
                                }
                            }
                            return;
                        }
                    }
                }
            }
        };
    }
    
    public JedisPubSub getJedisPubSub() {
        return this.jedisPubSub;
    }
    
    public Jedis getJedis() {
        return this.jedis;
    }
}
