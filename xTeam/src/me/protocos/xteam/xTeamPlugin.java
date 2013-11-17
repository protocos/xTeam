package me.protocos.xteam;

import java.util.List;
import me.protocos.xteam.api.TeamPlugin;
import me.protocos.xteam.api.command.ICommandManager;
import me.protocos.xteam.api.util.ILog;
import me.protocos.xteam.command.console.*;
import me.protocos.xteam.command.serveradmin.*;
import me.protocos.xteam.command.teamadmin.TeamAdminInvite;
import me.protocos.xteam.command.teamadmin.TeamAdminPromote;
import me.protocos.xteam.command.teamadmin.TeamAdminSetHeadquarters;
import me.protocos.xteam.command.teamleader.*;
import me.protocos.xteam.command.teamuser.*;
import org.bukkit.permissions.Permission;

public class xTeamPlugin extends TeamPlugin
{
	@Override
	public String getFolder()
	{
		return this.getDataFolder().getAbsolutePath();
	}

	@Override
	public String getPluginName()
	{
		return this.getName();
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
	public ILog getLog()
	{
		return logger;
	}

	@Override
	public void onEnable()
	{
		xteam.load(this);
		xteam.enable(this);
	}

	@Override
	public void onDisable()
	{
		xteam.disable(this);
	}

	@Override
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

	@Override
	public void registerServerAdminCommands(ICommandManager manager)
	{
		manager.registerCommand(new ServerAdminChatSpy());
		manager.registerCommand(new ServerAdminDisband());
		manager.registerCommand(new ServerAdminDemote());
		manager.registerCommand(new ServerAdminHelp());
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

	@Override
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

	@Override
	public void registerAdminCommands(ICommandManager manager)
	{
		manager.registerCommand(new TeamAdminSetHeadquarters());
		manager.registerCommand(new TeamAdminInvite());
		manager.registerCommand(new TeamAdminPromote());
	}

	@Override
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
