package me.protocos.xteam.util;

import java.io.*;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandManager;
import me.protocos.xteam.command.ICommandManager;
import me.protocos.xteam.fakeobjects.FakeConsoleSender;
import me.protocos.xteam.fakeobjects.FakeTeamPlayer;
import me.protocos.xteam.fakeobjects.FakeXTeam;
import org.junit.Test;

public class BukkitDevPageGeneratorUtil
{
	private static final String LINE = "{{{____________________________________________________________________________}}}\n\n";
	private static final String BANNER_IMAGE = "{{http://dev.bukkit.org/media/images/43/4/logo_right_size.png|logo}}\n\n";
	private static final String TITLE = "=//xTeam Plugin//=\n\n\n";
	private static final String BRIEF_DESCRIPTION = "===Brief Description===\n\n" +
			"xTeam is a team-based PvP plugin that allows for creating and joining teams, setting a headquarters and various other features. It is meant for use on hardcore PvP servers where land is not protected.\n\n\n" +
			"This is a mod I've been working on for some time. Initially I figured a mod like this would be made eventually and I would just switch to using the alternative instead of mine but surprisingly I haven't quite found an equivalent. Factions is nice but if you want more of a 'hardcore' team plugin, then this mod is for you.\n\n";
	private static final String ALIASES = "**Command Aliases: '/team', '/xteam', '/xt'**\n\n\n";
	private static final String IMPORTANT = "===**!!! IMPORTANT !!!**===\n" +
			"[[http://dev.bukkit.org/bukkit-plugins/xteam/files/36-v1-8-1/|Version 1.8.1]] of the plugin contains an Anonymous Error Reporting feature for sending errors directly to me when they are thrown.\n\n**This feature does not reveal any private information.** In fact the only one thing that could possibly identify the client is encrypted even from me. The benefit of this feature is that I can see which errors are happening the most and fix them more quickly. Having said that, there is an option in the configuration file to disable this feature by setting 'anonymouserrorreporting = false' if  you feel so inclined. Just know that if this is disabled, I will not be able to see what errors are happening frequently from your server.\n\n";
	private static final String PARAMETERS = "**Command Parameters: {optional} [required]  pick/one\n\n";
	private static final String FEATURES = "===Features===\n" +
			"* create and join teams\n" +
			"* get info for any team\n" +
			"* change the team tag\n" +
			"* set a headquarters\n" +
			"* rally team to a location\n" +
			"* teleport to teammates and headquarters\n" +
			"* teleport to return location that is saved upon using any of the other teleport functions\n" +
			"* teleport to rally location set by team leader\n" +
			"* promote and demote players to use team admin commands\n" +
			"* team chat function to toggle between team chat and regular chat\n" +
			"* disable friendly fire\n" +
			"* wolves can be team members too!\n" +
			"* default teams for automatic joining on login\n\n\n";
	private static final String TEAM_USER_COMMANDS = "===Team User Commands===\n";
	private static final String TEAM_ADMIN_COMMANDS = "===Team Admin Commands===\n";
	private static final String TEAM_LEADER_COMMANDS = "===Team Leader Commands===\n";
	private static final String SERVER_ADMIN_COMMANDS = "===Server Admin Commands===\n";
	private static final String CONSOLE_COMMANDS = "===Console Commands===\n";
	private static final String PERMISSION_SUPPORT = "===Permission Support===\n" +
			"* Supports bukkit SuperPerms (PermissionsEx, PermissionsBukkit, and bPermissions, etc.)\n\n";
	private static final String PERMISSION_NODES = "===Permissions Nodes===\n";
	private static final String CONFIG_FILE = "===Configuration===\n\n" +
			"Contains data that the plugin uses universally - feel free to change the values to suit your needs. Also contains the permission nodes for the plugin. Use these inside whatever permissions plugin you desire to enable or disable commands for certain people or groups.\n\n" +
			"NOTE: As this project is still in Beta stage, I will be adding/changing features until I'm satisfied with the basic functionality of the plugin. For updating to a newer version of the plugin, check to see whether there are any configuration file changes. If so, you will want to reload the configuration file or add the new options yourself.\n\n";
	private static final String FAQ = "===[[http://dev.bukkit.org/bukkit-plugins/xteam/pages/faq/|FAQ]]===\n\n" +
			"Many of the frequently asked questions about the plugin.\n\n";
	private static final String LINKS = "===Links===\n\n[[http://www.youtube.com/watch?v=7Z4svhff9W0|Video explaining the commands]]\n\n";
	private static final String DONATIONS = "===Donations===\n\nI work on this project in my spare time and I will always have a love for Minecraft no matter what, but it certainly does make me smile when people feel like contributing. Donating to the project helps encourage me to continue providing support and feedback to all of you awesome peoples! If you feel like helping out, there's a link at the top right of the page. ;)\n\n";
	private static final String BUG_REPORTS = "===Bug Reports===\n\n" +
			"Please leave any questions, comments, concerns, and potential bugs in the comments. I will try to squash them when I can. Having said that, I am a real human being, so if the bug is not fixed right away, please be patient.";

	@Test
	public void generateDescriptionPage() throws IOException
	{
		File file = SystemUtil.ensureFile("BukkitDescription.txt");
		FileWriter writer = new FileWriter(file);
		writer.write(BANNER_IMAGE);
		writer.write(TITLE);
		writer.write(BRIEF_DESCRIPTION);
		writer.write(LINE);
		writer.write(IMPORTANT);
		writer.write(LINE);
		writer.write(ALIASES);
		writer.write(PARAMETERS);
		writer.write(FEATURES);
		ICommandManager manager = new CommandManager();
		TeamPlugin fakePlugin = FakeXTeam.asTeamPlugin();
		fakePlugin.registerCommands(manager);
		writer.write(TEAM_USER_COMMANDS);
		for (String command : manager.getAvailableCommandsFor(FakeTeamPlayer.withUserPermissions()))
			writer.write("* " + command.replaceAll("�.", "") + "\n");
		writer.write("\n");
		writer.write(TEAM_ADMIN_COMMANDS);
		for (String command : manager.getAvailableCommandsFor(FakeTeamPlayer.withAdminPermissions()))
			writer.write("* " + command.replaceAll("�.", "") + "\n");
		writer.write("\n");
		writer.write(TEAM_LEADER_COMMANDS);
		for (String command : manager.getAvailableCommandsFor(FakeTeamPlayer.withLeaderPermissions()))
			writer.write("* " + command.replaceAll("�.", "") + "\n");
		writer.write("\n");
		writer.write(SERVER_ADMIN_COMMANDS);
		for (String command : manager.getAvailableCommandsFor(FakeTeamPlayer.withServerAdminPermissions()))
			writer.write("* " + command.replaceAll("�.", "") + "\n");
		writer.write("\n");
		writer.write(CONSOLE_COMMANDS);
		for (String command : manager.getAvailableCommandsFor(new FakeConsoleSender()))
			writer.write("* " + command.replaceAll("�.", "") + "\n");
		writer.write("\n");
		writer.write(PERMISSION_SUPPORT);
		writer.write(PERMISSION_NODES);
		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(new FileReader(new File("plugin.yml")));
		String line;
		boolean startRecording = false;
		while ((line = reader.readLine()) != null)
		{
			line = line.trim();
			if (startRecording)
			{
				if (line.startsWith("xteam.") && line.endsWith(":"))
					writer.write("* " + line.replaceAll(":", "") + " - ");
				if (line.startsWith("description: "))
					writer.write(line.replaceAll("description: ", "") + "\n");
			}
			else
			{
				if (line.startsWith("permissions:"))
					startRecording = true;
			}
		}
		writer.write("\n");
		writer.write(CONFIG_FILE);
		writer.write(FAQ);
		writer.write(LINKS);
		writer.write(DONATIONS);
		writer.write(BUG_REPORTS);
		writer.close();
	}

	private static final String CONFIG_OPTIONS = "**Q: How do I set/turn off/enable/change/remove XXXXXX feature?**\n\n" +
			"A: I would highly recommend looking at the configuration file before posting questions like this as there are comments explaining what each variable and much of it is self-explanatory.\n\n";
	private static final String DISABLE_COMMANDS = "**Q: How do I disable certain commands like teleporting?**\n\n" +
			"A: If you would like to disable certain commands for groups of users, simply do not give them the permission that corresponds to that command. For instance, If you do not want users to have access to the team rally command or the team return command, simply give them permission for all commands except for **xteam.core.user.rally** and **xteam.core.user.return.**\n\n";
	private static final String PLAY_WITHOUT_PERMISSIONS = "**Q: How do I play without permissions?**\n\n" +
			"A: In the configuration file, there is an option \"nopermissions\". If set to true, then by default all players will have access to team user, team admin, and team leader commands given that they are one of those three ranks. Also by default, ops can access all server admin commands as if they were in the console.\n\n";
	private static final String PERMISSION_DENIED = "**Q: Why am I getting permission denied when I try to use commands?**\n\n" +
			"A: There are a number of reasons that might cause this but I will attempt to address the most common. \n" +
			"# Permissions have not been distributed to users yet (see config file for list of permissions). \n" +
			"# There is no permissions plugin on your server in which case I would recommend PermissionsEx as it is pretty simple and straightforward. \n" +
			"# nopermissions is set to false inside the configuration file." +
			"\n\n";
	private static final String PLS_POST_TEH_SRC = "**Q: Is the source code online?**\n\n" +
			"A: Yes! Head on over to https://github.com/protocos/xTeam to get the latest version of the code!\n\n";
	private static final String LANGUAGES = "**Q: Are multiple languages supported?**\n\n" +
			"A: At the moment, only english is supported as it is the language I know the best. I do plan on adding multi-language support in the future, but I would need more help than just google translate to get it correct. Also, this would require an entire overhaul to the way messages are stored and displayed inside the plugin, so I would not expect it very soon.\n\n";
	private static final String WHY_NO_UPDATE = "**Q: Why hasn't there been an update recently?**\n\n" +
			"A: This is a frequent enough question that I feel needs addressing. I am in college at the moment and have other priorities that I have to contend with. That being said, I will always love minecraft and have a passion for personal projects like this one, so I will not be leaving the project any time soon. All I can do is ask for your patience and continued support as I learn from this project as well as school.\n\n";
	private static final String MIGRATE_OLD_DATA = "**Q: Is there a way to migrate my old data to new data?**\n\n" +
			"A: Yes! Just follow the procedure below: \n" +
			"# Shut down the server.\n" +
			"# **IMPORTANT** If any tables (team_data, player_data) already exists in the database, delete them! This will avoid corrupting the data.\n" +
			"# Launch the server with the File storage type set in the config to load all of the data into the cache.\n" +
			"# Open the configuration file and change the storage type to SQLite or whichever storage type you prefer.\n" +
			"# Reload the server with the '/reload' command and the data should be migrated.\n" +
			"\n\n";

	@Test
	public void generateFAQPage() throws IOException
	{
		File file = SystemUtil.ensureFile("BukkitFAQ.txt");
		FileWriter writer = new FileWriter(file);
		writeLine(writer, CONFIG_OPTIONS);
		writeLine(writer, DISABLE_COMMANDS);
		writeLine(writer, PLAY_WITHOUT_PERMISSIONS);
		writeLine(writer, PERMISSION_DENIED);
		writeLine(writer, PLS_POST_TEH_SRC);
		writeLine(writer, LANGUAGES);
		writeLine(writer, WHY_NO_UPDATE);
		writeLine(writer, MIGRATE_OLD_DATA);
		writer.close();
	}

	private static void writeLine(FileWriter writer, String line) throws IOException
	{
		writer.write(LINE);
		writer.write(line);
	}
}
