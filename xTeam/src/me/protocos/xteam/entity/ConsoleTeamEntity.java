package me.protocos.xteam.entity;

import java.util.List;
import me.protocos.api.util.CommonUtil;
import org.bukkit.command.CommandSender;

public class ConsoleTeamEntity implements ITeamEntity
{
	private CommandSender sender;

	public ConsoleTeamEntity(CommandSender sender)
	{
		this.sender = sender;
	}

	@Override
	public void sendMessage(String message)
	{
		sender.sendMessage(message);
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
