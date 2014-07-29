package me.protocos.xteam;

import java.io.File;
import java.util.List;
import me.protocos.api.util.CommonUtil;
import me.protocos.xteam.command.CommandDelegate;
import me.protocos.xteam.command.CommandManager;
import me.protocos.xteam.command.ICommandContainer;
import me.protocos.xteam.command.ICommandManager;
import me.protocos.xteam.core.*;
import me.protocos.xteam.event.EventDispatcher;
import me.protocos.xteam.event.IEventDispatcher;
import me.protocos.xteam.fakeobjects.FakeXTeam;
import me.protocos.xteam.model.ILog;
import me.protocos.xteam.model.Log;
import me.protocos.xteam.util.BukkitUtil;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public abstract class TeamPlugin extends JavaPlugin implements ICommandContainer
{
	private String folder;
	private ILog log;
	private BukkitScheduler bukkitScheduler;
	private IEventDispatcher eventDispatcher;
	private ICommandManager commandManager;
	private CommandExecutor commandExecutor;
	private BukkitUtil bukkitUtil;
	private ITeamCoordinator teamCoordinator;
	private IPlayerFactory playerFactory;
	private TeleportScheduler teleportScheduler;
	private InviteHandler inviteHandler;

	public TeamPlugin(Server server, String folder)
	{
		super();
		this.folder = folder;
		this.log = new Log(this, this.folder + "xTeam.log");
		this.bukkitUtil = new BukkitUtil(server);
		if (this.isMainPlugin())
			initialize(server);
	}

	private void initialize(Server server)
	{
		this.bukkitScheduler = new BukkitUtil(server).getScheduler();
		this.eventDispatcher = new EventDispatcher();
		this.commandManager = new CommandManager();
		this.commandExecutor = new CommandDelegate(this, commandManager);
		this.inviteHandler = new InviteHandler(this);
		this.teamCoordinator = new TeamCoordinator(this);
		this.playerFactory = new PlayerFactory(this);
		this.teleportScheduler = new TeleportScheduler(this, playerFactory, bukkitScheduler);
	}

	private boolean isMainPlugin()
	{
		return this.getPluginName().equals(XTeam.class.getSimpleName()) || this.getPluginName().equals(FakeXTeam.class.getSimpleName());
	}

	public final String getPluginName()
	{
		return this.getClass().getSimpleName();
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

	public ILog getLog()
	{
		return this.log;
	}

	public BukkitUtil getBukkitUtil()
	{
		return this.bukkitUtil;
	}

	public final IEventDispatcher getEventDispatcher()
	{
		if (this.isMainPlugin())
			return this.eventDispatcher;
		return this.getMainPlugin().eventDispatcher;
	}

	public final TeleportScheduler getTeleportScheduler()
	{
		if (this.isMainPlugin())
			return this.teleportScheduler;
		return this.getMainPlugin().teleportScheduler;
	}

	public final BukkitScheduler getBukkitScheduler()
	{
		if (this.isMainPlugin())
			return this.bukkitScheduler;
		return this.getMainPlugin().bukkitScheduler;
	}

	public final ICommandManager getCommandManager()
	{
		if (this.isMainPlugin())
			return this.commandManager;
		return this.getMainPlugin().commandManager;
	}

	public final CommandExecutor getCommandExecutor()
	{
		if (this.isMainPlugin())
			return this.commandExecutor;
		return this.getMainPlugin().commandExecutor;
	}

	public final ITeamCoordinator getTeamCoordinator()
	{
		if (this.isMainPlugin())
			return this.teamCoordinator;
		return this.getMainPlugin().teamCoordinator;
	}

	public final IPlayerFactory getPlayerFactory()
	{
		if (this.isMainPlugin())
			return this.playerFactory;
		return this.getMainPlugin().playerFactory;
	}

	public final InviteHandler getInviteHandler()
	{
		if (this.isMainPlugin())
			return this.inviteHandler;
		return this.getMainPlugin().inviteHandler;
	}

	public abstract void load();

	public abstract void enable();

	public abstract void disable();

	@Override
	public final void onLoad()
	{
		try
		{
			this.load();
			this.getCommandManager().register(this);
			this.getLog().debug("" + this.getPluginName() + " v" + this.getVersion() + " loaded");
		}
		catch (Exception e)
		{
			this.getLog().exception(e);
		}
	}

	@Override
	public final void onEnable()
	{
		try
		{
			this.enable();
			this.read();
			this.getLog().debug(this.getPluginName() + " v" + this.getVersion() + " enabled");
		}
		catch (Exception e)
		{
			this.getLog().exception(e);
		}
	}

	@Override
	public final void onDisable()
	{
		try
		{
			this.write();
			this.disable();
			this.getLog().debug(this.getPluginName() + " v" + this.getVersion() + " disabled");
			this.getLog().close();
		}
		catch (Exception e)
		{
			this.getLog().exception(e);
		}
	}

	public abstract void read();

	public abstract void write();

	public TeamPlugin getMainPlugin()
	{
		Plugin mainPlugin = this.getBukkitUtil().getPlugin("xTeam");
		if (mainPlugin instanceof TeamPlugin)
			return CommonUtil.assignFromType(mainPlugin, TeamPlugin.class);
		return null;
	}
}
