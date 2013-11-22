package me.protocos.xteam.command.action;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamOrPlayerDoesNotExistException;
import me.protocos.xteam.core.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class InfoAction
{
	public InfoAction()
	{
	}

	public void actOn(CommandSender sender, String other)
	{
		Team infoTeam = xTeam.getInstance().getTeamManager().getTeam(other);
		if (infoTeam == null)
			infoTeam = xTeam.getInstance().getPlayerManager().getPlayer(other).getTeam();
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
		if (xTeam.getInstance().getTeamManager().getTeam(other) == null)
		{
			if (xTeam.getInstance().getPlayerManager().getPlayer(other) == null)
			{
				throw new TeamOrPlayerDoesNotExistException();
			}
			Requirements.checkPlayerHasTeam(xTeam.getInstance().getPlayerManager().getPlayer(other));
		}
	}
}
