package me.protocos.xteam.api.command;

import java.util.List;
import me.protocos.xteam.command.BaseCommand;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.command.PlayerCommand;
import me.protocos.xteam.core.TeamPlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface ICommandManager
{
	public abstract void registerCommand(String key, BaseCommand command);

	public abstract BaseCommand get(String key);

	public abstract String getPattern(String key);

	public abstract String getPermissionNode(String key);

	public abstract String getUsage(String key);

	public abstract ConsoleCommand matchConsole(String pattern);

	public abstract PlayerCommand matchPlayer(String pattern);

	public abstract List<String> getAvailableCommands(CommandSender sender);

	public abstract List<String> getAvailableAdminCommandsFor(Player player);

	public abstract List<String> getAvailableCommandsFor(TeamPlayer teamPlayer);
}
