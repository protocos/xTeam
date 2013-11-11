package me.protocos.xteam.command.console;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.TeamException;
import org.bukkit.command.CommandSender;

public class ConsoleTeleAllHQ extends ConsoleCommand
{
	public ConsoleTeleAllHQ()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		for (TeamPlayer player : xTeam.getPlayerManager().getOnlinePlayers())
		{
			if (!player.hasTeam())
			{
				originalSender.sendMessage(player.getName() + " does not have a team and was not teleported");
			}
			else if (player.getTeam().hasHeadquarters())
			{
				originalSender.sendMessage("No team headquarters set for team " + player.getTeam().getName() + " for " + player.getName());
			}
			else
			{
				player.teleport(player.getTeam().getHeadquarters());
				player.sendMessage("You have been teleported to the team headquarters by an admin");
			}
		}
		originalSender.sendMessage("Players teleported");
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
	}

	@Override
	public String getPattern()
	{
		return patternOneOrMore("teleallhq") + OPTIONAL_WHITE_SPACE;
	}

	@Override
	public String getUsage()
	{
		return "/team teleallhq";
	}

	@Override
	public String getDescription()
	{
		return "teleports everyone to their headquarters";
	}
}
