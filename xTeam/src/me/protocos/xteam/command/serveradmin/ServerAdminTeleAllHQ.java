package me.protocos.xteam.command.serveradmin;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.TeamPlayer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.entity.Player;

public class ServerAdminTeleAllHQ extends ServerAdminCommand
{
	private BukkitUtil bukkitUtil;

	public ServerAdminTeleAllHQ(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
		bukkitUtil = teamPlugin.getBukkitUtil();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		Player[] players = bukkitUtil.getOnlinePlayers();
		for (Player p : players)
		{
			TeamPlayer otherPlayer = playerManager.getPlayer(p);
			ITeam playerTeam = otherPlayer.getTeam();
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
