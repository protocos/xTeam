package me.protocos.xteam.command.console;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.entity.TeamPlayer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.Message;
import me.protocos.xteam.util.PatternBuilder;

public class ConsoleTeleAllHQ extends ConsoleCommand
{
	public ConsoleTeleAllHQ(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		for (TeamPlayer player : playerFactory.getOnlinePlayers())
		{
			if (!player.hasTeam())
			{
				new Message.Builder(player.getName() + " does not have a team and was not teleported").addRecipients(sender).send(log);
			}
			else if (!player.getTeam().hasHeadquarters())
			{
				new Message.Builder("No team headquarters set for team " + player.getTeam().getName() + " for " + player.getName()).addRecipients(sender).send(log);
			}
			else
			{
				player.teleport(player.getTeam().getHeadquarters().getLocation());
				new Message.Builder("You have been teleported to the team headquarters by an admin").addRecipients(player).send(log);
			}
		}
		new Message.Builder("Players teleported").addRecipients(sender).send(log);
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
		return "team teleallhq";
	}

	@Override
	public String getDescription()
	{
		return "teleports everyone to their headquarters";
	}
}
