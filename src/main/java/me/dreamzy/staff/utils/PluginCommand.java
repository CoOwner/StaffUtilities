package me.dreamzy.staff.utils;

import me.dreamzy.staff.StaffPlugin;


public abstract class PluginCommand {

    public StaffPlugin plugin = StaffPlugin.getPlugin();
    
    public PluginCommand() {
        plugin.getFramework().registerCommands(this);
    }

    public abstract void onCommand(CommandArgs command);

}
