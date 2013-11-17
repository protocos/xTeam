package me.protocos.xteam;

import java.util.List;
import me.protocos.xteam.api.TeamPlugin;
import me.protocos.xteam.api.command.ICommandManager;
import me.protocos.xteam.api.util.ILog;
import me.protocos.xteam.command.console.*;
import me.protocos.xteam.command.serveradmin.*;
import me.protocos.xteam.command.teamadmin.UserInvite;
import me.protocos.xteam.command.teamadmin.UserPromote;
import me.protocos.xteam.command.teamadmin.UserSetHeadquarters;
import me.protocos.xteam.command.teamleader.*;
import me.protocos.xteam.command.teamuser.*;
import me.protocos.xteam.core.Log;
import org.bukkit.permissions.Permission;

public class xTeamPlugin extends TeamPlugin
{
	private ILog logger;

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
	public void onLoad()
	{
		logger = new Log(this.getFolder(), this);
		xteam.load(this);
	}

	@Override
	public void onEnable()
	{
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
		manager.registerCommand("console_debug", new ConsoleDebug());
		manager.registerCommand("console_demote", new ConsoleDemote());
		manager.registerCommand("console_disband", new ConsoleDisband());
		manager.registerCommand("console_help", new ConsoleHelp());
		manager.registerCommand("console_info", new ConsoleInfo());
		manager.registerCommand("console_list", new ConsoleList());
		manager.registerCommand("console_promote", new ConsolePromote());
		manager.registerCommand("console_remove", new ConsoleRemove());
		manager.registerCommand("console_rename", new ConsoleRename());
		manager.registerCommand("console_tag", new ConsoleTag());
		manager.registerCommand("console_open", new ConsoleOpen());
		manager.registerCommand("console_set", new ConsoleSet());
		manager.registerCommand("console_setleader", new ConsoleSetLeader());
		manager.registerCommand("console_teleallhq", new ConsoleTeleAllHQ());
	}

	@Override
	public void registerServerAdminCommands(ICommandManager manager)
	{
		manager.registerCommand("serveradmin_chatspy", new AdminChatSpy());
		manager.registerCommand("serveradmin_disband", new AdminDisband());
		manager.registerCommand("serveradmin_demote", new AdminDemote());
		manager.registerCommand("serveradmin_admin", new AdminHelp());
		manager.registerCommand("serveradmin_hq", new AdminHeadquarters());
		manager.registerCommand("serveradmin_promote", new AdminPromote());
		manager.registerCommand("serveradmin_remove", new AdminRemove());
		manager.registerCommand("serveradmin_rename", new AdminRename());
		manager.registerCommand("serveradmin_tag", new AdminTag());
		manager.registerCommand("serveradmin_open", new AdminOpen());
		manager.registerCommand("serveradmin_set", new AdminSet());
		manager.registerCommand("serveradmin_sethq", new AdminSetHeadquarters());
		manager.registerCommand("serveradmin_setleader", new AdminSetLeader());
		manager.registerCommand("serveradmin_teleallhq", new AdminTeleAllHQ());
		manager.registerCommand("serveradmin_tpall", new AdminTpAll());
	}

	@Override
	public void registerLeaderCommands(ICommandManager manager)
	{
		manager.registerCommand("leader_demote", new UserDemote());
		manager.registerCommand("leader_disband", new UserDisband());
		manager.registerCommand("leader_open", new UserOpen());
		manager.registerCommand("leader_remove", new UserRemove());
		manager.registerCommand("leader_rename", new UserRename());
		manager.registerCommand("leader_tag", new UserTag());
		manager.registerCommand("leader_setleader", new UserSetLeader());
		manager.registerCommand("leader_setrally", new UserSetRally());
	}

	@Override
	public void registerAdminCommands(ICommandManager manager)
	{
		manager.registerCommand("admin_sethq", new UserSetHeadquarters());
		manager.registerCommand("admin_invite", new UserInvite());
		manager.registerCommand("admin_promote", new UserPromote());
	}

	@Override
	public void registerUserCommands(ICommandManager manager)
	{
		manager.registerCommand("user_mainhelp", new UserMainHelp());
		manager.registerCommand("user_help", new UserHelp());
		manager.registerCommand("user_info", new UserInfo());
		manager.registerCommand("user_list", new UserList());
		manager.registerCommand("user_create", new UserCreate());
		manager.registerCommand("user_join", new UserJoin());
		manager.registerCommand("user_leave", new UserLeave());
		manager.registerCommand("user_accept", new UserAccept());
		manager.registerCommand("user_hq", new UserHeadquarters());
		manager.registerCommand("user_tele", new UserTeleport());
		manager.registerCommand("user_return", new UserReturn());
		manager.registerCommand("user_rally", new UserRally());
		manager.registerCommand("user_chat", new UserChat());
		manager.registerCommand("user_message", new UserMessage());
	}
}
