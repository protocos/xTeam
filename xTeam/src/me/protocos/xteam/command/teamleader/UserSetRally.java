package me.protocos.xteam.command.teamleader;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.exception.TeamException;
import org.bukkit.command.CommandSender;

public class UserSetRally extends UserCommand
{
	public UserSetRally()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		team.setRally(teamPlayer);
		System.out.println(team.getRally());
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		Requirements.checkPlayerHasTeam(teamPlayer);
		Requirements.checkPlayerIsTeamLeader(teamPlayer);
		Requirements.checkTeamHasRally(team);
	}

	@Override
	public String getPattern()
	{
		return patternOneOrMore("set") + patternOneOrMore("rally") + OPTIONAL_WHITE_SPACE;
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.leader.core.setrally";
	}

	@Override
	public String getUsage()
	{
		return "/team setrally";
	}
}
