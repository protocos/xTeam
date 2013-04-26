package me.protocos.xteam.testing;

import java.util.Map;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class FakeOfflinePlayer implements OfflinePlayer
{
	private String name;
	private boolean isOp;
	private boolean isOnline;
	private boolean hasPlayedBefore;

	public FakeOfflinePlayer()
	{
		this("offline");
	}

	public FakeOfflinePlayer(String name)
	{
		this.name = name;
	}

	public FakeOfflinePlayer(String name, boolean isOp, boolean isOnline, boolean hasPlayedBefore)
	{
		this.name = name;
		this.isOp = isOp;
		this.isOnline = isOnline;
		this.hasPlayedBefore = hasPlayedBefore;
	}

	public void setOnline(boolean isOnline)
	{
		this.isOnline = isOnline;
	}

	public void setHasPlayedBefore(boolean hasPlayedBefore)
	{
		this.hasPlayedBefore = hasPlayedBefore;
	}

	@Override
	public boolean isOp()
	{
		return isOp;
	}

	@Override
	public void setOp(boolean isOp)
	{
		this.isOp = isOp;
	}

	@Override
	public Map<String, Object> serialize()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location getBedSpawnLocation()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getFirstPlayed()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getLastPlayed()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public Player getPlayer()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasPlayedBefore()
	{
		return hasPlayedBefore;
	}

	@Override
	public boolean isBanned()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOnline()
	{
		return isOnline;
	}

	@Override
	public boolean isWhitelisted()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setBanned(boolean arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setWhitelisted(boolean arg0)
	{
		// TODO Auto-generated method stub

	}

}
