package me.protocos.xteam.command;

import org.bukkit.entity.Player;

public abstract class BaseServerAdminCommand extends BasePlayerCommand
{
	public BaseServerAdminCommand()
	{
		super();
	}

	public BaseServerAdminCommand(Player sender, String command)
	{
		super(sender, command);
	}
}
