package me.protocos.xteam.testing;

import java.util.Set;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

public class FakeCommandSender implements CommandSender
{
	String name;
	boolean isOp;

	public FakeCommandSender()
	{
		this("sender");
	}
	public FakeCommandSender(String name)
	{
		this(name, false);
	}
	public FakeCommandSender(String name, boolean isOp)
	{
		this.name = name;
		this.isOp = isOp;
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
	public String getName()
	{
		return name;
	}

	@Override
	public Server getServer()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasPermission(Permission arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasPermission(String arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOp()
	{
		return isOp;
	}

	@Override
	public boolean isPermissionSet(Permission arg0)
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
	public void sendMessage(String arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void sendMessage(String[] arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setOp(boolean isOp)
	{
		this.isOp = isOp;
	}

}
