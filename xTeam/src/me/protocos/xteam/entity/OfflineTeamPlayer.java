package me.protocos.xteam.entity;

import java.util.List;
import me.protocos.xteam.XTeam;
import me.protocos.xteam.command.IPermissible;
import me.protocos.xteam.model.ILocatable;
import me.protocos.xteam.util.CommonUtil;
import me.protocos.xteam.util.MessageUtil;
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
	public ITeam getTeam()
	{
		return XTeam.getInstance().getTeamManager().getTeamByPlayer(player.getName());
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
		return XTeam.getInstance().getPlayerManager().getTeammatesOf(this);
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
		return XTeam.getInstance().getPlayerManager().getOnlineTeammatesOf(this);
	}

	@Override
	public List<OfflineTeamPlayer> getOfflineTeammates()
	{
		return XTeam.getInstance().getPlayerManager().getOfflineTeammatesOf(this);
	}

	@Override
	public void setReturnLocation(Location returnLocation)
	{
		XTeam.getInstance().getPlayerManager().setReturnLocation(this, returnLocation);
	}

	@Override
	public Location getReturnLocation()
	{
		return XTeam.getInstance().getPlayerManager().getReturnLocation(this.getName());
	}

	@Override
	public void removeReturnLocation()
	{
		XTeam.getInstance().getPlayerManager().setReturnLocation(this, null);
	}

	@Override
	public boolean hasReturnLocation()
	{
		return XTeam.getInstance().getPlayerManager().getReturnLocation(this.getName()) != null;
	}

	@Override
	public void setLastAttacked(long lastAttacked)
	{
		XTeam.getInstance().getPlayerManager().setLastAttacked(this, lastAttacked);
	}

	@Override
	public long getLastAttacked()
	{
		return XTeam.getInstance().getPlayerManager().getLastAttacked(this.getName());
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
		XTeam.getInstance().getPlayerManager().setLastTeleported(this, lastTeleported);
	}

	@Override
	public long getLastTeleported()
	{
		return XTeam.getInstance().getPlayerManager().getLastTeleported(this.getName());
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
