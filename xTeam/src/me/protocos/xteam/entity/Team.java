package me.protocos.xteam.entity;

import java.util.*;
import me.protocos.xteam.XTeam;
import me.protocos.xteam.collections.HashList;
import me.protocos.xteam.command.action.TeleportScheduler;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.model.Headquarters;
import me.protocos.xteam.model.IHeadquarters;
import me.protocos.xteam.model.ILocatable;
import me.protocos.xteam.model.NullHeadquarters;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.CommonUtil;
import me.protocos.xteam.util.MessageUtil;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public class Team implements ITeam
{
	/*
	 * if you are "leader", you are also in "players"
	 * if you are "leader", you are not in "admins"
	 * if you are "admin", you are also in "players"
	 * if you are "admin", you are not the "leader"
	 */

	private String name;
	private String tag;
	private String leader;
	private Set<String> players;
	private Set<String> admins;
	private IHeadquarters headquarters;
	private long timeHeadquartersLastSet;
	private boolean openJoining;
	//TODO need to make Default team its own class
	private boolean defaultTeam;
	private Location rally;

	public static class Builder
	{
		//required
		private final String name;

		//optional
		private String tag;
		private String leader = "";
		private Set<String> players = new HashSet<String>();
		private Set<String> admins = new HashSet<String>();;
		private IHeadquarters headquarters = new NullHeadquarters();
		private boolean openJoining = false;
		//TODO need to make Default team its own class
		private boolean defaultTeam = false;
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

		public Builder admins(@SuppressWarnings("hiding") Collection<String> admins)
		{
			this.admins.addAll(admins);
			this.players.addAll(admins);
			return this;
		}

		public Builder players(@SuppressWarnings("hiding") String... players)
		{
			this.players.addAll(Arrays.asList(players));
			return this;
		}

		public Builder players(@SuppressWarnings("hiding") Collection<String> players)
		{
			this.players.addAll(players);
			return this;
		}

		public Builder headquarters(@SuppressWarnings("hiding") Headquarters headquarters)
		{
			this.headquarters = headquarters;
			return this;
		}

		public Builder openJoining(@SuppressWarnings("hiding") boolean openJoining)
		{
			this.openJoining = openJoining;
			return this;
		}

		public Builder timeHeadquartersSet(@SuppressWarnings("hiding") long timeHeadquartersLastSet)
		{
			this.timeHeadquartersLastSet = timeHeadquartersLastSet;
			return this;
		}

		public Builder defaultTeam(@SuppressWarnings("hiding") boolean defaultTeam)
		{
			this.defaultTeam = defaultTeam;
			return this;
		}

		public Team build()
		{
			return new Team(this);
		}
	}

	private Team(Builder builder)
	{
		name = builder.name;
		tag = builder.tag;
		leader = builder.leader;
		admins = builder.admins;
		players = builder.players;
		headquarters = builder.headquarters;
		openJoining = builder.openJoining;
		timeHeadquartersLastSet = builder.timeHeadquartersLastSet;
		defaultTeam = builder.defaultTeam;
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
	public boolean setLeader(String leader)
	{
		if (this.players.contains(leader))
		{
			this.leader = leader;
			this.admins.remove(leader);
			return true;
		}
		return false;
	}

	@Override
	public String getLeader()
	{
		return this.leader;
	}

	@Override
	public boolean hasLeader()
	{
		return !"".equals(leader);
	}

	@Override
	public boolean isLeader(String player)
	{
		return this.leader.equals(player);
	}

	//	@Override
	//	public Set<String> getAdmins()
	//	{
	//		return this.admins;
	//	}

	@Override
	public boolean isAdmin(String admin)
	{
		return this.admins.contains(admin);
	}

	@Override
	public Set<String> getPlayers()
	{
		return this.players;
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
	public void setTimeHeadquartersLastSet(long timeHeadquartersSet)
	{
		this.timeHeadquartersLastSet = timeHeadquartersSet;
	}

	@Override
	public long getTimeHeadquartersLastSet()
	{
		return timeHeadquartersLastSet;
	}

	@Override
	public void setDefaultTeam(boolean defaultTeam)
	{
		this.defaultTeam = defaultTeam;
	}

	@Override
	public boolean isDefaultTeam()
	{
		return defaultTeam;
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
		return headquarters.isValid();
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
		return getInfo(true);
	}

	@Override
	public String getPrivateInfo()
	{
		return getInfo(false);
	}

	private String getInfo(boolean usePublicData)
	{
		String message = (ChatColor.RESET + "Team Name - " + ChatColor.GREEN + this.getName());
		if (!this.getTag().equals(this.getName()))
			message += "\n" + (ChatColor.RESET + "Team Tag - " + ChatColor.GREEN + this.getTag());
		if (this.hasLeader())
			message += "\n" + (ChatColor.RESET + "Team Leader - " + ChatColor.GREEN + this.getLeader());
		if (this.admins.size() > 0)
			message += "\n" + (ChatColor.RESET + "Team Admins - " + ChatColor.GREEN + this.admins.toString().replaceAll("\\[|\\]" + (this.hasLeader() ? "|" + this.getLeader() + ", " : ""), ""));
		message += "\n" + (ChatColor.RESET + "Team Joining - " + (this.isOpenJoining() ? (MessageUtil.positiveMessage("Open")) : (MessageUtil.negativeMessage("Closed"))));
		if (usePublicData)
			message += "\n" + (ChatColor.RESET + "Team Headquarters - " + (this.hasHeadquarters() ? (MessageUtil.positiveMessage("Set")) : (ChatColor.RED + "None set")));
		else
			message += "\n" + (ChatColor.RESET + "Team Headquarters - " + (this.hasHeadquarters() ? (ChatColor.GREEN + "X:" + this.getHeadquarters().getRelativeX() + " Y:" + this.getHeadquarters().getRelativeY() + " Z:" + this.getHeadquarters().getRelativeZ()) : (ChatColor.RED + "None set")));
		List<TeamPlayer> onlineTeammates = this.getOnlineTeammates();
		if (onlineTeammates.size() > 0)
		{
			message += "\n" + (ChatColor.RESET + "Teammates online:");
			for (TeamPlayer p : onlineTeammates)
			{
				if (usePublicData)
					message += "\n" + p.getPublicInfo();
				else
					message += "\n" + p.getPrivateInfo();
			}
		}
		List<OfflineTeamPlayer> offlineTeammates = this.getOfflineTeammates();
		if (offlineTeammates.size() > 0)
		{
			message += "\n" + (ChatColor.RESET + "Teammates offline:");
			for (OfflineTeamPlayer p : offlineTeammates)
			{
				if (usePublicData)
					message += "\n" + p.getPublicInfo();
				else
					message += "\n" + p.getPrivateInfo();
			}
		}
		return message;
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
	public boolean addPlayer(String player)
	{
		return players.add(player);
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
		else if (this.leader.equals(player) && players.size() == 1)
		{
			this.leader = "";
			players.remove(player);
			return true;
		}
		return false;
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
	public boolean isEmpty()
	{
		return players.isEmpty();
	}

	@Override
	public boolean promote(String player)
	{
		if (this.players.contains(player) && !this.admins.contains(player) && !this.leader.equals(player))
		{
			this.admins.add(player);
			return true;
		}
		return false;
	}

	@Override
	public boolean demote(String player)
	{
		if (this.players.contains(player) && this.admins.contains(player) && !this.leader.equals(player))
		{
			this.admins.remove(player);
			return true;
		}
		return false;
	}

	@Override
	public void setRally(final Location location)
	{
		rally = location;
		final Team finalTeam = this;
		class RemoveRally implements Runnable
		{
			public void run()
			{
				rally = null;
				TeleportScheduler.getInstance().clearTeamRally(finalTeam);
				sendMessage("Team rally has been " + MessageUtil.positiveMessage("refreshed"));
			}
		}
		BukkitUtil.getScheduler().scheduleSyncDelayedTask(BukkitUtil.getxTeam(), new RemoveRally(), Configuration.RALLY_DELAY * BukkitUtil.ONE_MINUTE_IN_TICKS);
	}

	@Override
	public boolean hasRally()
	{
		return rally != null;
	}

	@Override
	public Location getRally()
	{
		return rally;
	}

	@Override
	public int size()
	{
		return players.size();
	}

	public static Team generateTeamFromProperties(String properties)
	{
		String[] props = properties.split(" ");
		HashList<String, String> teamProperties = new HashList<String, String>();
		for (String prop : props)
		{
			String[] left_right = prop.split(":");
			String left = left_right.length > 0 ? left_right[0] : "";
			String right = left_right.length > 1 ? left_right[1] : "";
			if (!left.equals("") && !right.equals(""))
				teamProperties.put(left, right);
		}
		try
		{
			String name = teamProperties.get("name") != null ? teamProperties.get("name") : "";
			String tag = teamProperties.get("tag") != null ? teamProperties.get("tag") : name;
			boolean openJoining = Boolean.parseBoolean(teamProperties.get("open") != null ? teamProperties.get("open") : "false");
			boolean defaultTeam = Boolean.parseBoolean(teamProperties.get("default") != null ? teamProperties.get("default") : "false");
			//modify timeLastSet from the previous versions
			teamProperties.updateKey("timeLastSet", "timeHeadquartersSet");
			teamProperties.updateKey("timeHeadquartersSet", "timeHeadquartersLastSet");
			long timeHeadquartersSet = Long.parseLong(teamProperties.get("timeHeadquartersLastSet") != null ? teamProperties.get("timeHeadquartersLastSet") : "0");
			String hq = teamProperties.get("Headquarters") != null ? teamProperties.get("Headquarters") : (hq = teamProperties.get("hq") != null ? teamProperties.get("hq") : "");
			if (teamProperties.containsKey("world"))
				hq = teamProperties.get("world") + "," + hq;
			String leader = teamProperties.get("leader");// != null ? teamProperties.get("leader") : "";
			String admins = teamProperties.get("admins");// != null ? teamProperties.get("admins") : "";
			String players = teamProperties.get("players");// != null ? teamProperties.get("players") : "";
			Team team = new Team.Builder(name).tag(tag).openJoining(openJoining).defaultTeam(defaultTeam).timeHeadquartersSet(timeHeadquartersSet).build();
			if (!hq.endsWith("0.0,0.0,0.0,0.0,0.0") && !hq.equals("") && !hq.equals("none"))
			{
				String[] locationData = hq.split(",");
				World world = BukkitUtil.getWorld(locationData[0]);
				double X = Double.parseDouble(locationData[1]);
				double Y = Double.parseDouble(locationData[2]);
				double Z = Double.parseDouble(locationData[3]);
				float yaw = Float.parseFloat(locationData[4]);
				float pitch = Float.parseFloat(locationData[5]);
				team.setHeadquarters(new Headquarters(world, X, Y, Z, yaw, pitch));
			}
			team.setPlayers(players == null ? new HashSet<String>() : new HashSet<String>(CommonUtil.split(players, ",")));
			team.setAdmins(admins == null ? new HashSet<String>() : new HashSet<String>(CommonUtil.split(admins, ",")));
			if (leader != null)
			{
				if (leader.equalsIgnoreCase("default"))
					team.setDefaultTeam(true);
				else
					team.setLeader(leader);
			}
			return team;
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private void setAdmins(Set<String> admins)
	{
		this.admins = admins;
	}

	private void setPlayers(Set<String> players)
	{
		this.players = players;
	}

	public static Team createTeam(String teamName)
	{
		Team team = new Team.Builder(teamName).build();
		return team;
	}

	public static Team createTeamWithLeader(String teamName, String player)
	{
		Team team = new Team.Builder(teamName).players(player).admins(player).leader(player).build();
		return team;
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder(11, 71).append(name).append(tag).append(leader).toHashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof Team))
			return false;

		Team other = (Team) obj;
		return new EqualsBuilder()
				.append(name, other.name)
				.append(tag, other.tag)
				.append(openJoining, other.openJoining)
				.append(defaultTeam, other.defaultTeam)
				.append(timeHeadquartersLastSet, other.timeHeadquartersLastSet)
				.append(headquarters, other.headquarters)
				.append(leader, other.leader)
				.append(admins, other.admins)
				.append(players, other.players)
				.isEquals();
	}

	@Override
	public String toString()
	{
		String teamData = "";
		teamData += "name:" + name;
		teamData += " tag:" + tag;
		teamData += " open:" + openJoining;
		teamData += " default:" + defaultTeam;
		teamData += " timeHeadquartersLastSet:" + timeHeadquartersLastSet;
		//		teamData += " hq:" + (hasHeadquarters() ? headquarters.toString() : "");
		teamData += " hq:" + headquarters;
		teamData += " leader:" + leader;
		teamData += " admins:" + admins.toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "");
		teamData += " players:" + players.toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "");
		return teamData;
	}
}
