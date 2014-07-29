package me.protocos.xteam.fakeobjects;

import java.util.Map;
import me.protocos.api.util.CommonUtil;
import me.protocos.xteam.TeamPlugin;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class FakeOfflinePlayer implements OfflinePlayer
{
	private static TeamPlugin teamPlugin;
	private String name;
	private boolean isOp;
	private boolean isOnline;
	private boolean hasPlayedBefore;

	public static void use(TeamPlugin fakeTeamPlugin)
	{
		FakeOfflinePlayer.teamPlugin = fakeTeamPlugin;
	}

	public static FakeOfflinePlayer get(String name)
	{
		return CommonUtil.assignFromType(teamPlugin.getBukkitUtil().getOfflinePlayer(name), FakeOfflinePlayer.class);
	}

	public static FakeOfflinePlayer online(String name)
	{
		return new FakeOfflinePlayer.Builder().name(name).isOnline(true).build();
	}

	public static FakeOfflinePlayer offline(String name)
	{
		return new FakeOfflinePlayer.Builder().name(name).isOnline(false).build();
	}

	public static FakeOfflinePlayer neverPlayed(String name)
	{
		return new FakeOfflinePlayer.Builder().name(name).isOp(false).isOnline(false).hasPlayedBefore(false).build();
	}

	public static class Builder
	{
		private String name;
		private boolean isOp = true;
		private boolean isOnline = true;
		private boolean hasPlayedBefore = true;

		public Builder name(@SuppressWarnings("hiding") String name)
		{
			this.name = name;
			return this;
		}

		public Builder isOp(@SuppressWarnings("hiding") boolean isOp)
		{
			this.isOp = isOp;
			return this;
		}

		public Builder isOnline(@SuppressWarnings("hiding") boolean isOnline)
		{
			this.isOnline = isOnline;
			return this;
		}

		public Builder hasPlayedBefore(@SuppressWarnings("hiding") boolean hasPlayedBefore)
		{
			this.hasPlayedBefore = hasPlayedBefore;
			return this;
		}

		public FakeOfflinePlayer build()
		{
			return new FakeOfflinePlayer(this);
		}
	}

	private FakeOfflinePlayer(Builder builder)
	{
		this.name = builder.name;
		this.isOp = builder.isOp;
		this.isOnline = builder.isOnline;
		this.hasPlayedBefore = builder.hasPlayedBefore;
	}

	public void setName(String name)
	{
		this.name = name;
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
		return this.isOp;
	}

	@Override
	public void setOp(boolean arg0)
	{
		this.isOp = arg0;
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
		return this.name;
	}

	@Override
	public Player getPlayer()
	{
		return teamPlugin.getBukkitUtil().getPlayer(this.name);
	}

	@Override
	public boolean hasPlayedBefore()
	{
		return this.hasPlayedBefore;
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
		return this.isOnline;
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
