package me.protocos.xteam.command;

import org.bukkit.entity.Player;

public interface IPlayerCommand extends ICommand
{
	public abstract void setSender(Player sender);
	public abstract Player getSender();
}
