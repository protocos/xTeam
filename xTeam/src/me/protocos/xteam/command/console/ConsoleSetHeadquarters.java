package me.protocos.xteam.command.console;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.Message;
import me.protocos.xteam.model.Headquarters;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.PatternBuilder;

public class ConsoleSetHeadquarters extends ConsoleCommand
{
	private BukkitUtil bukkitUtil;
	private String teamName, world;
	private double X, Y, Z;
	private ITeam team;

	public ConsoleSetHeadquarters(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
		bukkitUtil = teamPlugin.getBukkitUtil();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		team = teamCoordinator.getTeam(teamName);
		team.setHeadquarters(new Headquarters(bukkitUtil.getWorld(world), X, Y, Z, 0.0F, 0.0F));
		team.setTimeHeadquartersLastSet(System.currentTimeMillis());
		Message message = new Message.Builder("You set the team headquarters").addRecipients(sender).build();
		message.send(log);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		teamName = commandContainer.getArgument(1);
		Requirements.checkTeamExists(teamCoordinator, teamName);
		world = commandContainer.getArgument(2);
		X = Double.parseDouble(commandContainer.getArgument(3));
		Y = Double.parseDouble(commandContainer.getArgument(4));
		Z = Double.parseDouble(commandContainer.getArgument(5));
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("set")
				.oneOrMore("head")
				.oneOrMore("quarters")
				.whiteSpace()
				.anyString()
				.whiteSpace()
				.anyString()
				.whiteSpace()
				.anyNumber()
				.whiteSpace()
				.anyNumber()
				.whiteSpace()
				.anyNumber()
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getUsage()
	{
		return "team sethq [Team] [World] [X] [Y] [Z]";
	}

	@Override
	public String getDescription()
	{
		return "set headquarters of team";
	}
}
