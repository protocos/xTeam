package me.protocos.xteam.command;

import java.util.List;
import me.protocos.xteam.command.CommandContainer;
import org.bukkit.command.CommandSender;

public interface ICommandManager
{
	public abstract BaseCommand match(CommandContainer commandContainer);

	public abstract List<String> getAvailableCommandsFor(CommandSender sender);

	public abstract void registerCommand(BaseCommand command);

	public abstract void register(ICommandContainer container);
}
