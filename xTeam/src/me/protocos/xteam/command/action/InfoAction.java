package me.protocos.xteam.command.action;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.entity.ConsoleTeamEntity;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.exception.TeamOrPlayerDoesNotExistException;
import me.protocos.xteam.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.message.Message;
import me.protocos.xteam.model.ILog;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class InfoAction
{
	private ITeamCoordinator teamCoordinator;
	private IPlayerFactory playerFactory;
	private ILog log;

	public InfoAction(TeamPlugin teamPlugin)
	{
		this.teamCoordinator = teamPlugin.getTeamCoordinator();
		this.playerFactory = teamPlugin.getPlayerFactory();
		this.log = teamPlugin.getLog();
	}

	public void actOn(CommandSender sender, String other)
	{
		ITeam infoTeam = teamCoordinator.getTeam(other);
		if (infoTeam == null)
			infoTeam = playerFactory.getPlayer(other).getTeam();
		if (infoTeam != null)
		{
			if (sender instanceof ConsoleCommandSender)
			{
				ConsoleTeamEntity consoleTeamEntity = new ConsoleTeamEntity(sender);
				for (String line : infoTeam.getInfoFor(consoleTeamEntity).split("\n"))
					new Message.Builder(line).addRecipients(consoleTeamEntity).disableFormatting().send(log);
			}
			else if (sender instanceof ITeamPlayer)
			{
				ITeamPlayer teamPlayer = CommonUtil.assignFromType(sender, ITeamPlayer.class);
				for (String line : infoTeam.getInfoFor(teamPlayer).split("\n"))
					new Message.Builder(line).addRecipients(teamPlayer).disableFormatting().send(log);
			}
		}
	}

	public void checkRequirements(String other) throws TeamOrPlayerDoesNotExistException, TeamPlayerHasNoTeamException
	{
		if (teamCoordinator.getTeam(other) == null)
		{
			if (!playerFactory.getPlayer(other).hasPlayedBefore())
			{
				throw new TeamOrPlayerDoesNotExistException();
			}
			Requirements.checkPlayerHasTeam(playerFactory.getPlayer(other));
		}
	}
}
