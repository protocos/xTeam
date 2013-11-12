package me.protocos.xteam.api.fakeobjects;

import java.io.File;
import java.util.Set;
import me.protocos.xteam.xTeam;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.*;

public class FakePluginManager implements PluginManager
{
	private Plugin[] plugins;

	public FakePluginManager()
	{
		plugins = new Plugin[] { xTeam.getInstance() };
	}

	@Override
	public void addPermission(Permission arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void callEvent(Event arg0) throws IllegalStateException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void clearPlugins()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void disablePlugin(Plugin arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void disablePlugins()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void enablePlugin(Plugin arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public Set<Permissible> getDefaultPermSubscriptions(boolean arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Permission> getDefaultPermissions(boolean arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Permission getPermission(String arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Permissible> getPermissionSubscriptions(String arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Permission> getPermissions()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Plugin getPlugin(String name)
	{
		if ("xTeam".equals(name))
		{
			return xTeam.getInstance();
		}
		return null;
	}

	@Override
	public Plugin[] getPlugins()
	{
		return plugins;
	}

	@Override
	public boolean isPluginEnabled(String arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPluginEnabled(Plugin arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Plugin loadPlugin(File arg0) throws InvalidPluginException, InvalidDescriptionException, UnknownDependencyException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Plugin[] loadPlugins(File arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void recalculatePermissionDefaults(Permission arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void registerEvent(Class<? extends Event> arg0, Listener arg1, EventPriority arg2, EventExecutor arg3, Plugin arg4)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void registerEvent(Class<? extends Event> arg0, Listener arg1, EventPriority arg2, EventExecutor arg3, Plugin arg4, boolean arg5)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void registerEvents(Listener arg0, Plugin arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void registerInterface(Class<? extends PluginLoader> arg0) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void removePermission(Permission arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void removePermission(String arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void subscribeToDefaultPerms(boolean arg0, Permissible arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void subscribeToPermission(String arg0, Permissible arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void unsubscribeFromDefaultPerms(boolean arg0, Permissible arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void unsubscribeFromPermission(String arg0, Permissible arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean useTimings()
	{
		// TODO Auto-generated method stub
		return false;
	}

}
