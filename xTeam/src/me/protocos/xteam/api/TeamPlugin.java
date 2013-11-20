package me.protocos.xteam.api;

import java.util.List;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ICommandContainer;
import me.protocos.xteam.api.command.ICommandManager;
import me.protocos.xteam.api.core.IPlayerManager;
import me.protocos.xteam.api.core.ITeamManager;
import me.protocos.xteam.api.util.ILog;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class TeamPlugin extends JavaPlugin implements ICommandContainer
{
	protected xTeam xteam;
	protected ILog logger;

	public TeamPlugin()
	{
		super();
		xteam = xTeam.getInstance();
	}

	public abstract String getFolder();

	public abstract String getVersion();

	public abstract List<Permission> getPermissions();

	public final String getPluginName()
	{
		return this.getClass().getSimpleName();
	}

	public ILog getLog()
	{
		return xteam.getLog();
	}

	public final ICommandManager getCommandManager()
	{
		return xteam.getCommandManager();
	}

	public final ITeamManager getTeamManager()
	{
		return xteam.getTeamManager();
	}

	public final IPlayerManager getPlayerManager()
	{
		return xteam.getPlayerManager();
	}

	@Override
	public final void onLoad()
	{
		xteam.load(this);
		this.getLog().info("[" + this.getPluginName() + "] v" + this.getVersion() + " loaded");
	}

	public abstract void onEnable();

	public abstract void onDisable();
}