package com.mrz.dyndns.server.ThatOneSpawnPlugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.mrz.dyndns.server.ThatOneSpawnPlugin.SpawnManager;
import com.mrz.dyndns.server.ThatOneSpawnPlugin.ThatOneSpawnPlugin;

public class ReloadSpawnCommand implements CommandExecutor
{
	private final SpawnManager spawnManager;
	private final ThatOneSpawnPlugin plugin;
	
	public ReloadSpawnCommand(final ThatOneSpawnPlugin plugin, final SpawnManager spawnManager)
	{
		this.spawnManager = spawnManager;
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String alias, final String[] args)
	{
		Location loc = spawnManager.loadSpawnFromDisk();
		//Something didn't go right!
		if(loc == null)
		{
			sender.sendMessage(ChatColor.RED + "Something didn't go right when reloading the spawn from the config.yml! Make sure that the World, X, Y, Z, Yaw and Pitch are all there!");
		}
		//It all went right
		else
		{
			spawnManager.setSpawn(loc);
			sender.sendMessage(ChatColor.DARK_GREEN + "Spawn successfully reloaded from config.yml!");
		}
		
		//re-addres what events should be listened on
		plugin.reloadEvents();
		
		return true;
	}
}
