package me.protocos.xteam.command;

import java.util.List;
import me.protocos.xteam.entity.ITeamPlayer;

public interface ICommandManager
{
	public abstract String getHelp(CommandContainer commandContainer);

	public abstract BaseCommand match(CommandContainer commandContainer);

	public abstract List<ConsoleCommand> getConsoleCommands();

	public abstract List<PlayerCommand> getAvailableCommandsFor(ITeamPlayer sender);

	public abstract void registerCommand(BaseCommand command);

	public abstract void register(ICommandContainer container);
}
