package me.protocos.xteam.command.action;

import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.core.IPlayerManager;
import me.protocos.xteam.core.ITeamManager;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.exception.TeamOrPlayerDoesNotExistException;
import me.protocos.xteam.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class InfoAction
{
	private ITeamManager teamManager;
	private IPlayerManager playerManager;

	public InfoAction(ITeamManager teamManager, IPlayerManager playerManager)
	{
		this.teamManager = teamManager;
		this.playerManager = playerManager;
	}

	public void actOn(CommandSender sender, String other)
	{
		ITeam infoTeam = teamManager.getTeam(other);
		if (infoTeam == null)
			infoTeam = playerManager.getPlayer(other).getTeam();
		if (infoTeam != null)
		{
			if (sender instanceof ConsoleCommandSender)
			{
				sender.sendMessage(infoTeam.getPrivateInfo());
			}
			else if (sender instanceof ITeamPlayer)
			{
				ITeamPlayer teamPlayer = CommonUtil.assignFromType(sender, ITeamPlayer.class);
				if (teamPlayer.isOnSameTeam(infoTeam))
					teamPlayer.sendMessage(infoTeam.getPrivateInfo());
				else
					teamPlayer.sendMessage(infoTeam.getPublicInfo());
			}
		}
	}

	public void checkRequirements(String other) throws TeamOrPlayerDoesNotExistException, TeamPlayerHasNoTeamException
	{
		if (teamManager.getTeam(other) == null)
		{
			if (playerManager.getPlayer(other) == null)
			{
				throw new TeamOrPlayerDoesNotExistException();
			}
			Requirements.checkPlayerHasTeam(playerManager.getPlayer(other));
		}
	}
}
