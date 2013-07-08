package me.protocos.xteam.command;

import org.bukkit.entity.Player;

public interface IPlayerCommand extends ICommand
{
	public abstract Player getSender();
	public abstract void setSender(Player sender);
}
