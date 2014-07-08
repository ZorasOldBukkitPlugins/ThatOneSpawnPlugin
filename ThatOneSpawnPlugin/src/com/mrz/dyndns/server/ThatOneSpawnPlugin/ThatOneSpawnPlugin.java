package com.mrz.dyndns.server.ThatOneSpawnPlugin;

import org.bukkit.event.HandlerList;
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
		saveConfig();
		
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
		//Unregister all events
		HandlerList.unregisterAll(this);
		
		//Re-register it if applicable
		if(getConfig().getBoolean("GoToSpawnOnDeath"))
		{
			getServer().getPluginManager().registerEvents(new PlayerRespawnListener(spawnManager), this);
		}
		
		final boolean letOthersChangeSpawn = getConfig().getBoolean("letOtherPluginsChangeSpawn");
		getServer().getPluginManager().registerEvents(new SpawnChangedListener(spawnManager, letOthersChangeSpawn), this);
		
		//this event is always registered
		getServer().getPluginManager().registerEvents(new PlayerJoinListener(this, spawnManager), this);
	}
}
