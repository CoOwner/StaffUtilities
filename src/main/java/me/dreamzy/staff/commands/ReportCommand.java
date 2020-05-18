package me.dreamzy.staff.commands;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.dreamzy.staff.utils.Command;
import me.dreamzy.staff.utils.CommandArgs;
import me.dreamzy.staff.utils.PluginCommand;

public class ReportCommand extends PluginCommand {
	
    @Command(name = "report", aliases = "cheater")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        
        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Usage: /report <player> <reason>");
            return;
        }
        
        Player target = Bukkit.getPlayer(args[0]);
        
    	if(target == null) {
    		player.sendMessage(ChatColor.RED + "Player '" + args[0] + "' not found.");
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
//		if (plugin.getServerHandler().getCooldowns().containsKey(player.getUniqueId())) {
//            if (System.currentTimeMillis() - plugin.getServerHandler().getCooldowns().get(player.getUniqueId()) <= 120000L) {
//            	String format = (TimeUnit.MILLISECONDS.toMinutes(plugin.getServerHandler().getCooldowns().get(player.getUniqueId()) - System.currentTimeMillis() + 120000) == 0 ? "" : TimeUnit.MILLISECONDS.toMinutes(plugin.getServerHandler().getCooldowns().get(player.getUniqueId()) - System.currentTimeMillis() + 120000) + " minute and ") + (TimeUnit.MILLISECONDS.toSeconds(plugin.getServerHandler().getCooldowns().get(player.getUniqueId()) - System.currentTimeMillis() + 120000) - 60) + " seconds";
//                player.sendMessage(ChatColor.RED + "You must wait " + ChatColor.BOLD + DurationFormatUtils.formatDurationWords(time + TimeUnit.MINUTES.toMillis(2L), true, true) + ChatColor.RED + " before submitting a request again.");
//                return;
//            }
//            plugin.getServerHandler().getCooldowns().remove(player.getUniqueId());
//        }
        this.plugin.getServerHandler().getPublisher().write("request;REPORT;" + player.getName() + ";" + target.getName() + ";" + plugin.getConfig().getString("SERVER.NAME") + ";" + StringUtils.join((Object[])args, " ", 1, args.length).replace(";", ":"));
        player.sendMessage(ChatColor.GREEN + "Your report has been sent to online staff members.");
        plugin.getServerHandler().getCooldowns().put(player.getUniqueId(), System.currentTimeMillis());
        
    }
}
