package me.rafaelpiloto10.lightlevels;

import me.rafaelpiloto10.lightlevels.commands.LightLevelsCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class LightLevels extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        new LightLevelsCommand(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
