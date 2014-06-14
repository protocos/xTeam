package me.protocos.xteam.model;

import java.util.List;
import me.protocos.xteam.entity.ITeamEntity;
import me.protocos.xteam.message.MessageUtil;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public class NullHeadquarters implements IHeadquarters
{
	@Override
	public String getName()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Location getLocation()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public World getWorld()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Server getServer()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public int getRelativeX()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public int getRelativeY()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public int getRelativeZ()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public double getDistanceTo(ILocatable entity)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean teleportTo(ILocatable entity)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Entity> getNearbyEntities(int radius)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isValid()
	{
		return false;
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder(131, 31).toHashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof NullHeadquarters))
			return false;

		return true;
	}

	@Override
	public String getInfoFor(ITeamEntity entity)
	{
		return ChatColor.RESET + "Team Headquarters: " + MessageUtil.red("None set");
	}

	@Override
	public String toString()
	{
		return "";
	}
}
