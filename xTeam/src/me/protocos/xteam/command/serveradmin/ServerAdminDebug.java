package me.protocos.xteam.command.serveradmin;

import org.bukkit.entity.Player;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.Message;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.LocationUtil;
import me.protocos.xteam.util.PatternBuilder;

public class ServerAdminDebug extends ServerAdminCommand
{
	private String subCommand;
	private BukkitUtil bukkitUtil;

	public ServerAdminDebug(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
		this.bukkitUtil = teamPlugin.getBukkitUtil();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		if (subCommand.equalsIgnoreCase("yaw"))
		{
			Message message = new Message.Builder("Player yaw: " + Math.round(player.getLocation().getYaw()))
					.addRecipients(player)
					.build();
			message.send(log);
		}
		else if (subCommand.equalsIgnoreCase("directions"))
		{
			for (Player otherPlayer : bukkitUtil.getOnlinePlayers())
			{
				Message message = new Message.Builder("Yaw angle to " + otherPlayer.getName() + ": " + Math.round(LocationUtil.getYawDiffToLocation(player.getLocation(), otherPlayer.getLocation())) + "°")
						.addRecipients(player)
						.excludeRecipients(otherPlayer)
						.build();
				message.send(log);
				message.setMessage("Location angle to " + otherPlayer.getName() + ": " + Math.round(LocationUtil.getAngleBetween(player.getLocation(), otherPlayer.getLocation())) + "°");
				message.send(log);
			}
		}
		else
		{
			Message message = new Message.Builder("Options are: /team debug {yaw/directions}")
					.addRecipients(player)
					.build();
			message.send(log);
		}
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		subCommand = commandContainer.getArgument(1);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.append("d")
				.oneOrMore("ebug")
				.optional(new PatternBuilder()
						.whiteSpace()
						.anyString())
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.core.serveradmin.debug";
	}

	@Override
	public String getUsage()
	{
		return "/team debug {Option}";
	}

	@Override
	public String getDescription()
	{
		return "server admin debug menu for xTeam";
	}
}
