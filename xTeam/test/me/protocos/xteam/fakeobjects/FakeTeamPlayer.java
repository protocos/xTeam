package me.protocos.xteam.fakeobjects;

import java.util.List;
import java.util.Set;
import me.protocos.xteam.command.IPermissible;
import me.protocos.xteam.entity.*;
import me.protocos.xteam.model.ILocatable;
import me.protocos.xteam.util.PermissionUtil;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

public class FakeTeamPlayer implements ITeamPlayer, CommandSender
{
	private enum PermissionLevel
	{
		DELEGATE,
		SERVER_ADMIN,
		TEAM_LEADER,
		TEAM_ADMIN,
		TEAM_USER,
		ALL;
	}

	private PermissionLevel permissionLevel;
	private String name;
	private boolean allPermissions;
	private boolean isOnSameTeam;
	private Location location;

	public static class Builder
	{
		private PermissionLevel permissionLevel = PermissionLevel.DELEGATE;
		private String name = "player";
		private boolean allPermissions = true;
		private boolean isOnSameTeam = true;;
		private Location location = new FakeLocation();

		public Builder name(@SuppressWarnings("hiding") String name)
		{
			this.name = name;
			return this;
		}

		public Builder allPermissions(@SuppressWarnings("hiding") boolean allPermissions)
		{
			this.allPermissions = allPermissions;
			return this;
		}

		public Builder isOnSameTeam(@SuppressWarnings("hiding") boolean isOnSameTeam)
		{
			this.isOnSameTeam = isOnSameTeam;
			return this;
		}

		public Builder location(@SuppressWarnings("hiding") Location location)
		{
			this.location = location;
			return this;
		}

		public Builder serverAdminPermissions()
		{
			permissionLevel = PermissionLevel.SERVER_ADMIN;
			return this;
		}

		public Builder leaderPermissions()
		{
			permissionLevel = PermissionLevel.TEAM_LEADER;
			return this;
		}

		public Builder adminPermissions()
		{
			permissionLevel = PermissionLevel.TEAM_ADMIN;
			return this;
		}

		public Builder userPermissions()
		{
			permissionLevel = PermissionLevel.TEAM_USER;
			return this;
		}

		public Builder allPermissions()
		{
			permissionLevel = PermissionLevel.ALL;
			return this;
		}

		public FakeTeamPlayer build()
		{
			return new FakeTeamPlayer(this);
		}
	}

	private FakeTeamPlayer(Builder builder)
	{
		this.name = builder.name;
		this.allPermissions = builder.allPermissions;
		this.isOnSameTeam = builder.isOnSameTeam;
		this.location = builder.location;
		this.permissionLevel = builder.permissionLevel;
	}

	@Override
	public ITeam getTeam()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasTeam()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOnSameTeam(ITeamEntity entity)
	{
		return isOnSameTeam;
	}

	@Override
	public boolean isOnline()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isVulnerable()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ITeamPlayer> getTeammates()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TeamPlayer> getOnlineTeammates()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OfflineTeamPlayer> getOfflineTeammates()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInfoFor(ITeamEntity entity)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendMessage(String message)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public Location getLocation()
	{
		return location;
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
	public List<Entity> getNearbyEntities(int radius)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getHealthLevel()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getLastPlayed()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public boolean hasPermission(IPermissible permissible)
	{
		if (permissionLevel == PermissionLevel.DELEGATE)
			return PermissionUtil.hasPermission(this, permissible);
		if (permissionLevel == PermissionLevel.SERVER_ADMIN && permissible.getPermissionNode().startsWith("xteam.core.serveradmin."))
			return true;
		if (permissionLevel == PermissionLevel.TEAM_LEADER && permissible.getPermissionNode().startsWith("xteam.core.leader."))
			return true;
		if (permissionLevel == PermissionLevel.TEAM_ADMIN && permissible.getPermissionNode().startsWith("xteam.core.admin."))
			return true;
		if (permissionLevel == PermissionLevel.TEAM_USER && (permissible.getPermissionNode().startsWith("xteam.core.user.") || permissible.getPermissionNode().equals("help")))
			return true;
		if (permissionLevel == PermissionLevel.ALL)
			return true;
		return false;

	}

	@Override
	public boolean hasPlayedBefore()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAdmin()
	{
		return true;
	}

	@Override
	public boolean isDamaged()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLeader()
	{
		return true;
	}

	@Override
	public boolean isOp()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void sendMessageToTeam(String message)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setLastTeleported(long lastTeleported)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public long getLastTeleported()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setLastAttacked(long lastAttacked)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public long getLastAttacked()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setReturnLocation(Location location)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public Location getReturnLocation()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasReturnLocation()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeReturnLocation()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setLastKnownLocation(Location location)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public Location getLastKnownLocation()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean teleportTo(ILocatable entity)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0, int arg1)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2, int arg3)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<PermissionAttachmentInfo> getEffectivePermissions()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasPermission(String arg0)
	{
		return this.allPermissions;
	}

	@Override
	public boolean hasPermission(Permission arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPermissionSet(String arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPermissionSet(Permission arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void recalculatePermissions()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void removeAttachment(PermissionAttachment arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setOp(boolean arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void sendMessage(String[] arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public int getHungerLevel()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public static FakeTeamPlayer withServerAdminPermissions()
	{
		return new FakeTeamPlayer.Builder().serverAdminPermissions().build();
	}

	public static FakeTeamPlayer withLeaderPermissions()
	{
		return new FakeTeamPlayer.Builder().leaderPermissions().build();
	}

	public static FakeTeamPlayer withAdminPermissions()
	{
		return new FakeTeamPlayer.Builder().adminPermissions().build();
	}

	public static FakeTeamPlayer withUserPermissions()
	{
		return new FakeTeamPlayer.Builder().userPermissions().build();
	}
}
