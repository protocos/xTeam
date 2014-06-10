package me.protocos.xteam.entity;

import java.util.List;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.IPermissible;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.message.Message;
import me.protocos.xteam.message.MessageUtil;
import me.protocos.xteam.model.ILocatable;
import me.protocos.xteam.model.ILog;
import me.protocos.xteam.util.CommonUtil;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

public class OfflineTeamPlayer implements ITeamPlayer
{
	private ILog log;
	private IPlayerFactory playerFactory;
	private ITeamCoordinator teamCoordinator;
	private OfflinePlayer player;

	public OfflineTeamPlayer(TeamPlugin teamPlugin, OfflinePlayer player)
	{
		this.log = teamPlugin.getLog();
		this.teamCoordinator = teamPlugin.getTeamCoordinator();
		this.playerFactory = teamPlugin.getPlayerFactory();
		this.player = player;
	}

	@Override
	public ITeam getTeam()
	{
		return teamCoordinator.getTeamByPlayer(player.getName());
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
		return playerFactory.getTeammatesOf(this);
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
		return playerFactory.getOnlineTeammatesOf(this);
	}

	@Override
	public List<OfflineTeamPlayer> getOfflineTeammates()
	{
		return playerFactory.getOfflineTeammatesOf(this);
	}

	@Override
	public void setReturnLocation(Location returnLocation)
	{
		playerFactory.setReturnLocation(this, returnLocation);
	}

	@Override
	public Location getReturnLocation()
	{
		return playerFactory.getReturnLocation(this.getName());
	}

	@Override
	public void removeReturnLocation()
	{
		playerFactory.setReturnLocation(this, null);
	}

	@Override
	public boolean hasReturnLocation()
	{
		return playerFactory.getReturnLocation(this.getName()) != null;
	}

	@Override
	public void setLastAttacked(long lastAttacked)
	{
		playerFactory.setLastAttacked(this, lastAttacked);
	}

	@Override
	public long getLastAttacked()
	{
		return playerFactory.getLastAttacked(this.getName());
	}

	@Override
	public void sendMessageToTeam(String message)
	{
		//		MessageUtil.sendMessageToTeam(this, message);
		Message m = new Message.Builder(message).addRecipients(this.getTeam()).build();
		m.send(log);
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
		playerFactory.setLastTeleported(this, lastTeleported);
	}

	@Override
	public long getLastTeleported()
	{
		return playerFactory.getLastTeleported(this.getName());
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
	public String getInfoFor(ITeamEntity entity)
	{
		if (this.isOnSameTeam(entity))
			return MessageUtil.red("    " + this.getName()) + " was last online on " + this.getLastPlayed();
		return MessageUtil.red("    " + this.getName());
	}
}
