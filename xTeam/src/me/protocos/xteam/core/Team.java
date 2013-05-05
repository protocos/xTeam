package me.protocos.xteam.core;

import java.util.ArrayList;
import java.util.List;
import me.protocos.xteam.util.CommonUtil;
import me.protocos.xteam.util.HashList;
import org.bukkit.World;

public class Team implements ITeam
{
	private String name;
	private String tag;
	private String leader;
	private List<String> players;
	private List<String> admins;
	private TeamHeadquarters headquarters;
	private long timeHeadquartersSet;
	private boolean openJoining;
	private boolean defaultTeam;

	public static class Builder
	{
		//required
		private final String name;

		//optional
		private String tag = "";
		private String leader = "";
		private List<String> players = new ArrayList<String>();
		private List<String> admins = new ArrayList<String>();;
		private TeamHeadquarters headquarters = null;
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
			return this;
		}
		public Builder players(@SuppressWarnings("hiding") List<String> players)
		{
			this.players = players;
			return this;
		}
		public Builder admins(@SuppressWarnings("hiding") List<String> admins)
		{
			this.admins = admins;
			return this;
		}
		public Builder hq(@SuppressWarnings("hiding") TeamHeadquarters headquarters)
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

	public Team(Builder builder)
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
		if (leader.equalsIgnoreCase(player))
			this.leader = "";
		if (admins.contains(player))
			admins.remove(player);
		return players.remove(player);
	}
	public boolean demote(String player)
	{
		if (players.contains(player) && admins.contains(player) && !leader.equals(player))
		{
			admins.remove(player);
			return true;
		}
		return false;
	}
	public boolean equals(Team team)
	{
		try
		{
			if (getName().equalsIgnoreCase(team.getName()))
				return true;
		}
		catch (NullPointerException e)
		{
			return false;
		}
		return false;
	}
	public List<String> getAdmins()
	{
		return admins;
	}
	public TeamHeadquarters getHeadquarters()
	{
		return headquarters;
	}
	public String getLeader()
	{
		return leader;
	}
	public String getName()
	{
		return name;
	}
	public List<String> getOnlinePlayers()
	{
		List<String> onlinePlayers = new ArrayList<String>();
		for (String p : players)
		{
			TeamPlayer player = new TeamPlayer(p);
			if (player.isOnline())
				onlinePlayers.add(p);
		}
		return onlinePlayers;
	}
	public List<String> getOfflinePlayers()
	{
		List<String> offlinePlayers = new ArrayList<String>();
		for (String p : players)
		{
			TeamPlayer player = new TeamPlayer(p);
			if (!player.isOnline())
				offlinePlayers.add(p);
		}
		return offlinePlayers;
	}
	public List<String> getPlayers()
	{
		return players;
	}
	public long getTimeLastSet()
	{
		return timeHeadquartersSet;
	}
	public boolean hasHQ()
	{
		return getHeadquarters() != null;
	}
	public boolean hasLeader()
	{
		return !leader.equals("");
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
	public boolean promote(String player)
	{
		if (players.contains(player) && !admins.contains(player))
		{
			admins.add(player);
			return true;
		}
		return false;
	}
	public void setAdmins(List<String> admins)
	{
		this.admins = admins;
	}
	public void setDefaultTeam(boolean defaultTeam)
	{
		this.defaultTeam = defaultTeam;
	}
	public void setHQ(TeamHeadquarters headquarters)
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
	public void setPlayers(List<String> players)
	{
		this.players = players;
	}
	public void setTimeLastSet(long timeHeadquartersSet)
	{
		this.timeHeadquartersSet = timeHeadquartersSet;
	}
	public int size()
	{
		return getPlayers().size();
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
				World world = Data.BUKKIT.getWorld(locationData[0]);
				double X = Double.parseDouble(locationData[1]);
				double Y = Double.parseDouble(locationData[2]);
				double Z = Double.parseDouble(locationData[3]);
				float yaw = Float.parseFloat(locationData[4]);
				float pitch = Float.parseFloat(locationData[5]);
				team.setHQ(new TeamHeadquarters(world, X, Y, Z, yaw, pitch));
			}
			team.setPlayers(players == null ? new ArrayList<String>() : CommonUtil.toList(players, ","));
			team.setAdmins(admins == null ? new ArrayList<String>() : CommonUtil.toList(admins, ","));
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
		teamData += " hq:" + (getHeadquarters() == null ? "" : getHeadquarters().getWorld().getName() + "," + getHeadquarters().getX() + "," + getHeadquarters().getY() + "," + getHeadquarters().getZ() + "," + getHeadquarters().getYaw() + "," + getHeadquarters().getPitch());
		teamData += " leader:" + (getLeader() == null ? "" : getLeader());
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
	public void sendMessageToTeam(String message)
	{
		List<String> onlinePlayers = getOnlinePlayers();
		for (String p : onlinePlayers)
		{
			ITeamPlayer player = new TeamPlayer(p);
			player.sendMessage(message);
		}
	}
}
