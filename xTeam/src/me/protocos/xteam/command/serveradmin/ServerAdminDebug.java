package me.protocos.xteam.command.serveradmin;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.Message;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.LocationUtil;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.entity.Player;

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
			new Message.Builder("Player yaw: " + Math.round(serverAdmin.getLocation().getYaw()))
					.addRecipients(serverAdmin)
					.send(log);
		}
		else if (subCommand.equalsIgnoreCase("directions"))
		{
			for (Player otherPlayer : bukkitUtil.getOnlinePlayers())
			{
				Message message = new Message.Builder("Yaw angle to " + otherPlayer.getName() + ": " + Math.round(LocationUtil.getYawAngleToLocation(serverAdmin.getLocation(), otherPlayer.getLocation())) + "°")
						.addRecipients(serverAdmin)
						.excludeRecipients(otherPlayer)
						.send(log);
				message.setMessage("Location angle to " + otherPlayer.getName() + ": " + Math.round(LocationUtil.getAngleBetween(serverAdmin.getLocation(), otherPlayer.getLocation())) + "°");
				message.send(log);
				message.setMessage("Direction angle to " + otherPlayer.getName() + ": " + LocationUtil.getRelativeAngleBetween(serverAdmin.getLocation(), otherPlayer.getLocation()));
				message.send(log);
			}
		}
		else
		{
			new Message.Builder("Options are: /team debug {yaw/directions}")
					.addRecipients(serverAdmin)
					.send(log);
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
