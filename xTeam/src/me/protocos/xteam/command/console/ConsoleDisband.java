package me.protocos.xteam.command.console;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeamPlugin;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamException;
import org.bukkit.command.CommandSender;

public class ConsoleDisband extends ConsoleCommand
{
	private String teamName;

	public ConsoleDisband()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		Team team = xTeamPlugin.getInstance().getTeamManager().getTeam(teamName);
		team.sendMessage("Your team has been disbanded by an admin");
		xTeamPlugin.getInstance().getTeamManager().removeTeam(teamName);
		originalSender.sendMessage("You disbanded " + teamName);
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		teamName = parseCommand.get(1);
		Requirements.checkTeamExists(teamName);
		Team team = xTeamPlugin.getInstance().getTeamManager().getTeam(teamName);
		Requirements.checkTeamIsDefault(team);
	}

	@Override
	public String getPattern()
	{
		return "disband" + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}

	@Override
	public String getUsage()
	{
		return "/team disband [Team]";
	}

	@Override
	public String getDescription()
	{
		return "disband a team";
	}
}