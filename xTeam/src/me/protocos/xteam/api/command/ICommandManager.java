package me.protocos.xteam.api.command;

import java.util.List;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.core.TeamPlayer;
import org.bukkit.command.CommandSender;

public interface ICommandManager
{
	public abstract void registerCommand(BaseCommand command);

	public abstract BaseCommand match(CommandContainer commandContainer);

	public abstract List<String> getAvailableConsoleCommands(CommandSender sender);

	public abstract List<String> getAvailableAdminCommandsFor(TeamPlayer player);

	public abstract List<String> getAvailableCommandsFor(TeamPlayer teamPlayer);

	public abstract void register(ICommandContainer container);
}
