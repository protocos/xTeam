package me.protocos.xteam.api;

import java.util.List;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ICommandContainer;
import me.protocos.xteam.api.command.ICommandManager;
import me.protocos.xteam.api.util.ILog;
import me.protocos.xteam.core.Log;
import me.protocos.xteam.core.PlayerManager;
import me.protocos.xteam.core.TeamManager;
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

	public abstract String getPluginName();

	public abstract String getVersion();

	public abstract List<Permission> getPermissions();

	public ILog getLog()
	{
		return logger;
	}

	@Override
	public void onLoad()
	{
		this.logger = new Log(this.getFolder(), this);
		this.getCommandManager().register(this);
	}

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