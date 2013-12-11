package me.protocos.xteam.entity;

import java.util.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.collections.HashList;
import me.protocos.xteam.api.entity.ITeamEntity;
import me.protocos.xteam.api.entity.ITeamPlayer;
import me.protocos.xteam.api.model.ILocatable;
import me.protocos.xteam.api.model.ITeam;
import me.protocos.xteam.command.action.TeleportScheduler;
import me.protocos.xteam.core.Configuration;
import me.protocos.xteam.model.Headquarters;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.CommonUtil;
import me.protocos.xteam.util.MessageUtil;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.*;
import org.bukkit.entity.Entity;

public class Team implements ITeam
{
	private String name;
	private String tag;
	private String leader;
	private Set<String> players;
	private Set<String> admins;
	private Headquarters headquarters;
	private long timeHeadquartersSet;
	private boolean openJoining;
	private boolean defaultTeam;
	private Location rally;

	public static class Builder
	{
		//required
		private final String name;

		//optional
		private String tag = "";
		private String leader = "";
		private Set<String> players = new HashSet<String>();
		private Set<String> admins = new HashSet<String>();;
		private Headquarters headquarters = null;
		private long timeHeadquartersSet = 0L;
		private boolean openJoining = false;
		private boolean defaultTeam = false;

		public Builder(String name)
		{
			this.name = name;
		}

		public Builder tag(@SuppressWarnings("hiding") String tag)
		{
			this.tag = tag;
			return this;
		}

		public Builder leader(@SuppressWarnings("hiding") String leader)
		{
			this.leader = leader;
			this.admins.add(leader);
			this.players.add(leader);
			return this;
		}

		public Builder admins(@SuppressWarnings("hiding") String... admins)
		{
			return this.admins(Arrays.asList(admins));
		}

		public Builder admins(@SuppressWarnings("hiding") Collection<String> admins)
		{
			this.admins.addAll(admins);
			this.players.addAll(admins);
			return this;
		}

		public Builder players(@SuppressWarnings("hiding") String... players)
		{
			return this.players(Arrays.asList(players));
		}

		public Builder players(@SuppressWarnings("hiding") Collection<String> players)
		{
			this.players.addAll(players);
			return this;
		}

		public Builder hq(@SuppressWarnings("hiding") Headquarters headquarters)
		{
			this.headquarters = headquarters;
			return this;
		}

		public Builder timeHeadquartersSet(@SuppressWarnings("hiding") long timeHeadquartersSet)
		{
			this.timeHeadquartersSet = timeHeadquartersSet;
			return this;
		}

		public Builder openJoining(@SuppressWarnings("hiding") boolean openJoining)
		{
			this.openJoining = openJoining;
			return this;
		}

		public Builder defaultTeam(@SuppressWarnings("hiding") boolean defaultTeam)
		{
			this.defaultTeam = defaultTeam;
			return this;
		}

		public Team build()
		{
			if (tag.equals(""))
				tag = name;
			return new Team(this);
		}
	}

	private Team(Builder builder)
	{
		name = builder.name;
		tag = builder.tag;
		openJoining = builder.openJoining;
		defaultTeam = builder.defaultTeam;
		timeHeadquartersSet = builder.timeHeadquartersSet;
		headquarters = builder.headquarters;
		leader = builder.leader;
		players = builder.players;
		admins = builder.admins;
	}

	public boolean addPlayer(String player)
	{
		return players.add(player);
	}

	public boolean containsPlayer(String player)
	{
		return players.contains(player);
	}

	public boolean removePlayer(String player)
	{
		if (leader.equals(player))
			this.leader = "";
		if (admins.contains(player))
			admins.remove(player);
		return players.remove(player);
	}

	public void demote(String player)
	{
		if (players.contains(player) && admins.contains(player) && !leader.equals(player))
		{
			admins.remove(player);
		}
	}

	public int hashCode()
	{
		return new HashCodeBuilder(11, 71).append(name).append(tag).append(leader).toHashCode();
	}

	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof Team))
			return false;

		Team rhs = (Team) obj;
		return new EqualsBuilder().append(name, rhs.name).append(tag, rhs.tag).append(leader, rhs.leader).isEquals();
	}

	public Set<String> getAdmins()
	{
		return admins;
	}

	public Headquarters getHeadquarters()
	{
		return headquarters;
	}

	public String getLeader()
	{
		return leader;
	}

	public Set<String> getPlayers()
	{
		return players;
	}

	public long getTimeLastSet()
	{
		return timeHeadquartersSet;
	}

	public boolean hasHeadquarters()
	{
		return getHeadquarters() != null;
	}

	public boolean hasLeader()
	{
		return !leader.equals("");
	}

	public boolean hasTag()
	{
		return !this.tag.equals(this.name);
	}

	public boolean isDefaultTeam()
	{
		return defaultTeam;
	}

	public boolean isEmpty()
	{
		return size() == 0;
	}

	public boolean isOpenJoining()
	{
		return openJoining;
	}

	public void promote(String player)
	{
		if (players.contains(player) && !admins.contains(player))
		{
			admins.add(player);
		}
	}

	public void setAdmins(Set<String> admins)
	{
		this.admins = admins;
	}

	public void setDefaultTeam(boolean defaultTeam)
	{
		this.defaultTeam = defaultTeam;
	}

	public void setHeadquarters(Headquarters headquarters)
	{
		this.headquarters = headquarters;
	}

	public void setLeader(String leader)
	{
		this.leader = leader;
		if (!players.contains(leader))
			players.add(leader);
		if (!admins.contains(leader))
			admins.add(leader);
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setOpenJoining(boolean openJoining)
	{
		this.openJoining = openJoining;
	}

	public void setPlayers(Set<String> players)
	{
		this.players = players;
	}

	public void setTimeLastSet(long timeHeadquartersSet)
	{
		this.timeHeadquartersSet = timeHeadquartersSet;
	}

	public int size()
	{
		return this.getPlayers().size();
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
			long timeHeadquartersSet = Long.parseLong(teamProperties.get("timeHeadquartersSet") != null ? teamProperties.get("timeHeadquartersSet") : "0");
			String hq = teamProperties.get("Headquarters") != null ? teamProperties.get("Headquarters") : (hq = teamProperties.get("hq") != null ? teamProperties.get("hq") : "");
			if (teamProperties.containsKey("world"))
				hq = teamProperties.get("world") + "," + hq;
			String leader = teamProperties.get("leader");// != null ? teamProperties.get("leader") : "";
			String admins = teamProperties.get("admins");// != null ? teamProperties.get("admins") : "";
			String players = teamProperties.get("players");// != null ? teamProperties.get("players") : "";
			Team team = new Team.Builder(name).tag(tag).openJoining(openJoining).defaultTeam(defaultTeam).timeHeadquartersSet(timeHeadquartersSet).build();
			if (!hq.endsWith("0.0,0.0,0.0,0.0,0.0") && !hq.equals(""))
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

	public String toString()
	{
		String teamData = "";
		teamData += "name:" + getName();
		teamData += " tag:" + getTag();
		teamData += " open:" + isOpenJoining();
		teamData += " default:" + isDefaultTeam();
		teamData += " timeHeadquartersSet:" + getTimeLastSet();
		teamData += " hq:" + (getHeadquarters() == null ? "" : getHeadquarters().toString());
		teamData += " leader:" + getLeader();
		teamData += " admins:" + getAdmins().toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "");
		teamData += " players:" + getPlayers().toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "");
		return teamData;
	}

	public void setTag(String tag)
	{
		this.tag = tag;
	}

	public String getTag()
	{
		return tag;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public int getRelativeX()
	{
		return getHeadquarters().getRelativeX();
	}

	@Override
	public int getRelativeY()
	{
		return getHeadquarters().getRelativeY();
	}

	@Override
	public int getRelativeZ()
	{
		return getHeadquarters().getRelativeZ();
	}

	@Override
	public Team getTeam()
	{
		return this;
	}

	@Override
	public World getWorld()
	{
		if (this.hasHeadquarters())
			return getHeadquarters().getWorld();
		return null;
	}

	@Override
	public Server getServer()
	{
		return Bukkit.getServer();
	}

	@Override
	public double getDistanceTo(ILocatable entity)
	{
		return this.getHeadquarters().getLocation().distance(entity.getLocation());
	}

	@Override
	public boolean hasTeam()
	{
		return true;
	}

	@Override
	public boolean teleportTo(ILocatable entity)
	{
		return false;
	}

	@Override
	public boolean isOnSameTeam(ITeamEntity entity)
	{
		if (entity instanceof Team)
		{
			Team otherTeam = (Team) entity;
			return this.equals(otherTeam);
		}
		if (entity instanceof TeamPlayer)
		{
			TeamPlayer otherPlayer = (TeamPlayer) entity;
			return this.containsPlayer(otherPlayer.getName());
		}
		return false;
	}

	@Override
	public boolean isOnline()
	{
		return hasHeadquarters();
	}

	@Override
	public boolean isVulnerable()
	{
		return false;
	}

	@Override
	public List<TeamPlayer> getOnlineTeammates()
	{
		return xTeam.getInstance().getPlayerManager().getOnlineTeammatesOf(this);
	}

	@Override
	public List<OfflineTeamPlayer> getOfflineTeammates()
	{
		return xTeam.getInstance().getPlayerManager().getOfflineTeammatesOf(this);
	}

	@Override
	public List<ITeamPlayer> getTeammates()
	{
		return xTeam.getInstance().getPlayerManager().getTeammatesOf(this);
	}

	@Override
	public List<Entity> getNearbyEntities(int radius)
	{
		if (this.hasHeadquarters())
			return this.getHeadquarters().getNearbyEntities(radius);
		return CommonUtil.emptyList();
	}

	@Override
	public Location getLocation()
	{
		return this.getHeadquarters().getLocation();
	}

	@Override
	public void sendMessage(String message)
	{
		MessageUtil.sendMessageToTeam(this, message);
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
		if (this.getAdmins().size() > 1)
			message += "\n" + (ChatColor.RESET + "Team Admins - " + ChatColor.GREEN + this.getAdmins().toString().replaceAll("\\[|\\]" + (this.hasLeader() ? "|" + this.getLeader() + ", " : ""), ""));
		message += "\n" + (ChatColor.RESET + "Team Joining - " + (this.isOpenJoining() ? (ChatColorUtil.positiveMessage("Open")) : (ChatColorUtil.negativeMessage("Closed"))));
		if (usePublicData)
			message += "\n" + (ChatColor.RESET + "Team Headquarters - " + (this.hasHeadquarters() ? (ChatColorUtil.positiveMessage("Set")) : (ChatColor.RED + "None set")));
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

	public void setRally(final Location location)
	{
		rally = location;
		final Team finalTeam = this;
		class RemoveRally implements Runnable
		{
			@Override
			public void run()
			{
				rally = null;
				TeleportScheduler.getInstance().clearTeamRally(finalTeam);
				sendMessage("Team rally has been " + ChatColorUtil.positiveMessage("refreshed"));
			}
		}
		BukkitUtil.getScheduler().scheduleSyncDelayedTask(BukkitUtil.getxTeam(), new RemoveRally(), Configuration.RALLY_DELAY * BukkitUtil.ONE_MINUTE_IN_TICKS);
	}

	public boolean hasRally()
	{
		return rally != null;
	}

	public Location getRally()
	{
		return rally;
	}
}
