package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.TeamUserCommand;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.Configuration;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.command.CommandSender;

public class TeamUserCreate extends TeamUserCommand
{
	private String desiredName;

	public TeamUserCreate()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		String leader = teamPlayer.getName();
		Team newTeam = Team.createTeamWithLeader(desiredName, leader);
		xTeam.getInstance().getTeamManager().addTeam(newTeam);
		Configuration.lastCreated.put(leader, Long.valueOf(System.currentTimeMillis()));
		originalSender.sendMessage("You " + ChatColorUtil.positiveMessage("created") + " " + desiredName);
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		desiredName = parseCommand.get(1);
		Requirements.checkPlayerDoesNotHaveTeam(teamPlayer);
		Requirements.checkTeamOnlyJoinDefault(desiredName);
		Requirements.checkTeamNameTooLong(desiredName);
		Requirements.checkPlayerLastCreatedTeam(teamPlayer);
		Requirements.checkTeamNameAlphaNumeric(desiredName);
		Requirements.checkTeamAlreadyExists(desiredName);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("create")
				.whiteSpace()
				.anyString()
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.player.core.create";
	}

	@Override
	public String getUsage()
	{
		return "/team create [Name]";
	}

	@Override
	public String getDescription()
	{
		return "create a team";
	}
}
