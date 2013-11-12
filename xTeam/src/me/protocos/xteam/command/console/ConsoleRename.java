package me.protocos.xteam.command.console;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeamPlugin;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import org.bukkit.command.CommandSender;

public class ConsoleRename extends ConsoleCommand
{
	private String teamName, desiredName;

	public ConsoleRename()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		Team team = xTeamPlugin.getInstance().getTeamManager().getTeam(teamName);
		xTeamPlugin.getInstance().getTeamManager().removeTeam(teamName);
		team.setName(desiredName);
		xTeamPlugin.getInstance().getTeamManager().addTeam(team);
		originalSender.sendMessage("You " + ChatColorUtil.positiveMessage("renamed") + " the team to " + desiredName);
		team.sendMessage("The team has been " + ChatColorUtil.positiveMessage("renamed") + " to " + desiredName);
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		teamName = parseCommand.get(1);
		desiredName = parseCommand.get(2);
		Requirements.checkTeamExists(teamName);
		Requirements.checkTeamAlreadyExists(desiredName);
		Requirements.checkTeamNameAlphaNumeric(desiredName);
	}

	@Override
	public String getPattern()
	{
		return "re" + patternOneOrMore("name") + WHITE_SPACE + ANY_CHARS + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}

	@Override
	public String getUsage()
	{
		return "/team rename [Team] [Name]";
	}

	@Override
	public String getDescription()
	{
		return "rename a team";
	}
}
