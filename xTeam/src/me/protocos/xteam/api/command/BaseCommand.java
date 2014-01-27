package me.protocos.xteam.api.command;

import java.io.InvalidClassException;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import org.bukkit.command.CommandSender;

public abstract class BaseCommand
{
	public BaseCommand()
	{
	}

	public abstract String getUsage();

	public abstract String getPattern();

	public abstract String getDescription();

	protected abstract void preInitialize(CommandContainer commandContainer) throws TeamException, InvalidClassException;

	protected void initializeData(@SuppressWarnings("unused") CommandContainer commandContainer)
	{
	}

	protected abstract void checkCommandRequirements(CommandContainer commandContainer) throws TeamException;

	protected abstract void performCommandAction(CommandContainer commandContainer);

	public final boolean execute(CommandContainer commandContainer)
	{
		CommandSender sender = commandContainer.getSender();
		try
		{
			preInitialize(commandContainer);
			checkCommandRequirements(commandContainer);
			performCommandAction(commandContainer);
			return true;
		}
		catch (TeamException e)
		{
			sender.sendMessage(ChatColorUtil.negativeMessage(e.getMessage()));
			xTeam.getInstance().getLog().debug("Command execute failed for reason: " + e.getMessage());
		}
		catch (InvalidClassException e)
		{
			e.printStackTrace();
		}
		return false;
	}
}
