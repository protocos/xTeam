package me.protocos.xteam.command.console;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ConsoleCommand;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.command.CommandSender;

public class ConsoleTag extends ConsoleCommand
{
	private String teamName, desiredTag;

	public ConsoleTag()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		Team team = xTeam.getInstance().getTeamManager().getTeam(teamName);
		team.setTag(desiredTag);
		originalSender.sendMessage("The team tag has been " + ChatColorUtil.positiveMessage("set") + " to " + desiredTag);
		team.sendMessage("The team tag has been " + ChatColorUtil.positiveMessage("set") + " to " + desiredTag + " by an admin");
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		teamName = parseCommand.get(1);
		desiredTag = parseCommand.get(2);
		Team team = xTeam.getInstance().getTeamManager().getTeam(teamName);
		Requirements.checkTeamExists(teamName);
		Requirements.checkTeamNameAlreadyUsed(desiredTag, team);
		Requirements.checkTeamNameAlphaNumeric(desiredTag);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("tag")
				.whiteSpace()
				.anyString()
				.whiteSpace()
				.anyString()
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getUsage()
	{
		return "/team tag [Team] [Tag]";
	}

	@Override
	public String getDescription()
	{
		return "set team tag";
	}
}
