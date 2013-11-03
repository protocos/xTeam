package me.protocos.xteam.core;

import java.util.List;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.core.ILocatable;
import me.protocos.xteam.api.core.ITeamEntity;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.util.MessageUtil;
import me.protocos.xteam.util.StringUtil;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

public class OfflineTeamPlayer implements ITeamPlayer
{
	private OfflinePlayer player;

	public OfflineTeamPlayer(OfflinePlayer player)
	{
		this.player = player;
	}
	@Override
	public Team getTeam()
	{
		return xTeam.getTeamManager().getTeamFromPlayer(player.getName());
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
	public boolean sendMessage(String message)
	{
		return false;
	}

	@Override
	public List<ITeamPlayer> getTeammates()
	{
		return PlayerManager.getTeammatesOf(this);
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
		return PlayerManager.getOnlineTeammatesOf(this);
	}

	@Override
	public List<OfflineTeamPlayer> getOfflineTeammates()
	{
		return PlayerManager.getOfflineTeammatesOf(this);
	}

	@Override
	public void setReturnLocation(Location returnLocation)
	{
		PlayerManager.setReturnLocation(this, returnLocation);
	}
	@Override
	public Location getReturnLocation()
	{
		return PlayerManager.getReturnLocation(this.getName());
	}

	@Override
	public void removeReturnLocation()
	{
		PlayerManager.setReturnLocation(this, null);
	}

	@Override
	public boolean hasReturnLocation()
	{
		return PlayerManager.getReturnLocation(this.getName()) != null;
	}

	@Override
	public void setLastAttacked(long lastAttacked)
	{
		PlayerManager.setLastAttacked(this, lastAttacked);
	}

	@Override
	public long getLastAttacked()
	{
		return PlayerManager.getLastAttacked(this.getName());
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
			return getTeam().getAdmins().contains(this.getName());
		}
		return false;
	}

	@Override
	public String getLastPlayed()
	{
		return StringUtil.formatDateToMonthDay(player.getLastPlayed());
	}

	@Override
	public boolean isDamaged()
	{
		return false;
	}

	@Override
	public void setLastTeleported(long lastTeleported)
	{
		PlayerManager.setLastTeleported(this, lastTeleported);
	}

	@Override
	public long getLastTeleported()
	{
		return PlayerManager.getLastTeleported(this.getName());
	}

	@Override
	public boolean hasPermission(String permissionNode)
	{
		return false;
	}

	@Override
	public boolean isOp()
	{
		return player.isOp();
	}
	//	@Override
	//	public String quickInfo()
	//	{
	//		return this.getName() + " was last online on " + this.getLastPlayed();
	//	}
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
}
