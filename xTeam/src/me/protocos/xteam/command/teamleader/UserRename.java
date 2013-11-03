package me.protocos.xteam.command.teamleader;

import static me.protocos.xteam.util.StringUtil.*;
import java.io.InvalidClassException;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.exception.*;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class UserRename extends UserCommand
{
	private String newName;

	public UserRename()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		xTeam.getTeamManager().removeTeam(team.getName());
		team.setName(newName);
		xTeam.getTeamManager().addTeam(team);
		for (ITeamPlayer mate : teamPlayer.getOnlineTeammates())
		{
			mate.sendMessage("The team has been renamed to " + ChatColor.AQUA + newName);
		}
		originalSender.sendMessage("You renamed the team to " + ChatColor.AQUA + newName);
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, InvalidClassException
	{
		super.checkRequirements(originalSender, parseCommand);
		newName = parseCommand.get(1);
		if (!teamPlayer.hasTeam())
		{
			throw new TeamPlayerHasNoTeamException();
		}
		if (!teamPlayer.isLeader())
		{
			throw new TeamPlayerNotLeaderException();
		}
		if (Data.TEAM_TAG_LENGTH != 0 && newName.length() > Data.TEAM_TAG_LENGTH)
		{
			throw new TeamNameTooLongException();
		}
		if (xTeam.getTeamManager().contains(newName) && !team.getName().equalsIgnoreCase(newName))
		{
			throw new TeamAlreadyExistsException();
		}
		if (Data.ALPHA_NUM && !newName.matches(ALPHA_NUMERIC))
		{
			throw new TeamNameNotAlphaException();
		}
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("re") + patternOneOrMore("name") + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.leader.core.rename";
	}
	@Override
	public String getUsage()
	{
		return "/team rename [Name]";
	}
}
