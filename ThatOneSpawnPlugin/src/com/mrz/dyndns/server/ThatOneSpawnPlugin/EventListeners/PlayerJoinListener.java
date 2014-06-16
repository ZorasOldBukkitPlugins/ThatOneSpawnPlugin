package com.mrz.dyndns.server.ThatOneSpawnPlugin.EventListeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.mrz.dyndns.server.ThatOneSpawnPlugin.SpawnManager;

public class PlayerJoinListener implements Listener
{
	private final SpawnManager spawnManager;
	
	public PlayerJoinListener(final SpawnManager spawnManager)
	{
		this.spawnManager = spawnManager;
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerJoin(final PlayerJoinEvent event)
	{
		final Player player = event.getPlayer();
		//If this is their first time playing, take them to spawn
		if(!player.hasPlayedBefore())
		{
			Location loc = spawnManager.getSpawn();
			player.teleport(loc);
		}
	}
}
