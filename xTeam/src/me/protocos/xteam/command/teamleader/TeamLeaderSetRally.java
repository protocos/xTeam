package me.protocos.xteam.command.teamleader;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.api.command.TeamLeaderCommand;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import org.bukkit.command.CommandSender;

public class TeamLeaderSetRally extends TeamLeaderCommand
{
	public TeamLeaderSetRally()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		team.setRally(teamPlayer.getLocation());
		originalSender.sendMessage("You " + ChatColorUtil.positiveMessage("set") + " the team rally point");
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		Requirements.checkTeamNotHasRally(team);
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

	@Override
	public String getDescription()
	{
		return "set rally point for the team";
	}
}
