package com.mrz.dyndns.server.ThatOneSpawnPlugin.EventListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.mrz.dyndns.server.ThatOneSpawnPlugin.SpawnManager;

public class PlayerRespawnListener implements Listener
{
	private final SpawnManager spawnManager;
	
	public PlayerRespawnListener(final SpawnManager spawnManager)
	{
		this.spawnManager = spawnManager;
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerRespawn(PlayerRespawnEvent event)
	{
		event.setRespawnLocation(spawnManager.getSpawn());
	}
}
