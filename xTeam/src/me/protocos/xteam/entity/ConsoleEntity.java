package me.protocos.xteam.entity;

import java.util.List;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.command.ConsoleCommandSender;

public class ConsoleEntity implements ITeamEntity
{
	private ConsoleCommandSender sender;

	public ConsoleEntity(ConsoleCommandSender sender)
	{
		this.sender = sender;
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
		return false;
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
	public List<OfflineTeamPlayer> getOfflineTeammates()
	{
		return CommonUtil.emptyList();
	}

	@Override
	public List<TeamPlayer> getOnlineTeammates()
	{
		return CommonUtil.emptyList();
	}

	@Override
	public List<ITeamPlayer> getTeammates()
	{
		return CommonUtil.emptyList();
	}

	@Override
	public void sendMessage(String message)
	{
		sender.sendMessage(message);
	}

	@Override
	public String getPublicInfo()
	{
		return this.getName();
	}

	@Override
	public String getPrivateInfo()
	{
		return this.getName();
	}
}
