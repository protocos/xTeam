package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.TeamException;
import org.bukkit.command.CommandSender;

public class AdminTpAll extends ServerAdminCommand
{
	private String teamName;

	public AdminTpAll()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		Team changeTeam = xTeam.getInstance().getTeamManager().getTeam(teamName);
		for (TeamPlayer teammate : changeTeam.getOnlineTeammates())
		{
			if (teammate.isOnline())
			{
				teammate.teleport(player.getLocation());
				teammate.sendMessage("You have been teleported to " + originalSender.getName());
			}
		}
		originalSender.sendMessage("Players teleported");
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
		return "t" + patternOneOrMore("pall") + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.serveradmin.core.tpall";
	}

	@Override
	public String getUsage()
	{
		return "/team tpall [Team]";
	}

	@Override
	public String getDescription()
	{
		return "teleports a team to yourself";
	}
}
