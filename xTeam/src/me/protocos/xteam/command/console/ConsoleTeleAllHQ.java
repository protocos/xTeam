package me.protocos.xteam.command.console;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ConsoleCommand;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.entity.TeamPlayer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.PatternBuilder;

public class ConsoleTeleAllHQ extends ConsoleCommand
{
	public ConsoleTeleAllHQ()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		for (TeamPlayer player : xTeam.getInstance().getPlayerManager().getOnlinePlayers())
		{
			if (!player.hasTeam())
			{
				sender.sendMessage(player.getName() + " does not have a team and was not teleported");
			}
			else if (!player.getTeam().hasHeadquarters())
			{
				sender.sendMessage("No team headquarters set for team " + player.getTeam().getName() + " for " + player.getName());
			}
			else
			{
				player.teleport(player.getTeam().getHeadquarters().getLocation());
				player.sendMessage("You have been teleported to the team headquarters by an admin");
			}
		}
		sender.sendMessage("Players teleported");
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
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
