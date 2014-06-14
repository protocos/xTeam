package me.protocos.xteam.fakeobjects;

import java.util.Set;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

public class FakeCommandSender implements CommandSender
{
	private String name;
	private boolean isOp;

	public static class Builder
	{
		private String name = "sender";
		private boolean isOp;

		public Builder name(@SuppressWarnings("hiding") String name)
		{
			this.name = name;
			return this;
		}

		public Builder op(@SuppressWarnings("hiding") boolean isOp)
		{
			this.isOp = isOp;
			return this;
		}

		public FakeCommandSender build()
		{
			return new FakeCommandSender(this);
		}
	}

	private FakeCommandSender(Builder builder)
	{
		this.name = builder.name;
		this.isOp = builder.isOp;
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
	public boolean hasPermission(String permission)
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
