package me.protocos.xteam.command;

import org.bukkit.entity.Player;

public interface IPlayerCommand extends ICommand
{
	public abstract void setPlayer(Player sender);
	public abstract Player getPlayer();
}
