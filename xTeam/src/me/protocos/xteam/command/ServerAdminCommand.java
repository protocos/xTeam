package me.protocos.xteam.command;

import org.bukkit.entity.Player;

public abstract class ServerAdminCommand extends PlayerCommand
{
	public ServerAdminCommand(Player sender, String command)
	{
		super(sender, command);
	}
}
