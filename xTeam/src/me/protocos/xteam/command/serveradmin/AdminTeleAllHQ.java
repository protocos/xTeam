package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.core.exception.TeamInvalidCommandException;
import me.protocos.xteam.core.exception.TeamPlayerDoesNotExistException;
import me.protocos.xteam.core.exception.TeamPlayerPermissionException;
import me.protocos.xteam.util.PermissionUtil;
import org.bukkit.entity.Player;

public class AdminTeleAllHQ extends ServerAdminCommand
{
	public AdminTeleAllHQ(Player sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		Player[] players = Data.BUKKIT.getOnlinePlayers();
		for (Player p : players)
		{
			TeamPlayer otherPlayer = new TeamPlayer(p);
			Team team = otherPlayer.getTeam();
			{
				if (team == null)
				{
					player.sendMessage(otherPlayer.getName() + " does not have a team and was not teleported");
				}
				else if (!team.hasHQ())
				{
					player.sendMessage("No team headquarters set for team " + team.getName() + " for " + p.getName());
				}
				else
				{
					otherPlayer.teleport(team.getHeadquarters());
					otherPlayer.sendMessage("You have been teleported to the team headquarters by an admin");
				}
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
		if (parseCommand.size() == 1)
		{

		}
		else
		{
			throw new TeamInvalidCommandException();
		}
	}
	@Override
	public String getPattern()
	{
		return "tele" + patternOneOrMore("allhq") + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.serveradmin.core.teleallhq";
	}
	@Override
	public String getUsage()
	{
		return baseCommand + " teleallhq";
	}
}
