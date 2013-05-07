package me.protocos.xteam;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;
import me.protocos.xteam.command.CommandDelegate;
import me.protocos.xteam.command.CommandManager;
import me.protocos.xteam.command.ICommandManager;
import me.protocos.xteam.command.console.*;
import me.protocos.xteam.command.serveradmin.*;
import me.protocos.xteam.command.teamadmin.Invite;
import me.protocos.xteam.command.teamadmin.Promote;
import me.protocos.xteam.command.teamadmin.SetHeadquarters;
import me.protocos.xteam.command.teamleader.*;
import me.protocos.xteam.command.teamuser.*;
import me.protocos.xteam.core.*;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class xTeam extends JavaPlugin
{
	public static final Logger log = Logger.getLogger("Minecraft");
	public static ILog logger;
	public static String VERSION;
	public static TeamServiceManager sm;
	public static TeamManager tm;
	public static ICommandManager cm;
	public static CommandExecutor exec;

	public static void registerConsoleCommands(ICommandManager manager)
	{
		manager.registerCommand("console_demote", new ConsoleDemote());
		manager.registerCommand("console_disband", new ConsoleDisband());
		manager.registerCommand("console_help", new ConsoleHelp());
		manager.registerCommand("console_info", new ConsoleInfo());
		manager.registerCommand("console_list", new ConsoleList());
		manager.registerCommand("console_promote", new ConsolePromote());
		manager.registerCommand("console_reload", new ConsoleReload());
		manager.registerCommand("console_remove", new ConsoleRemove());
		manager.registerCommand("console_rename", new ConsoleRename());
		manager.registerCommand("console_tag", new ConsoleTag());
		manager.registerCommand("console_open", new ConsoleOpen());
		manager.registerCommand("console_set", new ConsoleSet());
		manager.registerCommand("console_setleader", new ConsoleSetLeader());
		manager.registerCommand("console_teleallhq", new ConsoleTeleAllHQ());
	}
	public static void registerServerAdminCommands(ICommandManager manager)
	{
		manager.registerCommand("serveradmin_chatspy", new AdminChatSpy());
		manager.registerCommand("serveradmin_disband", new AdminDisband());
		manager.registerCommand("serveradmin_demote", new AdminDemote());
		manager.registerCommand("serveradmin_admin", new AdminHelp());
		manager.registerCommand("serveradmin_hq", new AdminHeadquarters());
		manager.registerCommand("serveradmin_promote", new AdminPromote());
		manager.registerCommand("serveradmin_reload", new AdminReload());
		manager.registerCommand("serveradmin_remove", new AdminRemove());
		manager.registerCommand("serveradmin_rename", new AdminRename());
		manager.registerCommand("serveradmin_tag", new AdminTag());
		manager.registerCommand("serveradmin_open", new AdminOpen());
		manager.registerCommand("serveradmin_set", new AdminSet());
		manager.registerCommand("serveradmin_sethq", new AdminSetHeadquarters());
		manager.registerCommand("serveradmin_setleader", new AdminSetLeader());
		manager.registerCommand("serveradmin_teleallhq", new AdminTeleAllHQ());
		manager.registerCommand("serveradmin_tpall", new AdminTpAll());
		manager.registerCommand("serveradmin_update", new AdminUpdatePlayers());
	}
	public static void registerAdminCommands(ICommandManager manager)
	{
		manager.registerCommand("admin_invite", new Invite());
		manager.registerCommand("admin_promote", new Promote());
		manager.registerCommand("admin_sethq", new SetHeadquarters());
	}
	public static void registerLeaderCommands(ICommandManager manager)
	{
		manager.registerCommand("leader_demote", new Demote());
		manager.registerCommand("leader_disband", new Disband());
		manager.registerCommand("leader_open", new Open());
		manager.registerCommand("leader_remove", new Remove());
		manager.registerCommand("leader_rename", new Rename());
		manager.registerCommand("leader_setleader", new SetLeader());
		manager.registerCommand("leader_tag", new Tag());
	}
	public static void registerUserCommands(ICommandManager manager)
	{
		manager.registerCommand("user_accept", new Accept());
		manager.registerCommand("user_chat", new Chat());
		manager.registerCommand("user_create", new Create());
		manager.registerCommand("user_help", new Help());
		manager.registerCommand("user_hq", new Headquarters());
		manager.registerCommand("user_info", new Info());
		manager.registerCommand("user_join", new Join());
		manager.registerCommand("user_leave", new Leave());
		manager.registerCommand("user_list", new List());
		manager.registerCommand("user_mainhelp", new MainHelp());
		manager.registerCommand("user_message", new Message());
		manager.registerCommand("user_return", new Return());
		manager.registerCommand("user_tele", new Teleport());
	}
	@Override
	public void onEnable()
	{
		try
		{
			initFileSystem();
			logger = new LogUtil(this);
			VERSION = getDescription().getVersion();
			Data.settings = new File(getDataFolder().getAbsolutePath() + "/xTeam.cfg");
			Data.load();
			log.info("[xTeam] Config loaded.");
			sm = new TeamServiceManager(this);
			tm = new TeamManager();
			cm = new CommandManager();
			registerConsoleCommands(cm);
			registerServerAdminCommands(cm);
			registerAdminCommands(cm);
			registerLeaderCommands(cm);
			registerUserCommands(cm);
			exec = new CommandDelegate(cm);
			getCommand("team").setExecutor(exec);
			Functions.readTeamData(new File(getDataFolder().getAbsolutePath() + "/teams.txt"));
			Data.ensureDefaultTeams();
			//		sm.loadConfig();
			logger.custom("[xTeam] v" + VERSION + " enabled");
		}
		catch (Exception e)
		{
			logger.exception(e);
			e.printStackTrace();
		}
	}
	@Override
	public void onDisable()
	{
		try
		{
			Functions.writeTeamData(new File(getDataFolder().getAbsolutePath() + "/teams.txt"));
			//		sm.saveConfig();
			logger.custom("[xTeam] v" + VERSION + " disabled");
			logger.close();
		}
		catch (Exception e)
		{
			logger.exception(e);
			e.printStackTrace();
		}
	}
	public void initFileSystem()
	{
		File f = new File(getDataFolder().getAbsolutePath());
		if (!f.exists())
		{
			f.mkdir();
		}
		f = new File(getDataFolder().getAbsolutePath() + "/teams.txt");
		if (!f.exists())
		{
			try
			{
				f.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		f = new File(getDataFolder().getAbsolutePath() + "/xTeam.cfg");
		if (!f.exists())
		{
			try
			{
				f.createNewFile();
				BufferedWriter bw = new BufferedWriter(new FileWriter(f));
				bw.write("############################################\n" +
						"# \n" +
						"# \n" +
						"# Bukkit preferences\n" +
						"# \n" +
						"# \n" +
						"### playersonteam - Amount of players that can be on a team. (default=10)\n" +
						"### sethqinterval - Delay in hours between use of /team sethq (default=0)\n" +
						"### teleportradius - Maximum distance in blocks between team mates to teleport to one another (default=500)\n" +
						"### canteamchat - Allows/Disallows the use of team chat function completely (default=true)\n" +
						"### enemyproximity - When teleporting, if enemies are within this radius of blocks, the teleport is delayed (default=16)\n" +
						"### teledelay - Delay in seconds for teleporting when enemies are near (default=10)\n" +
						"### telerefreshdelay - Delay in seconds for when you can use team teleporting. Does not include /team return (default=60)\n" +
						"### createteamdelay - Delay in minutes for creating teams (default=20)\n" +
						"### teamwolves - Protects your wolfies from you and your teammates from damaging them (default=true)\n" +
						"### defaultteams - Default list of teams for the server separated by commas (e.g. defaultteams=red,green,blue,yellow)\n" +
						"### randomjointeam - Player randomly joins one of the default teams on joining (default=false)\n" +
						"### balanceteams - Balance teams when someone randomly joins (default=false)\n" +
						"### onlyjoindefaultteam - When true, players can only join one of the default teams listed above (default=false)\n" +
						"### defaulthqonjoin - When true, players on default teams are teleported to their headqloaduarters on join (default=false)\n" +
						"### lastattackeddelay - How long a teamPlayer has to wait after being attacked to teleport (default=15)\n" +
						"### teamtagenabled - When true, players have their team tag displayed when in chat (default=true)\n" +
						"### teamtagmaxlength - Maximum length of a team tag (default=0 == no maximum tag length)\n" +
						"### disabledworlds - World names, separated by commas, that xTeam is disabled in (e.g. disabledworlds=world,world_nether,world_the_end)\n" +
						"### nopermissions - When true, xTeam will give all regular commands to players and admin commands to OPs (default=false)\n" +
						"### alphanumericnames - When true, players can only create teams with alphanumeric names and no symbols (i.e. TeamAwesome123) (default=false)\n" +
						"### displaycoordinates - When true, players can see coordinates of other team mates in team info (default=true)\n" +
						"### tagcolor - Color representing the color of the tag in game (e.g. green, dark_red, light_purple)\n" +
						"### chatnamecolor - Color representing the color of player names in team chat (e.g. green, dark_red, light_purple)\n" +
						"############################################\n" +
						"playersonteam=10\n" +
						"sethqinterval=0\n" +
						"hqondeath=true\n" +
						"teleportradius=500\n" +
						"canteamchat=true\n" +
						"enemyproximity=16\n" +
						"teledelay=10\n" +
						"telerefreshdelay=60\n" +
						"createteamdelay=20\n" +
						"teamwolves=true\n" +
						"defaultteams=\n" +
						"randomjointeam=false\n" +
						"balanceteams=false\n" +
						"onlyjoindefaultteam=false\n" +
						"defaulthqonjoin=false\n" +
						"teamtagenabled=true\n" +
						"teamtagmaxlength=0\n" +
						"teamfriendlyfire=false\n" +
						"lastattackeddelay=15\n" +
						"disabledworlds=\n" +
						"nopermissions=false\n" +
						"alphanumericnames=false\n" +
						"displaycoordinates=true\n" +
						"tagcolor=green\n" +
						"chatnamecolor=dark_green\n" +
						//				"############################################\n" +
						//				"# \n" +
						//				"# \n" +
						//				"# Locations preferences (should you choose to add xTeamLocations to your server)\n" +
						//				"# \n" +
						//				"# \n" +
						//				"### maxnumlocations - Maximum number of locations that can be stored per team (default=9)\n" +
						//				"############################################\n" +
						//				"maxnumlocations=9\n" +
						"############################################\n" +
						"# \n" +
						"# \n" +
						"# Spout preferences (should you choose to add spout to your server)\n" +
						"# \n" +
						"# \n" +
						"### teamhidename - If enabled players can only see teammates names and players with no team at all (default=true)\n" +
						"### namerevealtime - Amount in seconds that a teamPlayer name is revealed if attacked by another teamPlayer (default=5)\n" +
						"############################################\n" +
						"teamhidename=true\n" +
						"namerevealtime=5\n" +
						"############################################\n" +
						"# \n" +
						"# \n" +
						"# Permission Nodes\n" +
						"# \n" +
						"# \n" +
						"### xteam.player.core.accept - (Allows players to accept an invitation)\n" +
						"### xteam.player.core.chat - (Allows players to chat/msg their team)\n" +
						"### xteam.player.core.create - (Allows players to create a team)\n" +
						"### xteam.player.core.hq - (Allows players to teleport to headquarters)\n" +
						"### xteam.player.core.join - (Allows players to join a team)\n" +
						"### xteam.player.core.leave - (Allows players to leave a team)\n" +
						"### xteam.player.core.list - (Allows players to list all team names)\n" +
						"### xteam.player.core.return - (Allows players to return to teleport location)\n" +
						"### xteam.player.core.tele - (Allows players to teleport to teammates)\n" +
						"### xteam.admin.core.invite - (Allows players to invite other players)\n" +
						"### xteam.admin.core.promote - (Allows players to promote teammates)\n" +
						"### xteam.admin.core.sethq - (Allows players to set a headquarters)\n" +
						"### xteam.leader.core.demote - (Allows players to demote teammates)\n" +
						"### xteam.leader.core.disband - (Allows players to disband a team)\n" +
						"### xteam.leader.core.open - (Allows players to open team to public)\n" +
						"### xteam.leader.core.remove - (Allows players to remove players)\n" +
						"### xteam.leader.core.rename - (Allows players to rename team)\n" +
						"### xteam.leader.core.tag - (Allows players to set team tag)\n" +
						"### xteam.leader.core.setleader - (Allows players to set leader of team)\n" +
						"### xteam.serveradmin.core.chatspy - (Allows players to spy on team chat)\n" +
						"### xteam.serveradmin.core.disband - (Allows players to disband a team)\n" +
						"### xteam.serveradmin.core.demote - (Allows players to demote a teamPlayer on a team)\n" +
						"### xteam.serveradmin.core.hq - (Allows players to teleport to any headquarters)\n" +
						"### xteam.serveradmin.core.promote - (Allows players to promote a teamPlayer on a team)\n" +
						"### xteam.serveradmin.core.reload - (Allows players to reload the configuration file)\n" +
						"### xteam.serveradmin.core.remove - (Allows players to remove teamPlayer from a team)\n" +
						"### xteam.serveradmin.core.rename - (Allows players to rename a team)\n" +
						"### xteam.serveradmin.core.tag - (Allows players to set team tag)\n" +
						"### xteam.serveradmin.core.open - (Allows players to open a team to public)\n" +
						"### xteam.serveradmin.core.set - (Allows players to set the team of a teamPlayer)\n" +
						"### xteam.serveradmin.core.sethq - (Allows players to set the headquarters of a team)\n" +
						"### xteam.serveradmin.core.setleader - (Allows players to set the leader of a team)\n" +
						"### xteam.serveradmin.core.teleallhq - (Allows players to teleport everyone to their headquarters)\n" +
						"### xteam.serveradmin.core.tpall - (Allows players to teleport a team to current location)\n" +
						"### xteam.serveradmin.core.update - (Allows players to update teamPlayer names for Spout features)\n" +
						"############################################\n");
				bw.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		f = new File(getDataFolder().getAbsolutePath() + "/xTeam.log");
		if (!f.exists())
		{
			try
			{
				f.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
