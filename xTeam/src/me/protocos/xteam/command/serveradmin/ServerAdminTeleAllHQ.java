package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ServerAdminCommand;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.BukkitUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ServerAdminTeleAllHQ extends ServerAdminCommand
{
	public ServerAdminTeleAllHQ()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		Player[] players = BukkitUtil.getOnlinePlayers();
		for (Player p : players)
		{
			TeamPlayer otherPlayer = xTeam.getInstance().getPlayerManager().getPlayer(p);
			Team playerTeam = otherPlayer.getTeam();
			{
				if (playerTeam == null)
				{
					originalSender.sendMessage(otherPlayer.getName() + " does not have a team and was not teleported");
				}
				else if (!playerTeam.hasHeadquarters())
				{
					originalSender.sendMessage("No team headquarters set for team " + playerTeam.getName() + " for " + p.getName());
				}
				else
				{
					otherPlayer.teleport(playerTeam.getHeadquarters().getLocation());
					otherPlayer.sendMessage("You have been teleported to the team headquarters by an admin");
				}
			}
		}
		originalSender.sendMessage("Players teleported");
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
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
		return "/team teleallhq";
	}

	@Override
	public String getDescription()
	{
		return "teleports everyone to their headquarters";
	}
}
