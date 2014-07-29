package me.protocos.xteam.entity;

import java.util.List;
import me.protocos.api.util.CommonUtil;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.IPermissible;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.data.translator.LocationDataTranslator;
import me.protocos.xteam.data.translator.LongDataTranslator;
import me.protocos.xteam.message.MessageUtil;
import me.protocos.xteam.model.ILocatable;
import me.protocos.xteam.util.BukkitUtil;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public class OfflineTeamPlayer implements ITeamPlayer
{
	private TeamPlugin teamPlugin;
	private IPlayerFactory playerFactory;
	private ITeamCoordinator teamCoordinator;
	private OfflinePlayer player;

	public OfflineTeamPlayer(TeamPlugin teamPlugin, OfflinePlayer player)
	{
		this.teamPlugin = teamPlugin;
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
		if (this.isOnline())
			playerFactory.getPlayer(this.getName()).sendMessage(message);
	}

	@Override
	public List<ITeamPlayer> getTeammates()
	{
		return playerFactory.getTeammatesOf(this);
	}

	@Override
	public int getHungerLevel()
	{
		return -1;
	}

	@Override
	public int getHealthLevel()
	{
		return -1;
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
	public void setLastAttacked(long lastAttacked)
	{
		playerFactory.getPlayerPropertiesFor(this.getName()).put("lastAttacked", lastAttacked, new LongDataTranslator());
	}

	@Override
	public long getLastAttacked()
	{
		return playerFactory.getPlayerPropertiesFor(this.getName()).get("lastAttacked").getValueUsing(new LongDataTranslator());
	}

	@Override
	public void setLastTeleported(long lastTeleported)
	{
		playerFactory.getPlayerPropertiesFor(this.getName()).put("lastTeleported", lastTeleported, new LongDataTranslator());
	}

	@Override
	public long getLastTeleported()
	{
		return playerFactory.getPlayerPropertiesFor(this.getName()).get("lastTeleported").getValueUsing(new LongDataTranslator());
	}

	@Override
	public void setReturnLocation(Location returnLocation)
	{
		playerFactory.getPlayerPropertiesFor(this.getName()).put("returnLocation", returnLocation, new LocationDataTranslator(teamPlugin));
	}

	@Override
	public Location getReturnLocation()
	{
		return playerFactory.getPlayerPropertiesFor(this.getName()).get("returnLocation").getValueUsing(new LocationDataTranslator(teamPlugin));
	}

	@Override
	public boolean hasReturnLocation()
	{
		return this.getReturnLocation() != null;
	}

	@Override
	public void removeReturnLocation()
	{
		playerFactory.getPlayerPropertiesFor(this.getName()).put("returnLocation", null, new LocationDataTranslator(teamPlugin));
	}

	@Override
	public void setLastKnownLocation(Location lastKnownLocation)
	{
		playerFactory.getPlayerPropertiesFor(this.getName()).put("lastKnownLocation", lastKnownLocation, new LocationDataTranslator(teamPlugin));
	}

	@Override
	public Location getLastKnownLocation()
	{
		if (this.isOnline())
			this.setLastKnownLocation(playerFactory.getPlayer(this.getName()).getLocation());
		return playerFactory.getPlayerPropertiesFor(this.getName()).get("lastKnownLocation").getValueUsing(new LocationDataTranslator(teamPlugin));
	}

	//	@Override
	//	public void sendMessageToTeam(String message)
	//	{
	//		new Message.Builder(message).addRecipients(this.getTeam()).send(log);
	//	}

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
		if (entity.isOnSameTeam(this))
			return MessageUtil.red("   " + this.getName()) + " was last online on " + this.getLastPlayed();
		return MessageUtil.red("   " + this.getName());
	}

	@Override
	public Location getLocation()
	{
		return this.getLastKnownLocation();
	}

	@Override
	public World getWorld()
	{
		return this.getLocation().getWorld();
	}

	@Override
	public Server getServer()
	{
		return teamPlugin.getServer();
	}

	@Override
	public int getRelativeX()
	{
		return CommonUtil.round(this.getLocation().getX());
	}

	@Override
	public int getRelativeY()
	{
		return CommonUtil.round(this.getLocation().getY());
	}

	@Override
	public int getRelativeZ()
	{
		return CommonUtil.round(this.getLocation().getZ());
	}

	@Override
	public double getDistanceTo(ILocatable entity)
	{
		if (this.getLocation().getWorld().equals(entity.getLocation().getWorld()))
			return this.getLocation().distance(entity.getLocation());
		return Double.MAX_VALUE;
	}

	@Override
	public List<Entity> getNearbyEntities(int radius)
	{
		return BukkitUtil.getNearbyEntities(this.getLocation(), radius);
	}

	@Override
	public String toString()
	{
		String playerData = "";
		playerData += "name:" + this.getName();
		playerData += " hasPlayed:" + this.hasPlayedBefore();
		playerData += " team:" + (this.hasTeam() ? this.getTeam().getName() : "none");
		playerData += " admin:" + (this.isAdmin() ? "true" : "false");
		playerData += " leader:" + (this.isLeader() ? "true" : "false");
		return playerData;
	}
}
