package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import java.io.InvalidClassException;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamDoesNotExistException;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.core.exception.TeamNameConflictsWithTagException;
import me.protocos.xteam.core.exception.TeamNameNotAlphaException;
import me.protocos.xteam.util.StringUtil;
import org.bukkit.command.CommandSender;

public class AdminTag extends ServerAdminCommand
{
	private String teamName, newTag;
	private Team changeTeam;

	public AdminTag()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		changeTeam.setTag(newTag);
		if (!changeTeam.containsPlayer(originalSender.getName()))
			originalSender.sendMessage("The team tag has been set to " + newTag);
		changeTeam.sendMessage("The team tag has been set to " + newTag + " by an admin");
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, InvalidClassException
	{
		super.checkRequirements(originalSender, parseCommand);
		teamName = parseCommand.get(1);
		newTag = parseCommand.get(2);
		changeTeam = xTeam.tm.getTeam(teamName);
		if (changeTeam == null)
		{
			throw new TeamDoesNotExistException();
		}
		if (!newTag.equalsIgnoreCase(changeTeam.getName()) && StringUtil.toLowerCase(xTeam.tm.getAllTeamNames()).contains(newTag.toLowerCase()))
		{
			throw new TeamNameConflictsWithTagException();
		}
		if (Data.ALPHA_NUM && !newTag.matches(ALPHA_NUMERIC))
		{
			throw new TeamNameNotAlphaException();
		}
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("tag") + WHITE_SPACE + ANY_CHARS + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.serveradmin.core.tag";
	}
	@Override
	public String getUsage()
	{
		return "/team tag [Team] [Tag]";
	}
}
