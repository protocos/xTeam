package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.Headquarters;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import org.bukkit.command.CommandSender;

public class AdminSetHeadquarters extends ServerAdminCommand
{
	private String teamName;

	public AdminSetHeadquarters()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		Team changeTeam = xTeam.getInstance().getTeamManager().getTeam(teamName);
		changeTeam.setHQ(new Headquarters(player.getLocation()));
		originalSender.sendMessage("You " + ChatColorUtil.positiveMessage("set") + " the team headquarters for team " + teamName);
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		teamName = parseCommand.get(1);
		Requirements.checkTeamExists(teamName);
	}

	@Override
	public String getPattern()
	{
		return "set" + patternOneOrMore("hq") + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.serveradmin.core.sethq";
	}

	@Override
	public String getUsage()
	{
		return "/team sethq [Team]";
	}

	@Override
	public String getDescription()
	{
		return "set team headquarters for team";
	}
}
