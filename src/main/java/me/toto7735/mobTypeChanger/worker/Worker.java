package me.toto7735.mobTypeChanger.worker;

import me.toto7735.mobTypeChanger.MobTypeChanger;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Worker implements Listener {

    Map<Player, Integer> map = new HashMap<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        map.put(player, new BukkitRunnable() {
            public void run() {
                for (Entity nearbyEntity : player.getNearbyEntities(20, 10, 20)) {
                    if (nearbyEntity instanceof Player) continue;
                    nearbyEntity.remove();
                    boolean b = false;
                    Entity newEntity = null;
                    while (!b) {
                        try {
                            newEntity = nearbyEntity.getWorld().spawnEntity(nearbyEntity.getLocation(), EntityType.values()[new Random().nextInt(EntityType.values().length)]);
                            b = true;
                        } catch (IllegalArgumentException e) {
                            b = false;
                        }
                    }
                    if (nearbyEntity instanceof LivingEntity && newEntity instanceof LivingEntity) {
                        double percent = ((LivingEntity) nearbyEntity).getHealth() / ((LivingEntity) nearbyEntity).getMaxHealth();
                        ((LivingEntity) newEntity).setHealth( (percent / 100) * ((LivingEntity) newEntity).getMaxHealth() );
                    }
                }
            }
        }.runTaskTimer(MobTypeChanger.plugin, 100, 100).getTaskId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Bukkit.getScheduler().cancelTask(map.get(event.getPlayer()));
        map.remove(event.getPlayer());
    }

}
