package me.dreamzy.staff.commands;

import java.util.concurrent.TimeUnit;

import net.minecraft.util.org.apache.commons.lang3.time.DurationFormatUtils;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.dreamzy.staff.utils.Command;
import me.dreamzy.staff.utils.CommandArgs;
import me.dreamzy.staff.utils.PluginCommand;

public class RequestCommand extends PluginCommand {
	
    @Command(name = "request", aliases = "helpop")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        
        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "Usage: /request <reason>");
            return;
        }
        
    	if (plugin.getServerHandler().getCooldowns().containsKey(player.getUniqueId())) {
    		long time = System.currentTimeMillis() - plugin.getServerHandler().getCooldowns().get(player.getUniqueId());
    		if(time <= TimeUnit.MINUTES.toMillis(2L)) {
    			player.sendMessage(ChatColor.RED + "You must wait " + ChatColor.BOLD + DurationFormatUtils.formatDurationWords(TimeUnit.MINUTES.toMillis(2L) - time, true, true) + ChatColor.RED + " before submitting a request again.");
    			return;
    		}
    		plugin.getServerHandler().getCooldowns().remove(player.getUniqueId());
    		
    	}
        this.plugin.getServerHandler().getPublisher().write("request;REQUEST;" + player.getName() + ";" + plugin.getConfig().getString("SERVER.NAME") + ";" + StringUtils.join((Object[])args, " ").replace(";", ":"));
        player.sendMessage(ChatColor.GREEN + "Your request has been submitted to all online staff members.");
        plugin.getServerHandler().getCooldowns().put(player.getUniqueId(), System.currentTimeMillis());
        
    }
}
