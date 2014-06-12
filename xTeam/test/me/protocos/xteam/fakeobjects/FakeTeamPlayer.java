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
	private boolean allPermissions;

	public FakeTeamPlayer(boolean allPermissions)
	{
		this.allPermissions = allPermissions;
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
		// TODO Auto-generated method stub
		return false;
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
	public List<Entity> getNearbyEntities(int radius)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getHealth()
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasPermission(IPermissible permissible)
	{
		return PermissionUtil.hasPermission(this, permissible);
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
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		return false;
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
}
