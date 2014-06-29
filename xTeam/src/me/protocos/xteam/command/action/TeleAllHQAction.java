package me.protocos.xteam.command.action;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.entity.TeamPlayer;
import me.protocos.xteam.message.Message;
import me.protocos.xteam.model.ILog;
import org.bukkit.command.CommandSender;

public class TeleAllHQAction
{
	private IPlayerFactory playerFactory;
	private ILog log;

	public TeleAllHQAction(TeamPlugin teamPlugin)
	{
		this.playerFactory = teamPlugin.getPlayerFactory();
		this.log = teamPlugin.getLog();
	}

	public void actOn(CommandSender sender)
	{
		for (TeamPlayer player : playerFactory.getOnlinePlayers())
		{
			if (player.hasTeam() && player.getTeam().hasHeadquarters())
			{
				player.teleport(player.getTeam().getHeadquarters().getLocation());
				new Message.Builder("You have been teleported to the team headquarters").addRecipients(player).send(log);
			}
		}
		new Message.Builder("Players teleported").addRecipients(sender).send(log);
	}
}
