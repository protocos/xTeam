package me.protocos.xteam;

import me.protocos.xteam.command.ICommandManager;
import me.protocos.xteam.command.console.*;
import me.protocos.xteam.command.serveradmin.*;
import me.protocos.xteam.command.teamadmin.TeamAdminInvite;
import me.protocos.xteam.command.teamadmin.TeamAdminPromote;
import me.protocos.xteam.command.teamadmin.TeamAdminSetHeadquarters;
import me.protocos.xteam.command.teamleader.*;
import me.protocos.xteam.command.teamuser.*;
import me.protocos.xteam.data.DataStorageFactory;
import me.protocos.xteam.data.IPersistenceLayer;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.listener.TeamChatListener;
import me.protocos.xteam.listener.TeamFriendlyFireListener;
import me.protocos.xteam.listener.TeamPlayerListener;
import me.protocos.xteam.model.XTeamWebPage;
import me.protocos.xteam.util.SystemUtil;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitTask;

public final class XTeam extends TeamPlugin
{
	private Configuration configuration;
	private IPersistenceLayer persistenceLayer;

	public XTeam()
	{
		super(Bukkit.getServer(), "plugins/xTeam/");
	}

	public XTeam(Server server, String folder)
	{
		super(server, folder);
	}

	private void initFileSystem()
	{
		SystemUtil.ensureFolder(this.getFolder());
		this.configuration = new Configuration(this, SystemUtil.ensureFile(this.getFolder() + "xTeam.cfg"));
		this.configuration.load();
		this.configuration.write();
	}

	@Override
	public void load()
	{
		XTeamWebPage page = new XTeamWebPage("http://dev.bukkit.org/bukkit-plugins/xteam/files/", this.getLog());
		if (page.isDownloadSuccessful() && !("v" + this.getVersion()).equals(page.getMostRecentVersion()))
		{
			this.getLog().info("There is a newer version of xTeam available at the following link:");
			this.getLog().info("	http://dev.bukkit.org/bukkit-plugins/xteam/");
		}
		this.getCommandManager().register(this);
		this.initFileSystem();
		persistenceLayer = new DataStorageFactory(this).dataManagerFromString(Configuration.STORAGE_TYPE);
	}

	@Override
	public void read()
	{
		persistenceLayer.read();
	}

	@Override
	public void write()
	{
		persistenceLayer.write();
	}

	@Override
	public void enable()
	{
		PluginManager pm = this.getBukkitUtil().getPluginManager();
		pm.registerEvents(new TeamFriendlyFireListener(this), this);
		pm.registerEvents(new TeamPlayerListener(this), this);
		pm.registerEvents(new TeamChatListener(this), this);
		this.getCommand("team").setExecutor(this.getCommandExecutor());
	}

	@Override
	public void disable()
	{
		this.initFileSystem();
		persistenceLayer = new DataStorageFactory(this).dataManagerFromString(Configuration.STORAGE_TYPE);
		persistenceLayer.write();
		DataStorageFactory.close();
		//does the same thing as this.bukkitScheduler.cancelAllTasks();
		for (BukkitTask task : this.getBukkitScheduler().getPendingTasks())
		{
			this.getLog().info("Cancelling task id: " + task.getTaskId());
			task.cancel();
		}
	}

	@Override
	public void registerCommands(ICommandManager manager)
	{
		this.registerConsoleCommands(manager);
		this.registerServerAdminCommands(manager);
		this.registerUserCommands(manager);
		this.registerAdminCommands(manager);
		this.registerLeaderCommands(manager);
	}

	public void registerConsoleCommands(ICommandManager manager)
	{
		manager.registerCommand(new ConsoleHelp(this));
		manager.registerCommand(new ConsoleDebug(this));
		manager.registerCommand(new ConsoleDemote(this));
		manager.registerCommand(new ConsoleDisband(this));
		manager.registerCommand(new ConsoleInfo(this));
		manager.registerCommand(new ConsoleList(this));
		manager.registerCommand(new ConsolePromote(this));
		manager.registerCommand(new ConsoleRemove(this));
		manager.registerCommand(new ConsoleRename(this));
		manager.registerCommand(new ConsoleTag(this));
		manager.registerCommand(new ConsoleOpen(this));
		manager.registerCommand(new ConsoleSet(this));
		manager.registerCommand(new ConsoleSetHeadquarters(this));
		manager.registerCommand(new ConsoleSetLeader(this));
		manager.registerCommand(new ConsoleSetRally(this));
		manager.registerCommand(new ConsoleTeleAllHQ(this));
	}

	public void registerServerAdminCommands(ICommandManager manager)
	{
		manager.registerCommand(new ServerAdminChatSpy(this));
		manager.registerCommand(new ServerAdminDebug(this));
		manager.registerCommand(new ServerAdminDisband(this));
		manager.registerCommand(new ServerAdminDemote(this));
		manager.registerCommand(new ServerAdminHeadquarters(this));
		manager.registerCommand(new ServerAdminPromote(this));
		manager.registerCommand(new ServerAdminRemove(this));
		manager.registerCommand(new ServerAdminRename(this));
		manager.registerCommand(new ServerAdminTag(this));
		manager.registerCommand(new ServerAdminOpen(this));
		manager.registerCommand(new ServerAdminSet(this));
		manager.registerCommand(new ServerAdminSetHeadquarters(this));
		manager.registerCommand(new ServerAdminSetLeader(this));
		manager.registerCommand(new ServerAdminSetRally(this));
		manager.registerCommand(new ServerAdminTeleAllHQ(this));
		manager.registerCommand(new ServerAdminTpAll(this));
	}

	public void registerLeaderCommands(ICommandManager manager)
	{
		manager.registerCommand(new TeamLeaderDemote(this));
		manager.registerCommand(new TeamLeaderDisband(this));
		manager.registerCommand(new TeamLeaderOpen(this));
		manager.registerCommand(new TeamLeaderRemove(this));
		manager.registerCommand(new TeamLeaderRename(this));
		manager.registerCommand(new TeamLeaderTag(this));
		manager.registerCommand(new TeamLeaderSetLeader(this));
		manager.registerCommand(new TeamLeaderSetRally(this));
	}

	public void registerAdminCommands(ICommandManager manager)
	{
		manager.registerCommand(new TeamAdminSetHeadquarters(this));
		manager.registerCommand(new TeamAdminInvite(this));
		manager.registerCommand(new TeamAdminPromote(this));
	}

	public void registerUserCommands(ICommandManager manager)
	{
		manager.registerCommand(new TeamUserMainHelp(this));
		manager.registerCommand(new TeamUserHelp(this));
		manager.registerCommand(new TeamUserInfo(this));
		manager.registerCommand(new TeamUserList(this));
		manager.registerCommand(new TeamUserCreate(this));
		manager.registerCommand(new TeamUserJoin(this));
		manager.registerCommand(new TeamUserLeave(this));
		manager.registerCommand(new TeamUserAccept(this));
		manager.registerCommand(new TeamUserHeadquarters(this));
		manager.registerCommand(new TeamUserTeleport(this));
		manager.registerCommand(new TeamUserReturn(this));
		manager.registerCommand(new TeamUserRally(this));
		manager.registerCommand(new TeamUserChat(this));
		manager.registerCommand(new TeamUserMessage(this));
	}
}
