package me.dreamzy.staff.commands;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.dreamzy.staff.StaffPlugin;
import me.dreamzy.staff.utils.Command;
import me.dreamzy.staff.utils.CommandArgs;
import me.dreamzy.staff.utils.PluginCommand;

public class AdminChatCommand extends PluginCommand {
	
    @Command(name = "adminchat", aliases = "ac", permission = "staff.adminchat")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        
        if (args.length == 0) {
        	List<Player> lol = StaffPlugin.getPlugin().getToggledAdmin();
            
        	if(lol.contains(player)) {
        		lol.remove(player);
        		player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("LANGUAGE.ADMINCHAT.TOGGLE.DISABLED")));
        	} else {
        		lol.add(player);
        		player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("LANGUAGE.ADMINCHAT.TOGGLE.ENABLED")));
        	}
            return;
        }
        
        this.plugin.getServerHandler().getPublisher().write("adminchat;" + player.getName() + ";" + plugin.getConfig().getString("SERVER.NAME") + ";" + StringUtils.join((Object[])args, " ").replace(";", ":"));
        
    }
}
