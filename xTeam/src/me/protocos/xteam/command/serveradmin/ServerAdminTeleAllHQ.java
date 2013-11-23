package me.protocos.xteam.command.serveradmin;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ServerAdminCommand;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.entity.Player;

public class ServerAdminTeleAllHQ extends ServerAdminCommand
{
	public ServerAdminTeleAllHQ()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		Player[] players = BukkitUtil.getOnlinePlayers();
		for (Player p : players)
		{
			TeamPlayer otherPlayer = xTeam.getInstance().getPlayerManager().getPlayer(p);
			Team playerTeam = otherPlayer.getTeam();
			{
				if (playerTeam == null)
				{
					player.sendMessage(otherPlayer.getName() + " does not have a team and was not teleported");
				}
				else if (!playerTeam.hasHeadquarters())
				{
					player.sendMessage("No team headquarters set for team " + playerTeam.getName() + " for " + p.getName());
				}
				else
				{
					otherPlayer.teleport(playerTeam.getHeadquarters().getLocation());
					otherPlayer.sendMessage("You have been teleported to the team headquarters by an admin");
				}
			}
		}
		player.sendMessage("Players teleported");
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("teleport")
				.oneOrMore("all")
				.oneOrMore("head")
				.oneOrMore("quarters")
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.core.serveradmin.teleallhq";
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
