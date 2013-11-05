package me.protocos.xteam.command.teamadmin;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.Headquarters;
import me.protocos.xteam.core.exception.TeamException;
import org.bukkit.command.CommandSender;

public class UserSetHeadquarters extends UserCommand
{
	public UserSetHeadquarters()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		team.setHQ(new Headquarters(teamPlayer.getLocation()));
		team.setTimeLastSet(System.currentTimeMillis());
		originalSender.sendMessage("You set the team headquarters");
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		super.checkRequirements(originalSender, parseCommand);
		Requirements.checkPlayerHasTeam(teamPlayer);
		Requirements.checkPlayerIsTeamAdmin(teamPlayer);
		Requirements.checkPlayerNotDamaged(teamPlayer);
		Requirements.checkTeamHeadquartersRecentlySet(team);
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("sethq") + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.admin.core.sethq";
	}
	@Override
	public String getUsage()
	{
		return "/team sethq";
	}
}
