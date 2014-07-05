package com.mrz.dyndns.server.ThatOneSpawnPlugin;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * This class manages a (THE) spawn location
 * @author Zora
 *
 */
public class SpawnManager
{
	//plugin instance
	private final JavaPlugin plugin;
	
	//The in-memory spawn (location) instance, so we don't have to keep reading it from a file
	private Location spawn;
	
	//This is a flag so we know if it's us setting spawn or someone else. The event handler will read this value to decide if it needs to do something or not
	private boolean spawnBeingSet;
	
	/**
	 * Constructor
	 * @param plugin The plugin that this SpawnManager belongs to
	 */
	public SpawnManager(final JavaPlugin plugin)
	{
		this.plugin = plugin;
		spawnBeingSet = false;
	}
	
	/**
	 * Retrieves the location of the spawn. If the location is not saved, the default will be retrieved, saved and returned.
	 * @return The spawn location for the server
	 */
	public Location getSpawn()
	{
		//If the spawn has already been read from the config
		if(spawn != null)
		{
			return spawn;
		}
		//The spawn isn't in memory so it will need to be read from the file
		
		//Get the spawn from the config.yml file. If that doesn't work, get the default spawn
		else
		{
			//Load the spawn from the disk
			Location loc = loadSpawnFromDisk();
			
			//Something didn't go as planned
			if(loc == null)
			{
				//get the default spawn
				loc = getDefaultSpawn();
				//save it
				setSpawn(loc);
			}
			
			//put the location in memory so it doesn't need to be loaded again
			spawn = loc;
			return loc;
		}
	}
	
	/**
	 * Saves/sets the spawn
	 * @param loc The spawn location to be saved
	 */
	public void setSpawn(final Location loc)
	{
		//The spawn is being set
		spawnBeingSet = true;
		
		//set in memory
		spawn = loc;
		
		//saved in file
		final FileConfiguration config = plugin.getConfig();
		config.set("World", loc.getWorld().getName());
		config.set("X", loc.getX());
		config.set("Y", loc.getY());
		config.set("Z", loc.getZ());
		config.set("Yaw", loc.getYaw());
		config.set("Pitch", loc.getPitch());
		plugin.saveConfig();
		
		//The spawn is also set in a way so that the server will know where spawn is even if the plugin is gone/unable to do it's job
		writeSpawnToBukkitSpawn();
		
		//The spawn is done being set
		spawnBeingSet = false;
	}
	
	/**
	 * This will get the server's default spawn
	 * @return The spawn of the primary world (the one named in the server.properties file)
	 */
	public Location getDefaultSpawn()
	{
		//Get the tallest block at that location. Sometimes the default spawn is in the middle of the ground, which can cause suffocation.
		World defaultWorld = plugin.getServer().getWorlds().get(0);
		Location loc = defaultWorld.getSpawnLocation();
		loc.setY(defaultWorld.getHighestBlockYAt(loc));
		return loc;
	}
	
	/**
	 * Checks if spawn is being set
	 * @return True if it is the SpawnManager that is changing spawn, False if it is not
	 */
	public boolean isSpawnBeingSet()
	{
		return spawnBeingSet;
	}
	
	/**
	 * Loads the spawn from the config.yml file, if it exists
	 * @return The spawn location if it is saved, null if it could not be read correctly or doesn't exist
	 */
	public Location loadSpawnFromDisk()
	{
		//Make sure the config is the one that is represented on the hdd/sdd
		plugin.reloadConfig();
		final FileConfiguration config = plugin.getConfig();
		
		//This makes sure that the config has everything we need to get the location.
		//If all 5 of these things aren't here, then it either isn't written, or is corrupted. Either of those two scenarios mean that spawn must be (re)written
		if(config.contains("World") &&
				config.contains("X") && 
				config.contains("Y") &&
				config.contains("Z") &&
				config.contains("Yaw") &&
				config.contains("Pitch"))
		{
			final World world = plugin.getServer().getWorld(config.getString("World"));
			
			//Making sure the world spawn is in exists...
			if(world != null)
			{
				//Read the spawn from the file
				final Location loc = new Location(world,
						config.getDouble("X"),
						config.getDouble("Y"),
						config.getDouble("Z"),
						(float) config.getDouble("Yaw"),
						(float) config.getDouble("Pitch"));
				
				return loc;
			}
		}
		
		//If the code reaches here then something has gone wrong down the line. Returning null as promised.
		return null;
	}
	
	/**
	 * Sets the plugin's spawn to the built-in spawn system
	 */
	public void writeSpawnToBukkitSpawn()
	{
		//The spawn is being set
		spawnBeingSet = true;
		
		spawn.getWorld().setSpawnLocation(
				(int) spawn.getX(),
				(int) spawn.getY(),
				(int) spawn.getZ());
		
		//The spawn is done being set
		spawnBeingSet = false;
	}
}