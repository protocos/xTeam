package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import org.bukkit.command.CommandSender;

public class UserCreate extends UserCommand
{
	private String desiredName;

	public UserCreate()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		String leader = teamPlayer.getName();
		Team newTeam = Team.createTeamWithLeader(desiredName, leader);
		xTeam.getTeamManager().addTeam(newTeam);
		Data.lastCreated.put(leader, Long.valueOf(System.currentTimeMillis()));
		originalSender.sendMessage("You " + ChatColorUtil.positiveMessage("created") + " " + desiredName);
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		super.checkRequirements(originalSender, parseCommand);
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
		return patternOneOrMore("create") + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
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
}
