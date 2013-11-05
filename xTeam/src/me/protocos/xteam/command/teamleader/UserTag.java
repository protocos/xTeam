package me.protocos.xteam.command.teamleader;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.exception.TeamException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class UserTag extends UserCommand
{
	private String desiredTag;

	public UserTag()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		team.setTag(desiredTag);
		teamPlayer.sendMessage("The team tag has been set to " + ChatColor.AQUA + desiredTag);
		for (ITeamPlayer mate : teamPlayer.getOnlineTeammates())
		{
			mate.sendMessage("The team tag has been set to " + ChatColor.AQUA + desiredTag);
		}
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		super.checkRequirements(originalSender, parseCommand);
		desiredTag = parseCommand.get(1);
		Requirements.checkPlayerHasTeam(teamPlayer);
		Requirements.checkPlayerIsTeamLeader(teamPlayer);
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
}
