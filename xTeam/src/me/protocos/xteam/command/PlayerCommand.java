package me.protocos.xteam.command;

import org.bukkit.entity.Player;

public abstract class PlayerCommand extends Command implements IPlayerCommand
{
	protected Player player;

	public PlayerCommand(Player sender, String command)
	{
		super(sender, command);
		setPlayer(sender);
	}

	public void setPlayer(Player player)
	{
		this.player = player;
	}

	public Player getPlayer()
	{
		return player;
	}
}
