package me.protocos.xteam.command.teamleader;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.exception.*;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class UserRename extends UserCommand
{
	private String desiredName;

	public UserRename()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		xTeam.getTeamManager().removeTeam(team.getName());
		team.setName(desiredName);
		xTeam.getTeamManager().addTeam(team);
		for (ITeamPlayer mate : teamPlayer.getOnlineTeammates())
		{
			mate.sendMessage("The team has been renamed to " + ChatColor.AQUA + desiredName);
		}
		originalSender.sendMessage("You renamed the team to " + ChatColor.AQUA + desiredName);
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		super.checkRequirements(originalSender, parseCommand);
		desiredName = parseCommand.get(1);
		Requirements.checkPlayerHasTeam(teamPlayer);
		Requirements.checkPlayerIsTeamLeader(teamPlayer);
		Requirements.checkTeamNameTooLong(desiredName);
		Requirements.checkTeamNameAlphaNumeric(desiredName);
		Requirements.checkTeamNameAlreadyUsed(desiredName, team);
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
