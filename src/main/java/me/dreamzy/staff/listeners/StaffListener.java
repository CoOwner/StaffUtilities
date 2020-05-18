package me.dreamzy.staff.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.dreamzy.staff.StaffPlugin;

public class StaffListener implements Listener {
	
	private StaffPlugin plugin;
	
	public StaffListener(StaffPlugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onAsync(AsyncPlayerChatEvent event) {
		if(plugin.getToggledStaff().contains(event.getPlayer())) {
			event.setCancelled(true);
			this.plugin.getServerHandler().getPublisher().write("staffchat;" + event.getPlayer().getName() + ";" + plugin.getConfig().getString("SERVER.NAME") + ";" + event.getMessage().replace(";", ":"));
	        
		} else if(plugin.getToggledAdmin().contains(event.getPlayer())) {
			event.setCancelled(true);
			this.plugin.getServerHandler().getPublisher().write("adminchat;" + event.getPlayer().getName() + ";" + plugin.getConfig().getString("SERVER.NAME") + ";" + event.getMessage().replace(";", ":"));
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if(event.getPlayer().hasPermission(plugin.getConfig().getString("LANGUAGE.QUIT.PERMISSION"))) {
			this.plugin.getServerHandler().getPublisher().write("staffquit;" + player.getName() + ";" + Bukkit.getServerName());
					
			//other.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("LANGUAGE.QUIT.MESSAGE")).replace("%PLAYER%", player.getName()).replace("%SERVER%", plugin.getConfig().getString("SERVER.NAME")));
		}		
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if(event.getPlayer().hasPermission(plugin.getConfig().getString("LANGUAGE.JOIN.PERMISSION"))) {
			this.plugin.getServerHandler().getPublisher().write("staffjoin;" + player.getName() + ";" + Bukkit.getServerName());
		}
	}

}
