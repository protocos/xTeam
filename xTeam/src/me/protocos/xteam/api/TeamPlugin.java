package me.protocos.xteam.api;

import java.util.List;
import me.protocos.xteam.api.command.ICommandManager;
import me.protocos.xteam.api.util.ILog;
import me.protocos.xteam.core.PlayerManager;
import me.protocos.xteam.core.TeamManager;
import me.protocos.xteam.util.ConfigLoader;
import org.bukkit.command.CommandExecutor;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class TeamPlugin extends JavaPlugin
{
	public TeamPlugin()
	{
		super();
	}

	public abstract ILog getLog();

	public abstract String getVersion();

	public abstract String getPluginName();

	public abstract ICommandManager getCommandManager();

	public abstract CommandExecutor getCommandExecutor();

	public abstract TeamManager getTeamManager();

	public abstract PlayerManager getPlayerManager();

	public abstract ConfigLoader getConfigLoader();

	public abstract void initFileSystem(String path);

	public abstract List<Permission> getPermissions();

	public abstract void registerConsoleCommands(ICommandManager manager);

	public abstract void registerServerAdminCommands(ICommandManager manager);

	public abstract void registerLeaderCommands(ICommandManager manager);

	public abstract void registerAdminCommands(ICommandManager manager);

	public abstract void registerUserCommands(ICommandManager manager);
}