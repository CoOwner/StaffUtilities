package me.dreamzy.staff;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.dreamzy.staff.commands.AdminChatCommand;
import me.dreamzy.staff.commands.ReportCommand;
import me.dreamzy.staff.commands.RequestCommand;
import me.dreamzy.staff.commands.StaffChatCommand;
import me.dreamzy.staff.handlers.ServerHandler;
import me.dreamzy.staff.listeners.StaffListener;
import me.dreamzy.staff.utils.CommandFramework;

public class StaffPlugin extends JavaPlugin {

	@Getter public static StaffPlugin plugin;
	@Getter private ServerHandler serverHandler;
	@Getter private CommandFramework framework;
	@Getter private List<Player> toggledStaff;
	@Getter private List<Player> toggledAdmin; //Stored in Player as does not need to fetch offline UUID.
	
	@Override
	public void onEnable() {
		plugin = this;
		this.toggledStaff = new ArrayList();
		this.toggledAdmin = new ArrayList();
		
		if (!new File("plugins/StaffUtilities/config.yml").exists()) {
			this.saveDefaultConfig();
			this.getConfig().options().copyDefaults(true);
			this.saveConfig();
		}
			
		this.serverHandler = new ServerHandler(this);
		this.framework = new CommandFramework(this);	
		
		getServer().getPluginManager().registerEvents(new StaffListener(this), this);
		
		new AdminChatCommand();
		new ReportCommand();
		new RequestCommand();
		new StaffChatCommand();
	}
	
	@Override
	public void onDisable() {
		this.serverHandler.disable();
	}
}
