package me.protocos.xteam.entity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import me.protocos.xteam.XTeam;
import me.protocos.xteam.model.Headquarters;
import me.protocos.xteam.model.ILocatable;
import me.protocos.xteam.util.CommonUtil;
import me.protocos.xteam.util.MessageUtil;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public class SimpleTeam implements ITeam
{
	private String name;
	private String leader;
	private Set<String> admins;
	private Set<String> players;

	public static class Builder
	{
		private String name;
		private String leader = "";
		private Set<String> admins = new HashSet<String>();
		private Set<String> players = new HashSet<String>();

		public Builder(String name)
		{
			this.name = name;
		}

		public Builder leader(@SuppressWarnings("hiding") String leader)
		{
			this.leader = leader;
			this.players.add(leader);
			return this;
		}

		public Builder admins(@SuppressWarnings("hiding") String... admins)
		{
			this.admins.addAll(Arrays.asList(admins));
			this.players.addAll(Arrays.asList(admins));
			return this;
		}

		public Builder players(@SuppressWarnings("hiding") String... players)
		{
			this.players.addAll(Arrays.asList(players));
			return this;
		}

		public SimpleTeam build()
		{
			return new SimpleTeam(this);
		}
	}

	private SimpleTeam(Builder builder)
	{
		this.name = builder.name;
		this.leader = builder.leader;
		this.players = builder.players;
		this.admins = builder.admins;
	}

	@Override
	public ITeam getTeam()
	{
		return this;
	}

	@Override
	public boolean hasTeam()
	{
		return true;
	}

	@Override
	public boolean isOnSameTeam(ITeamEntity entity)
	{
		return this.equals(entity.getTeam());
	}

	@Override
	public boolean isOnline()
	{
		return true;
	}

	@Override
	public boolean isVulnerable()
	{
		return false;
	}

	@Override
	public List<TeamPlayer> getOnlineTeammates()
	{
		//TODO not covered in test case
		return XTeam.getInstance().getPlayerManager().getOnlineTeammatesOf(this);
	}

	@Override
	public List<OfflineTeamPlayer> getOfflineTeammates()
	{
		//TODO not covered in test case
		return XTeam.getInstance().getPlayerManager().getOfflineTeammatesOf(this);
	}

	@Override
	public List<ITeamPlayer> getTeammates()
	{
		//TODO not covered in test case
		return XTeam.getInstance().getPlayerManager().getTeammatesOf(this);
	}

	@Override
	public void sendMessage(String message)
	{
		//TODO not covered in test case
		MessageUtil.sendMessageToTeam(this, message);
	}

	@Override
	public String getPublicInfo()
	{
		StringBuilder publicInfo = new StringBuilder();
		publicInfo.append("Team Name: ").append(this.getName()).append("\n");
		if (this.hasTag())
			publicInfo.append("Team Tag: ").append(this.getTag()).append("\n");

		return publicInfo.toString();
	}

	@Override
	public String getPrivateInfo()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location getLocation()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public World getWorld()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Server getServer()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRelativeX()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRelativeY()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRelativeZ()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getDistanceTo(ILocatable entity)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean teleportTo(ILocatable entity)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Entity> getNearbyEntities(int radius)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public String getName()
	{
		return this.name;
	}

	@Override
	public void setTag(String tag)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public String getTag()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasTag()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setHeadquarters(Headquarters headquarters)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public Headquarters getHeadquarters()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasHeadquarters()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addPlayer(String player)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsPlayer(String player)
	{
		List<String> allPlayers = CommonUtil.emptyList();
		allPlayers.add(leader);
		allPlayers.addAll(admins);
		allPlayers.addAll(players);
		return CommonUtil.containsIgnoreCase(allPlayers, player);
	}

	@Override
	public boolean removePlayer(String player)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void promote(String player)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void demote(String player)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setOpenJoining(boolean open)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isOpenJoining()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPlayers(Set<String> players)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public Set<String> getPlayers()
	{
		return this.players;
	}

	@Override
	public Set<String> getAdmins()
	{
		return this.admins;
	}

	@Override
	public void setLeader(String playerName)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public String getLeader()
	{
		return this.leader;
	}

	@Override
	public void setDefaultTeam(boolean defaultTeam)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isDefaultTeam()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setRally(Location location)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public Location getRally()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasRally()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setTimeLastSet(long currentTimeMillis)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public long getTimeLastSet()
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
