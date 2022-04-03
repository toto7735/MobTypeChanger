package me.toto7735.mobTypeChanger;

import me.toto7735.mobTypeChanger.worker.Worker;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class MobTypeChanger extends JavaPlugin {

    public static JavaPlugin plugin;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new Worker(), this);
        plugin = this;
    }

}

