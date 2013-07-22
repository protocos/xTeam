package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import java.io.InvalidClassException;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.TeamDoesNotExistException;
import me.protocos.xteam.core.exception.TeamException;
import org.bukkit.command.CommandSender;

public class AdminTpAll extends ServerAdminCommand
{
	private String teamName;
	Team changeTeam;

	public AdminTpAll()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		for (String teammember : changeTeam.getOnlinePlayers())
		{
			TeamPlayer p = new TeamPlayer(teammember);
			if (p.isOnline())
			{
				p.sendMessage("You have been teleported to " + originalSender.getName());
				p.teleport(teamPlayer.getLocation());
			}
		}
		originalSender.sendMessage("Players teleported");
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, InvalidClassException
	{
		super.checkRequirements(originalSender, parseCommand);
		teamName = parseCommand.get(1);
		changeTeam = xTeam.tm.getTeam(teamName);
		if (changeTeam == null)
		{
			throw new TeamDoesNotExistException();
		}
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
}
