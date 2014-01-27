package me.protocos.xteam;

import java.io.File;
import java.util.List;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.ICommandContainer;
import me.protocos.xteam.command.ICommandManager;
import me.protocos.xteam.core.IPlayerManager;
import me.protocos.xteam.core.ITeamManager;
import me.protocos.xteam.model.ILog;
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

	public String getFolder()
	{
		return this.getDataFolder().getAbsolutePath() + File.separator;
	}

	public String getVersion()
	{
		return this.getDescription().getVersion();
	}

	public List<Permission> getPermissions()
	{
		return this.getDescription().getPermissions();
	}

	public ILog getLog()
	{
		return xteam.getLog();
	}

	public final String getPluginName()
	{
		return this.getClass().getSimpleName();
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
		this.getLog().debug("[" + this.getPluginName() + "] v" + this.getVersion() + " loaded");
	}
}
