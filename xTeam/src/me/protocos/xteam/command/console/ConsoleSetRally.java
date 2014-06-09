package me.protocos.xteam.command.console;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.Message;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.Location;

public class ConsoleSetRally extends ConsoleCommand
{
	private BukkitUtil bukkitUtil;
	private String teamName, world;
	private double X, Y, Z;
	private ITeam team;

	public ConsoleSetRally(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
		bukkitUtil = teamPlugin.getBukkitUtil();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		team = teamCoordinator.getTeam(teamName);
		team.setRally(new Location(bukkitUtil.getWorld(world), X, Y, Z, 0.0F, 0.0F));
		Message message = new Message.Builder("You set the rally point for " + team.getName() + " to X:" + X + " Y:" + Y + " Z:" + Z).addRecipients(sender).build();
		message.send(log);
		message = new Message.Builder("Team rally point has been set (expires in " + Configuration.RALLY_DELAY + " minutes)").addRecipients(team).excludeRecipients(sender).build();
		message.send(log);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		teamName = commandContainer.getArgument(1);
		world = commandContainer.getArgument(2);
		X = Double.parseDouble(commandContainer.getArgument(3));
		Y = Double.parseDouble(commandContainer.getArgument(4));
		Z = Double.parseDouble(commandContainer.getArgument(5));
		Requirements.checkTeamExists(teamCoordinator, teamName);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("set")
				.oneOrMore("rally")
				.whiteSpace()
				.anyString()
				.whiteSpace()
				.anyString()
				.whiteSpace()
				.anyDouble()
				.whiteSpace()
				.anyDouble()
				.whiteSpace()
				.anyDouble()
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getUsage()
	{
		return "team setrally [Team] [World] [X] [Y] [Z]";
	}

	@Override
	public String getDescription()
	{
		return "set rally point of team";
	}
}
