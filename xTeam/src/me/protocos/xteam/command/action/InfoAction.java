package me.protocos.xteam.command.action;

import me.protocos.xteam.xTeamPlugin;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamOrPlayerDoesNotExistException;
import me.protocos.xteam.core.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class InfoAction
{
	public InfoAction()
	{
	}

	public void actOn(CommandSender sender, String other)
	{
		Team infoTeam = xTeamPlugin.getInstance().getTeamManager().getTeam(other);
		if (infoTeam == null)
			infoTeam = xTeamPlugin.getInstance().getTeamManager().getTeamByTag(other);
		if (infoTeam == null)
			infoTeam = xTeamPlugin.getInstance().getPlayerManager().getPlayer(other).getTeam();
		if (infoTeam != null)
		{
			if (sender instanceof ConsoleCommandSender)
			{
				sender.sendMessage(infoTeam.getPrivateInfo());
			}
			else if (sender instanceof Player)
			{
				Player player = CommonUtil.assignFromType(sender, Player.class);
				ITeamPlayer teamPlayer = xTeamPlugin.getInstance().getPlayerManager().getPlayer(player);
				if (teamPlayer.isOnSameTeam(infoTeam))
					teamPlayer.sendMessage(infoTeam.getPrivateInfo());
				else
					teamPlayer.sendMessage(infoTeam.getPublicInfo());
			}
		}
	}

	public void checkRequirements(String other) throws TeamOrPlayerDoesNotExistException, TeamPlayerHasNoTeamException
	{
		if (xTeamPlugin.getInstance().getTeamManager().getTeam(other) == null && xTeamPlugin.getInstance().getTeamManager().getTeamByTag(other) == null)
		{
			if (xTeamPlugin.getInstance().getPlayerManager().getPlayer(other) == null)
			{
				throw new TeamOrPlayerDoesNotExistException();
			}
			Requirements.checkPlayerHasTeam(xTeamPlugin.getInstance().getPlayerManager().getPlayer(other));
		}
	}
}
