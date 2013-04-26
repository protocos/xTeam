package me.protocos.xteam.command;

import org.bukkit.entity.Player;

public abstract class BasePlayerCommand extends BaseCommand implements IPlayerCommand
{
	protected Player player;

	public BasePlayerCommand()
	{
		super();
	}

	public BasePlayerCommand(Player sender, String command)
	{
		super(sender, command);
		setSender(sender);
	}

	public void setSender(Player player)
	{
		this.player = player;
	}

	public Player getSender()
	{
		return player;
	}
}
