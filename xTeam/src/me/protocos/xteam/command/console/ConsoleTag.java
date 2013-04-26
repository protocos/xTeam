package me.protocos.xteam.command.console;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseConsoleCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.exception.*;
import me.protocos.xteam.util.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

public class ConsoleTag extends BaseConsoleCommand
{
	private String teamName, newTag;

	public ConsoleTag()
	{
		super();
	}
	public ConsoleTag(ConsoleCommandSender sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		Team team = xTeam.tm.getTeam(teamName);
		team.setTag(newTag);
		originalSender.sendMessage("The team tag has been set to " + newTag);
		for (String p : team.getOnlinePlayers())
		{
			TeamPlayer mate = new TeamPlayer(p);
			mate.sendMessage("The team tag has been set to " + ChatColor.AQUA + newTag + ChatColor.WHITE + " by an admin");
		}
	}
	@Override
	public void checkRequirements() throws TeamException
	{
		if (parseCommand.size() == 3)
		{
			teamName = parseCommand.get(1);
			newTag = parseCommand.get(2);
		}
		else
		{
			throw new TeamInvalidCommandException();
		}
		Team desiredTeam = xTeam.tm.getTeam(teamName);
		if (desiredTeam == null)
		{
			throw new TeamDoesNotExistException();
		}
		if (!newTag.equalsIgnoreCase(desiredTeam.getName()) && StringUtil.toLowerCase(xTeam.tm.getAllTeamNames()).contains(newTag.toLowerCase()))
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
	public String getUsage()
	{
		return baseCommand + " tag [Team] [Tag]";
	}
}