package me.protocos.xteam.entity;

import java.util.*;
import me.protocos.xteam.XTeam;
import me.protocos.xteam.model.Headquarters;
import me.protocos.xteam.model.IHeadquarters;
import me.protocos.xteam.model.ILocatable;
import me.protocos.xteam.model.NullHeadquarters;
import me.protocos.xteam.util.CommonUtil;
import me.protocos.xteam.util.MessageUtil;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public class SimpleTeam implements ITeam
{
	private String name;
	private String tag;
	private String leader;
	private Set<String> admins;
	private Set<String> players;
	private IHeadquarters headquarters;
	private Location rally;
	private boolean openJoining;
	private long timeHeadquartersLastSet;

	public static class Builder
	{
		private String name;
		private String tag;
		private String leader = "";
		private Set<String> admins = new HashSet<String>();
		private Set<String> players = new HashSet<String>();
		private IHeadquarters headquarters = new NullHeadquarters();
		private boolean openJoining = false;
		private long timeHeadquartersLastSet = 0L;

		public Builder(String name)
		{
			this.name = name;
			this.tag = name;
		}

		public Builder tag(@SuppressWarnings("hiding") String tag)
		{
			this.tag = tag;
			return this;
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

		public Builder headquarters(@SuppressWarnings("hiding") IHeadquarters headquarters)
		{
			this.headquarters = headquarters;
			return this;
		}

		public Builder openJoining(@SuppressWarnings("hiding") boolean openJoining)
		{
			this.openJoining = openJoining;
			return this;
		}

		public Builder timeHeadquartersLastSet(@SuppressWarnings("hiding") long timeHeadquartersLastSet)
		{
			this.timeHeadquartersLastSet = timeHeadquartersLastSet;
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
		this.tag = builder.tag;
		this.leader = builder.leader;
		this.admins = builder.admins;
		this.players = builder.players;
		this.headquarters = builder.headquarters;
		this.openJoining = builder.openJoining;
		this.timeHeadquartersLastSet = builder.timeHeadquartersLastSet;
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
		//EXTERNAL call
		return XTeam.getInstance().getPlayerManager().getOnlineTeammatesOf(this);
	}

	@Override
	public List<OfflineTeamPlayer> getOfflineTeammates()
	{
		//EXTERNAL call
		return XTeam.getInstance().getPlayerManager().getOfflineTeammatesOf(this);
	}

	@Override
	public List<ITeamPlayer> getTeammates()
	{
		//EXTERNAL call
		return XTeam.getInstance().getPlayerManager().getTeammatesOf(this);
	}

	@Override
	public void sendMessage(String message)
	{
		//EXTERNAL call
		MessageUtil.sendMessageToTeam(this, message);
	}

	@Override
	public String getPublicInfo()
	{
		// TODO Auto-generated method stub
		//		StringBuilder publicInfo = new StringBuilder();
		//		publicInfo.append("Team Name: ").append(this.getName()).append("\n");
		//		if (this.hasTag())
		//			publicInfo.append("Team Tag: ").append(this.getTag()).append("\n");
		//
		//		return publicInfo.toString();
		return null;
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
		return headquarters.getLocation();
	}

	@Override
	public World getWorld()
	{
		return headquarters.getWorld();
	}

	@Override
	public Server getServer()
	{
		return headquarters.getServer();
	}

	@Override
	public int getRelativeX()
	{
		return headquarters.getRelativeX();
	}

	@Override
	public int getRelativeY()
	{
		return headquarters.getRelativeY();
	}

	@Override
	public int getRelativeZ()
	{
		return headquarters.getRelativeZ();
	}

	@Override
	public double getDistanceTo(ILocatable entity)
	{
		return headquarters.getDistanceTo(entity);
	}

	@Override
	public boolean teleportTo(ILocatable entity)
	{
		return headquarters.teleportTo(entity);
	}

	@Override
	public List<Entity> getNearbyEntities(int radius)
	{
		return headquarters.getNearbyEntities(radius);
	}

	@Override
	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public String getName()
	{
		return this.name;
	}

	@Override
	public void setTag(String tag)
	{
		this.tag = tag;
	}

	@Override
	public String getTag()
	{
		return this.tag;
	}

	@Override
	public boolean hasTag()
	{
		return !this.tag.equals(this.name);
	}

	@Override
	public void setHeadquarters(IHeadquarters headquarters)
	{
		this.headquarters = headquarters;
	}

	@Override
	public IHeadquarters getHeadquarters()
	{
		return headquarters;
	}

	@Override
	public boolean hasHeadquarters()
	{
		return (headquarters instanceof Headquarters);
	}

	@Override
	public boolean addPlayer(String player)
	{
		return players.add(player);
	}

	@Override
	public boolean containsPlayer(String player)
	{
		List<String> allPlayers = new ArrayList<String>(players);
		return CommonUtil.containsIgnoreCase(allPlayers, player);
	}

	@Override
	public boolean containsAdmin(String admin)
	{
		List<String> allAdmins = new ArrayList<String>(admins);
		return CommonUtil.containsIgnoreCase(allAdmins, admin);
	}

	@Override
	public boolean removePlayer(String player)
	{
		if (!this.leader.equals(player))
		{
			admins.remove(player);
			players.remove(player);
			return true;
		}
		return false;
	}

	@Override
	public boolean isEmpty()
	{
		return players.isEmpty();
	}

	@Override
	public void promote(String player)
	{
		if (this.players.contains(player) &&
				!this.leader.equals(player))
		{
			this.admins.add(player);
		}
	}

	@Override
	public void demote(String player)
	{
		this.admins.remove(player);
	}

	@Override
	public void setOpenJoining(boolean openJoining)
	{
		this.openJoining = openJoining;
	}

	@Override
	public boolean isOpenJoining()
	{
		return this.openJoining;
	}

	@Override
	public int size()
	{
		return players.size();
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
	public void setLeader(String leader)
	{
		if (this.players.contains(leader))
		{
			this.leader = leader;
			this.admins.remove(leader);
		}
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
	public void setRally(Location rally)
	{
		this.rally = rally;
	}

	@Override
	public Location getRally()
	{
		return rally;
	}

	@Override
	public boolean hasRally()
	{
		return rally != null;
	}

	@Override
	public void setTimeHeadquartersLastSet(long timeHeadquartersLastSet)
	{
		this.timeHeadquartersLastSet = timeHeadquartersLastSet;
	}

	@Override
	public long getTimeHeadquartersLastSet()
	{
		return this.timeHeadquartersLastSet;
	}

	@Override
	public void setPlayers(Set<String> players)
	{
		this.players = players;
	}

}
