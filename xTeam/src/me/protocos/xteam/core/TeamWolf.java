package me.protocos.xteam.core;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Wolf;

public class TeamWolf implements ITeamWolf
{
	Wolf wolf;

	public TeamWolf(Wolf w)
	{
		this.wolf = w;
	}
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof TeamWolf))
			return false;

		TeamWolf rhs = (TeamWolf) obj;
		return new EqualsBuilder().append(getOwner(), rhs.getOwner()).isEquals();
	}
	@Override
	public double getDistanceTo(ITeamEntity entity)
	{
		return getLocation().distance(entity.getLocation());
	}
	@Override
	public double getHealth()
	{
		return wolf.getHealth();
	}
	@Override
	public Location getLocation()
	{
		return wolf.getLocation();
	}
	@Override
	public TeamPlayer getOwner()
	{
		if (wolf.getOwner() != null)
			return new TeamPlayer(wolf.getOwner().getName());
		return null;
	}
	@Override
	public int getRelativeX()
	{
		Location loc = getLocation();
		return (int) Math.round(loc.getX());
	}
	@Override
	public int getRelativeY()
	{
		Location loc = getLocation();
		return (int) Math.round(loc.getY());
	}
	@Override
	public int getRelativeZ()
	{
		Location loc = getLocation();
		return (int) Math.round(loc.getZ());
	}
	@Override
	public Server getServer()
	{
		return wolf.getServer();
	}
	@Override
	public Team getTeam()
	{
		if (hasTeam())
			return getOwner().getTeam();
		return null;
	}
	@Override
	public World getWorld()
	{
		return wolf.getWorld();
	}
	public int hashCode()
	{
		return new HashCodeBuilder(53, 89).append(getOwner()).append(getLocation()).toHashCode();
	}
	@Override
	public boolean hasOwner()
	{
		return getOwner() != null;
	}
	@Override
	public boolean hasTeam()
	{
		return hasOwner() && getOwner().hasTeam();
	}
	@Override
	public boolean isEnemy(ITeamEntity entity)
	{
		if (this.isOnSameTeam(entity))
			return false;
		return true;
	}
	@Override
	public boolean isOnline()
	{
		return true;
	}
	@Override
	public boolean isOnSameTeam(ITeamEntity entity)
	{
		if (isOnline() && entity.isOnline())
		{
			return getTeam().equals(entity.getTeam());
		}
		return false;
	}
	@Override
	public boolean teleport(ITeamEntity entity)
	{
		if (isOnline() && entity.isOnline())
		{
			return wolf.teleport(entity.getLocation());
		}
		return false;
	}
	@Override
	public boolean teleport(Location location)
	{
		if (isOnline())
		{
			return wolf.teleport(location);
		}
		return false;
	}
	@Override
	public String toString()
	{
		String wolfData = "";
		return wolfData;
	}
}
