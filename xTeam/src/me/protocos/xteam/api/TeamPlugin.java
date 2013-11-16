package me.protocos.xteam.api;

import java.util.List;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ICommandManager;
import me.protocos.xteam.api.util.ILog;
import me.protocos.xteam.core.PlayerManager;
import me.protocos.xteam.core.TeamManager;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class TeamPlugin extends JavaPlugin
{
	protected xTeam xteam;

	public TeamPlugin()
	{
		super();
		xteam = xTeam.getInstance();
	}

	public abstract String getFolder();

	public abstract String getPluginName();

	public abstract String getVersion();

	public abstract List<Permission> getPermissions();

	public abstract ILog getLog();

	public abstract void onLoad();

	public abstract void onEnable();

	public abstract void onDisable();

	public ICommandManager getCommandManager()
	{
		return xteam.getCommandManager();
	}

	public TeamManager getTeamManager()
	{
		return xteam.getTeamManager();
	}

	public PlayerManager getPlayerManager()
	{
		return xteam.getPlayerManager();
	}
}