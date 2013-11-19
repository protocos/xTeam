package me.protocos.xteam.command.serveradmin;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ServerAdminCommand;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.command.CommandSender;

public class ServerAdminTpAll extends ServerAdminCommand
{
	private String teamName;

	public ServerAdminTpAll()
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
				teammate.teleport(teamPlayer.getLocation());
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
		return new PatternBuilder()
				.oneOrMore("tele")
				.oneOrMore("port")
				.oneOrMore("all")
				.whiteSpace()
				.anyString()
				.whiteSpaceOptional()
				.toString();
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
