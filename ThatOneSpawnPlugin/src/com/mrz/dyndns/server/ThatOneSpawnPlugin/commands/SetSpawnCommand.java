package com.mrz.dyndns.server.ThatOneSpawnPlugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mrz.dyndns.server.ThatOneSpawnPlugin.SpawnManager;

public class SetSpawnCommand implements CommandExecutor
{
	private final SpawnManager spawnManager;
	
	public SetSpawnCommand(final SpawnManager spawnManager)
	{
		this.spawnManager = spawnManager;
	}
	
	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String alias, final String[] args)
	{
		if(sender instanceof Player)
		{
			Player player = (Player) sender;
			spawnManager.setSpawn(player.getLocation());
			player.sendMessage(ChatColor.DARK_GREEN + "The spawn has been successfully set!");
		}
		else
		{
			sender.sendMessage(ChatColor.RED + "You must be a player to set the spawn!");
		}
		return true;
	}
}
