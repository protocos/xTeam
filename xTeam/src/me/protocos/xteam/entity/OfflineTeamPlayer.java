package me.protocos.xteam.entity;

import java.util.List;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.IPermissible;
import me.protocos.xteam.core.IPlayerManager;
import me.protocos.xteam.core.ITeamManager;
import me.protocos.xteam.model.ILocatable;
import me.protocos.xteam.util.CommonUtil;
import me.protocos.xteam.util.MessageUtil;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

public class OfflineTeamPlayer implements ITeamPlayer
{
	private IPlayerManager playerManager;
	private ITeamManager teamManager;
	private OfflinePlayer player;

	public OfflineTeamPlayer(TeamPlugin teamPlugin, OfflinePlayer player)
	{
		this.teamManager = teamPlugin.getTeamManager();
		this.playerManager = teamPlugin.getPlayerManager();
		this.player = player;
	}

	@Override
	public ITeam getTeam()
	{
		return teamManager.getTeamByPlayer(player.getName());
	}

	@Override
	public String getName()
	{
		return player.getName();
	}

	@Override
	public boolean hasTeam()
	{
		return this.getTeam() != null;
	}

	@Override
	public boolean isOnSameTeam(ITeamEntity otherEntity)
	{
		if (this.hasTeam() && otherEntity.hasTeam())
		{
			return getTeam().equals(otherEntity.getTeam());
		}
		return false;
	}

	@Override
	public boolean isOnline()
	{
		return player.isOnline();
	}

	@Override
	public boolean isVulnerable()
	{
		return true;
	}

	@Override
	public void sendMessage(String message)
	{
	}

	@Override
	public List<ITeamPlayer> getTeammates()
	{
		return playerManager.getTeammatesOf(this);
	}

	@Override
	public double getHealth()
	{
		return -1.0;
	}

	@Override
	public boolean hasPlayedBefore()
	{
		return player.hasPlayedBefore();
	}

	@Override
	public List<TeamPlayer> getOnlineTeammates()
	{
		return playerManager.getOnlineTeammatesOf(this);
	}

	@Override
	public List<OfflineTeamPlayer> getOfflineTeammates()
	{
		return playerManager.getOfflineTeammatesOf(this);
	}

	@Override
	public void setReturnLocation(Location returnLocation)
	{
		playerManager.setReturnLocation(this, returnLocation);
	}

	@Override
	public Location getReturnLocation()
	{
		return playerManager.getReturnLocation(this.getName());
	}

	@Override
	public void removeReturnLocation()
	{
		playerManager.setReturnLocation(this, null);
	}

	@Override
	public boolean hasReturnLocation()
	{
		return playerManager.getReturnLocation(this.getName()) != null;
	}

	@Override
	public void setLastAttacked(long lastAttacked)
	{
		playerManager.setLastAttacked(this, lastAttacked);
	}

	@Override
	public long getLastAttacked()
	{
		return playerManager.getLastAttacked(this.getName());
	}

	@Override
	public void sendMessageToTeam(String message)
	{
		MessageUtil.sendMessageToTeam(this, message);
	}

	@Override
	public boolean isLeader()
	{
		if (this.hasTeam())
		{
			return getTeam().getLeader().equals(this.getName());
		}
		return false;
	}

	@Override
	public boolean isAdmin()
	{
		if (hasTeam())
		{
			return getTeam().isAdmin(this.getName());
		}
		return false;
	}

	@Override
	public String getLastPlayed()
	{
		return CommonUtil.formatDateToMonthDay(player.getLastPlayed());
	}

	@Override
	public boolean isDamaged()
	{
		return false;
	}

	@Override
	public void setLastTeleported(long lastTeleported)
	{
		playerManager.setLastTeleported(this, lastTeleported);
	}

	@Override
	public long getLastTeleported()
	{
		return playerManager.getLastTeleported(this.getName());
	}

	@Override
	public boolean hasPermission(IPermissible permission)
	{
		return false;
	}

	@Override
	public boolean isOp()
	{
		return player.isOp();
	}

	@Override
	public boolean teleportTo(ILocatable entity)
	{
		return false;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof ITeamPlayer))
			return false;

		ITeamPlayer rhs = (ITeamPlayer) obj;
		return new EqualsBuilder().append(this.getName(), rhs.getName()).isEquals();
	}

	@Override
	public String getPublicInfo()
	{
		return MessageUtil.negativeMessage("    " + this.getName());
	}

	@Override
	public String getPrivateInfo()
	{
		return MessageUtil.negativeMessage("    " + this.getName()) + " was last online on " + this.getLastPlayed();
	}
}
