package me.protocos.xteam.command;

import org.bukkit.entity.Player;

public abstract class PlayerCommand extends Command implements IPlayerCommand
{
	protected Player sender;

	public PlayerCommand()
	{
	}
	public PlayerCommand(Player sender, CommandParser command)
	{
		super(sender, command);
		setSender(sender);
	}

	public Player getSender()
	{
		return sender;
	}

	public void setSender(Player sender)
	{
		this.sender = sender;
	}
}
