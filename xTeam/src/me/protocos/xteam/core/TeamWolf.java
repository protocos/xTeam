package me.protocos.xteam.core;

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
	@Override
	public double getDistanceTo(ITeamEntity entity)
	{
		return getLocation().distance(entity.getLocation());
	}
	@Override
	public int getHealth()
	{
		return wolf.getHealth();
	}
	@Override
	public Location getLocation()
	{
		return wolf.getLocation();
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
	@Override
	public boolean hasTeam()
	{
		return hasOwner() && getOwner().hasTeam();
	}
	@Override
	public boolean isOnline()
	{
		return true;
	}
	@Override
	public TeamPlayer getOwner()
	{
		return new TeamPlayer(wolf.getOwner().getName());
	}
	@Override
	public boolean hasOwner()
	{
		return getOwner() != null;
	}
	@Override
	public String toString()
	{
		String wolfData = "";
		return wolfData;
	}
	public boolean equals(TeamWolf otherWolf)
	{
		return getOwner().equals(otherWolf.getOwner()) && getLocation().equals(otherWolf.getLocation());
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
	public boolean teleport(ITeamEntity entity)
	{
		if (isOnline() && entity.isOnline())
		{
			return wolf.teleport(entity.getLocation());
		}
		return false;
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
	public boolean isEnemy(ITeamEntity entity)
	{
		if (this.isOnSameTeam(entity))
			return false;
		return true;
	}
}
