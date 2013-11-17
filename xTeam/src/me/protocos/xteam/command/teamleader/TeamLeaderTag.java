package me.protocos.xteam.command.teamleader;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.api.command.TeamLeaderCommand;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import org.bukkit.command.CommandSender;

public class TeamLeaderTag extends TeamLeaderCommand
{
	private String desiredTag;

	public TeamLeaderTag()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		team.setTag(desiredTag);
		teamPlayer.sendMessage("The team tag has been " + ChatColorUtil.positiveMessage("set") + " to " + desiredTag);
		for (ITeamPlayer mate : teamPlayer.getOnlineTeammates())
		{
			mate.sendMessage("The team tag has been " + ChatColorUtil.positiveMessage("set") + " to " + desiredTag);
		}
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		desiredTag = parseCommand.get(1);
		Requirements.checkTeamNameTooLong(desiredTag);
		Requirements.checkTeamNameAlphaNumeric(desiredTag);
		Requirements.checkTeamNameAlreadyUsed(desiredTag, team);
	}

	@Override
	public String getPattern()
	{
		return patternOneOrMore("tag") + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.leader.core.tag";
	}

	@Override
	public String getUsage()
	{
		return "/team tag [Tag]";
	}

	@Override
	public String getDescription()
	{
		return "set the team tag";
	}
}
