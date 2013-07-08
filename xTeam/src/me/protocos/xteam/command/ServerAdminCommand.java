package me.protocos.xteam.command;

import org.bukkit.entity.Player;

public abstract class ServerAdminCommand extends PlayerCommand
{
	public ServerAdminCommand()
	{
	}
	public ServerAdminCommand(Player sender, CommandParser command)
	{
		super(sender, command);
	}
}
