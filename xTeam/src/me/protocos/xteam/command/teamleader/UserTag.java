package me.protocos.xteam.command.teamleader;

import static me.protocos.xteam.util.StringUtil.*;
import java.io.InvalidClassException;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.*;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class UserTag extends UserCommand
{
	private String newTag;

	public UserTag()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		team.setTag(newTag);
		teamPlayer.sendMessage("The team tag has been set to " + ChatColor.AQUA + newTag);
		for (String p : teamPlayer.getOnlineTeammates())
		{
			TeamPlayer mate = new TeamPlayer(p);
			mate.sendMessage("The team tag has been set to " + ChatColor.AQUA + newTag);
		}
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, InvalidClassException
	{
		super.checkRequirements(originalSender, parseCommand);
		newTag = parseCommand.get(1);
		if (!teamPlayer.hasTeam())
		{
			throw new TeamPlayerHasNoTeamException();
		}
		if (!teamPlayer.isLeader())
		{
			throw new TeamPlayerNotLeaderException();
		}
		if (Data.TEAM_TAG_LENGTH != 0 && newTag.length() > Data.TEAM_TAG_LENGTH)
		{
			throw new TeamNameTooLongException();
		}
		if (Data.ALPHA_NUM && !newTag.matches(ALPHA_NUMERIC))
		{
			throw new TeamNameNotAlphaException();
		}
		if (!newTag.equalsIgnoreCase(team.getName()) && toLowerCase(xTeam.tm.getAllTeamNames()).contains(newTag.toLowerCase()))
		{
			throw new TeamNameConflictsWithTagException();
		}
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
