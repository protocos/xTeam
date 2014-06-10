package me.protocos.xteam.command.action;

import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.exception.TeamOrPlayerDoesNotExistException;
import me.protocos.xteam.exception.TeamPlayerHasNoTeamException;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class InfoAction
{
	private ITeamCoordinator teamCoordinator;
	private IPlayerFactory playerFactory;

	public InfoAction(ITeamCoordinator teamCoordinator, IPlayerFactory playerFactory)
	{
		this.teamCoordinator = teamCoordinator;
		this.playerFactory = playerFactory;
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
				for (String line : infoTeam.getInfoFor(infoTeam).split("\n"))
					sender.sendMessage(line);
			}
			else if (sender instanceof ITeamPlayer)
			{
				ITeamPlayer teamPlayer = CommonUtil.assignFromType(sender, ITeamPlayer.class);
				teamPlayer.sendMessage(infoTeam.getInfoFor(teamPlayer));
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
