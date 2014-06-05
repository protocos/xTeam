package me.protocos.xteam;

import java.io.File;
import java.util.List;
import me.protocos.xteam.command.CommandDelegate;
import me.protocos.xteam.command.CommandManager;
import me.protocos.xteam.command.ICommandContainer;
import me.protocos.xteam.command.ICommandManager;
import me.protocos.xteam.command.action.InviteHandler;
import me.protocos.xteam.command.action.TeleportScheduler;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.core.ITeamManager;
import me.protocos.xteam.core.PlayerFactory;
import me.protocos.xteam.core.TeamManager;
import me.protocos.xteam.event.EventDispatcher;
import me.protocos.xteam.event.IEventDispatcher;
import me.protocos.xteam.model.ILog;
import me.protocos.xteam.model.Log;
import me.protocos.xteam.util.BukkitUtil;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public abstract class TeamPlugin extends JavaPlugin implements ICommandContainer
{
	protected final String folder;
	protected final CommandExecutor commandExecutor;
	protected final BukkitScheduler bukkitScheduler;
	protected final IEventDispatcher eventDispatcher;
	protected final TeleportScheduler teleportScheduler;
	protected final InviteHandler inviteHandler;
	protected final BukkitUtil bukkitUtil;
	protected final ITeamManager teamManager;
	protected final IPlayerFactory playerFactory;
	protected final ICommandManager commandManager;
	protected final ILog log;

	public TeamPlugin(Server server, String folder)
	{
		super();
		this.folder = folder;
		this.log = new Log(this, folder + "xTeam.log");
		this.bukkitScheduler = new BukkitUtil(server).getScheduler();
		this.eventDispatcher = new EventDispatcher();
		this.commandManager = new CommandManager();
		this.commandExecutor = new CommandDelegate(this, this.getCommandManager());
		this.bukkitUtil = new BukkitUtil(server);
		this.teamManager = new TeamManager(this);
		this.playerFactory = new PlayerFactory(this);
		this.teleportScheduler = new TeleportScheduler(this, playerFactory, bukkitScheduler);
		this.inviteHandler = new InviteHandler(this);
	}

	public String getFolder()
	{
		return folder + File.separator;
	}

	public String getVersion()
	{
		return this.getDescription().getVersion();
	}

	public List<Permission> getPermissions()
	{
		return this.getDescription().getPermissions();
	}

	public final IEventDispatcher getEventDispatcher()
	{
		return this.eventDispatcher;
	}

	public final TeleportScheduler getTeleportScheduler()
	{
		return this.teleportScheduler;
	}

	public final BukkitUtil getBukkitUtil()
	{
		return this.bukkitUtil;
	}

	public final BukkitScheduler getBukkitScheduler()
	{
		return this.bukkitScheduler;
	}

	public final String getPluginName()
	{
		return this.getClass().getSimpleName();
	}

	public final ICommandManager getCommandManager()
	{
		return this.commandManager;
	}

	public final ITeamManager getTeamManager()
	{
		return this.teamManager;
	}

	public final IPlayerFactory getPlayerManager()
	{
		return this.playerFactory;
	}

	public final InviteHandler getInviteHandler()
	{
		return inviteHandler;
	}

	public ILog getLog()
	{
		return log;
	}

	public void load()
	{
	}

	@Override
	public final void onLoad()
	{
		this.load();
		this.getLog().debug("[" + this.getPluginName() + "] v" + this.getVersion() + " loaded");
	}

	public abstract void readTeamData();

	public abstract void writeTeamData();
}
