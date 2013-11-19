package me.protocos.xteam.command.console;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ConsoleCommand;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.PatternBuilder;
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
		for (TeamPlayer player : xTeam.getInstance().getPlayerManager().getOnlinePlayers())
		{
			if (!player.hasTeam())
			{
				originalSender.sendMessage(player.getName() + " does not have a team and was not teleported");
			}
			else if (!player.getTeam().hasHeadquarters())
			{
				originalSender.sendMessage("No team headquarters set for team " + player.getTeam().getName() + " for " + player.getName());
			}
			else
			{
				player.teleport(player.getTeam().getHeadquarters().getLocation());
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
		return new PatternBuilder()
				.oneOrMore("tele")
				.oneOrMore("all")
				.oneOrMore("hq")
				.whiteSpaceOptional()
				.toString();
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
