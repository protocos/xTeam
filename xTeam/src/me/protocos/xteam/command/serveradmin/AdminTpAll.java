package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseServerAdminCommand;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.exception.*;
import me.protocos.xteam.util.PermissionUtil;
import org.bukkit.entity.Player;

public class AdminTpAll extends BaseServerAdminCommand
{
	private String teamName;

	public AdminTpAll()
	{
		super();
	}
	public AdminTpAll(Player sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		Team team = xTeam.tm.getTeam(teamName);
		for (String teammember : team.getOnlinePlayers())
		{
			TeamPlayer p = new TeamPlayer(teammember);
			if (p.isOnline())
			{
				p.sendMessage("You have been teleported to " + player.getName());
				p.teleport(player.getLocation());
			}
		}
		originalSender.sendMessage("Players teleported");
	}
	@Override
	public void checkRequirements() throws TeamException
	{
		if (!PermissionUtil.hasPermission(originalSender, getPermissionNode()))
		{
			throw new TeamPlayerPermissionException();
		}
		if (player == null)
		{
			throw new TeamPlayerDoesNotExistException();
		}
		if (parseCommand.size() == 2)
		{
			teamName = parseCommand.get(1);
		}
		else
		{
			throw new TeamInvalidCommandException();
		}
		Team team = xTeam.tm.getTeam(teamName);
		if (team == null)
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
		return baseCommand + " tpall [Team]";
	}
}