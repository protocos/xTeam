package me.protocos.xteam;

import java.io.File;
import java.util.List;
import me.protocos.xteam.api.TeamPlugin;
import me.protocos.xteam.api.command.ICommandManager;
import me.protocos.xteam.command.CommandDelegate;
import me.protocos.xteam.command.console.*;
import me.protocos.xteam.command.serveradmin.*;
import me.protocos.xteam.command.teamadmin.TeamAdminInvite;
import me.protocos.xteam.command.teamadmin.TeamAdminPromote;
import me.protocos.xteam.command.teamadmin.TeamAdminSetHeadquarters;
import me.protocos.xteam.command.teamleader.*;
import me.protocos.xteam.command.teamuser.*;
import me.protocos.xteam.listener.TeamChatListener;
import me.protocos.xteam.listener.TeamPlayerListener;
import me.protocos.xteam.listener.TeamPvPEntityListener;
import me.protocos.xteam.listener.TeamScoreListener;
import me.protocos.xteam.util.BukkitUtil;
import org.bukkit.command.CommandExecutor;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;

public class xTeamPlugin extends TeamPlugin
{
	private CommandExecutor commandExecutor;

	@Override
	public String getFolder()
	{
		return this.getDataFolder().getAbsolutePath();
	}

	@Override
	public String getVersion()
	{
		return this.getDescription().getVersion();
	}

	@Override
	public List<Permission> getPermissions()
	{
		return this.getDescription().getPermissions();
	}

	@Override
	public void onEnable()
	{
		this.enable();
	}

	@Override
	public void onDisable()
	{
		this.disable();
	}

	private void enable()
	{
		try
		{
			PluginManager pm = BukkitUtil.getPluginManager();
			pm.registerEvents(new TeamPvPEntityListener(), this);
			pm.registerEvents(new TeamPlayerListener(), this);
			pm.registerEvents(new TeamScoreListener(), this);
			pm.registerEvents(new TeamChatListener(), this);
			this.commandExecutor = new CommandDelegate(this.getCommandManager());
			xteam.readTeamData(new File(this.getDataFolder().getAbsolutePath() + "/teams.txt"));
			this.getCommand("team").setExecutor(commandExecutor);
			this.getLog().info("[" + this.getPluginName() + "] v" + this.getVersion() + " enabled");
		}
		catch (Exception e)
		{
			this.getLog().exception(e);
		}
	}

	private void disable()
	{
		try
		{
			xteam.writeTeamData(new File(this.getDataFolder().getAbsolutePath() + "/teams.txt"));
			this.getLog().info("[" + this.getPluginName() + "] v" + this.getVersion() + " disabled");
			this.getLog().close();
		}
		catch (Exception e)
		{
			this.getLog().exception(e);
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
		manager.registerCommand(new ConsoleDebug());
		manager.registerCommand(new ConsoleDemote());
		manager.registerCommand(new ConsoleDisband());
		manager.registerCommand(new ConsoleHelp());
		manager.registerCommand(new ConsoleInfo());
		manager.registerCommand(new ConsoleList());
		manager.registerCommand(new ConsolePromote());
		manager.registerCommand(new ConsoleRemove());
		manager.registerCommand(new ConsoleRename());
		manager.registerCommand(new ConsoleTag());
		manager.registerCommand(new ConsoleOpen());
		manager.registerCommand(new ConsoleSet());
		manager.registerCommand(new ConsoleSetLeader());
		manager.registerCommand(new ConsoleTeleAllHQ());
	}

	public void registerServerAdminCommands(ICommandManager manager)
	{
		manager.registerCommand(new ServerAdminChatSpy());
		manager.registerCommand(new ServerAdminDisband());
		manager.registerCommand(new ServerAdminDemote());
		manager.registerCommand(new ServerAdminHeadquarters());
		manager.registerCommand(new ServerAdminPromote());
		manager.registerCommand(new ServerAdminRemove());
		manager.registerCommand(new ServerAdminRename());
		manager.registerCommand(new ServerAdminTag());
		manager.registerCommand(new ServerAdminOpen());
		manager.registerCommand(new ServerAdminSet());
		manager.registerCommand(new ServerAdminSetHeadquarters());
		manager.registerCommand(new ServerAdminSetLeader());
		manager.registerCommand(new ServerAdminTeleAllHQ());
		manager.registerCommand(new ServerAdminTpAll());
	}

	public void registerLeaderCommands(ICommandManager manager)
	{
		manager.registerCommand(new TeamLeaderDemote());
		manager.registerCommand(new TeamLeaderDisband());
		manager.registerCommand(new TeamLeaderOpen());
		manager.registerCommand(new TeamLeaderRemove());
		manager.registerCommand(new TeamLeaderRename());
		manager.registerCommand(new TeamLeaderTag());
		manager.registerCommand(new TeamLeaderSetLeader());
		manager.registerCommand(new TeamLeaderSetRally());
	}

	public void registerAdminCommands(ICommandManager manager)
	{
		manager.registerCommand(new TeamAdminSetHeadquarters());
		manager.registerCommand(new TeamAdminInvite());
		manager.registerCommand(new TeamAdminPromote());
	}

	public void registerUserCommands(ICommandManager manager)
	{
		manager.registerCommand(new TeamUserMainHelp());
		manager.registerCommand(new TeamUserHelp());
		manager.registerCommand(new TeamUserInfo());
		manager.registerCommand(new TeamUserList());
		manager.registerCommand(new TeamUserCreate());
		manager.registerCommand(new TeamUserJoin());
		manager.registerCommand(new TeamUserLeave());
		manager.registerCommand(new TeamUserAccept());
		manager.registerCommand(new TeamUserHeadquarters());
		manager.registerCommand(new TeamUserTeleport());
		manager.registerCommand(new TeamUserReturn());
		manager.registerCommand(new TeamUserRally());
		manager.registerCommand(new TeamUserChat());
		manager.registerCommand(new TeamUserMessage());
	}
}
