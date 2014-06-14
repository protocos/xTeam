package me.protocos.xteam.command.serveradmin;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.TeamPlayer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.Message;
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
			TeamPlayer otherPlayer = playerFactory.getPlayer(p);
			ITeam playerTeam = otherPlayer.getTeam();
			{
				if (playerTeam == null)
				{
					new Message.Builder(otherPlayer.getName() + " does not have a team and was not teleported").addRecipients(serverAdmin).send(log);
				}
				else if (!playerTeam.hasHeadquarters())
				{
					new Message.Builder("No team headquarters set for team " + playerTeam.getName() + " for " + p.getName()).addRecipients(serverAdmin).send(log);
				}
				else
				{
					otherPlayer.teleport(playerTeam.getHeadquarters().getLocation());
					new Message.Builder("You have been teleported to the team headquarters by an admin").addRecipients(otherPlayer).send(log);
				}
			}
		}
		new Message.Builder("Players teleported").addRecipients(serverAdmin).send(log);
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
