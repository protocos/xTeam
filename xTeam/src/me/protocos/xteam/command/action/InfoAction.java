package me.protocos.xteam.command.action;

import java.util.List;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.entity.*;
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
				for (String line : infoTeam.getInfoFor(new ConsoleTeamEntity(log, sender)).split("\n"))
					new Message.Builder(line).addRecipients(sender).disableFormatting().send(log);
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

class ConsoleTeamEntity implements ITeamEntity
{
	private ILog log;
	private CommandSender sender;

	public ConsoleTeamEntity(ILog log, CommandSender sender)
	{
		this.log = log;
		this.sender = sender;
	}

	@Override
	public void sendMessage(String message)
	{
		new Message.Builder(message).addRecipients(sender).send(log);
	}

	@Override
	public ITeam getTeam()
	{
		return null;
	}

	@Override
	public String getName()
	{
		return sender.getName();
	}

	@Override
	public boolean hasTeam()
	{
		return false;
	}

	@Override
	public boolean isOnSameTeam(ITeamEntity entity)
	{
		return true;
	}

	@Override
	public boolean isOnline()
	{
		return true;
	}

	@Override
	public boolean isVulnerable()
	{
		return false;
	}

	@Override
	public List<ITeamPlayer> getTeammates()
	{
		return CommonUtil.emptyList();
	}

	@Override
	public List<TeamPlayer> getOnlineTeammates()
	{
		return CommonUtil.emptyList();
	}

	@Override
	public List<OfflineTeamPlayer> getOfflineTeammates()
	{
		return CommonUtil.emptyList();
	}

	@Override
	public String getInfoFor(ITeamEntity entity)
	{
		return this.getName();
	}

}
