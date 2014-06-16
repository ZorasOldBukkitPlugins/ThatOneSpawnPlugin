package com.mrz.dyndns.server.ThatOneSpawnPlugin;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.world.SpawnChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.mrz.dyndns.server.ThatOneSpawnPlugin.EventListeners.*;
import com.mrz.dyndns.server.ThatOneSpawnPlugin.commands.*;

public class ThatOneSpawnPlugin extends JavaPlugin
{
	//An instance of the class that makes it all happen
	private SpawnManager spawnManager;
	
	@Override
	public void onEnable()
	{
		//Copy default values to the config
		getConfig().options().copyDefaults(true);
		
		//Instantiate that good stuff
		spawnManager = new SpawnManager(this);
		
		//Map dem commands
		getCommand("spawn").setExecutor(new GoToSpawnCommand(spawnManager));
		getCommand("setspawn").setExecutor(new SetSpawnCommand(spawnManager));
		getCommand("reloadspawn").setExecutor(new ReloadSpawnCommand(this, spawnManager));
		
		//...And dose events!
		reloadEvents();
	}
	
	/**
	 * This reloads the events in case the type of events the plugin is listening for changes during runtime
	 */
	public void reloadEvents()
	{
		//Unregister the event that could have potentially changed
		PlayerRespawnEvent.getHandlerList().unregister(this);
		//I unregister all of them just to make certain that I only have one instance registered at a time
		PlayerJoinEvent.getHandlerList().unregister(this);
		SpawnChangeEvent.getHandlerList().unregister(this);
		
		//Re-register it if applicable
		if(getConfig().getBoolean("GoToSpawnOnDeath"))
		{
			getServer().getPluginManager().registerEvents(new PlayerRespawnListener(spawnManager), this);
		}
		
		final boolean letOthersChangeSpawn = getConfig().getBoolean("letOtherPluginsChangeSpawn");
		getServer().getPluginManager().registerEvents(new SpawnChangedListener(spawnManager, letOthersChangeSpawn), this);
		
		//this event is always registered
		getServer().getPluginManager().registerEvents(new PlayerJoinListener(spawnManager), this);
		}
}
