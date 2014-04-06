package com.mrz.dyndns.server.ThatOneSpawnPlugin.EventListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.SpawnChangeEvent;

import com.mrz.dyndns.server.ThatOneSpawnPlugin.SpawnManager;

public class SpawnChangedListener implements Listener
{
	private final SpawnManager spawnManager;
	private final boolean allowChange;
	
	public SpawnChangedListener(final SpawnManager spawnManager, boolean allowChange)
	{
		this.spawnManager = spawnManager;
		this.allowChange = allowChange;
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onSpawnChanged(final SpawnChangeEvent event)
	{
		//The spawn in the spawn world is being changed
		if(event.getWorld() == spawnManager.getSpawn().getWorld())
		{
			if(!spawnManager.isSpawnBeingSet())
			{
				if(allowChange)
				{
					spawnManager.setSpawn(event.getWorld().getSpawnLocation());
				}
				else
				{
					spawnManager.writeSpawnToBukkitSpawn();
				}
			}
		}
	}
}
