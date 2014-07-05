package com.mrz.dyndns.server.ThatOneSpawnPlugin.EventListeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.mrz.dyndns.server.ThatOneSpawnPlugin.SpawnManager;

public class PlayerJoinListener implements Listener
{
	private final SpawnManager spawnManager;
	private final JavaPlugin plugin;
	
	public PlayerJoinListener(final JavaPlugin plugin, final SpawnManager spawnManager)
	{
		this.spawnManager = spawnManager;
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerJoin(final PlayerJoinEvent event)
	{
		final Player player = event.getPlayer();
		//If this is their first time playing, take them to spawn
		if(!player.hasPlayedBefore())
		{
			final Location loc = spawnManager.getSpawn();
			
			//The player is teleported on the next tick. This gives the server time to load the spawn world into memory if it is not loaded already.
			Bukkit.getScheduler().runTask(plugin, new Runnable() {
				@Override
				public void run()
				{
					player.teleport(loc);
				}
			});
		}
	}
}
