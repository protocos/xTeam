package me.protocos.xteam.command.console;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.collections.HashList;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.command.ICommandManager;
import me.protocos.xteam.command.PlayerCommand;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.CommonUtil;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class ConsoleDebug extends ConsoleCommand
{
	private static HashList<String, Runnable> options = new HashList<String, Runnable>();

	public static void addDebugOption(String option, Runnable runnable)
	{
		options.put(option, runnable);
	}

	private static TeamPlugin plugin;
	private static IPlayerFactory pF;
	private static ICommandManager cM;
	private static int taskID;
	private static boolean testmode;
	private String subCommand;

	public ConsoleDebug(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
		plugin = teamPlugin;
		pF = teamPlugin.getPlayerFactory();
		cM = teamPlugin.getCommandManager();
		testmode = false;

		options.put("chat", new Runnable()
		{
			@Override
			public void run()
			{
				System.out.println("Chat statuses: " + Configuration.chatStatus.toString());
			}
		});
		options.put("invites", new Runnable()
		{
			@Override
			public void run()
			{
				System.out.println("Invites: ");
				for (String line : plugin.getInviteHandler().exportData())
					System.out.println(line);
			}
		});
		options.put("spies", new Runnable()
		{
			@Override
			public void run()
			{
				System.out.println("Spies: " + Configuration.spies.toString());
			}
		});
		options.put("created", new Runnable()
		{
			@Override
			public void run()
			{
				System.out.println("Last created: " + Configuration.lastCreated.toString());
			}
		});
		options.put("players", new Runnable()
		{
			@Override
			public void run()
			{
				System.out.println("Players:");
				for (String line : plugin.getPlayerFactory().exportData())
					System.out.println(line);
			}
		});
		options.put("teams", new Runnable()
		{
			@Override
			public void run()
			{
				System.out.println("Teams:");
				for (String line : plugin.getTeamCoordinator().exportData())
					System.out.println(line);
			}
		});
		options.put("perms", new Runnable()
		{
			@Override
			public void run()
			{
				System.out.println("Debugging permissions for online players:");
				System.out.println("'nopermissions' is set to '" + Configuration.NO_PERMISSIONS + "' in the configuration file");
				for (ITeamPlayer player : pF.getOnlinePlayers())
				{
					System.out.println("==================================================");
					System.out.println("Permissions for '" + player.getName() + "'");
					System.out.println("==================================================");
					for (PlayerCommand command : cM.getPlayerCommands())
					{
						if (player.hasPermission(command))
							System.out.println("[ + ] " + command.getPermissionNode() + " - " + command.getDescription());
						else
							System.out.println("[   ] " + command.getPermissionNode() + " - " + command.getDescription());
					}
				}
			}
		});
		options.put("reset", new Runnable()
		{
			@Override
			public void run()
			{
				reset();
				System.out.println("All players hunger reset");
				System.out.println("All players health reset");
				System.out.println("All players damage reset");
				for (World world : Bukkit.getWorlds())
					System.out.println("environment reset for \"" + world.getName() + "\"");
			}
		});
		options.put("live", new Runnable()
		{
			@Override
			public void run()
			{
				System.out.println("Bukkit server is " + (BukkitUtil.serverIsLive() ? "live!" : "offline."));
			}
		});
		options.put("error", new Runnable()
		{
			@Override
			public void run()
			{
				throw new RuntimeException("Test exception!");
			}
		});
		options.put("tasks", new Runnable()
		{
			@Override
			public void run()
			{
				System.out.println("Tasks: " + plugin.getBukkitScheduler().getPendingTasks());
			}
		});
		options.put("reload", new Runnable()
		{
			@Override
			public void run()
			{
				plugin.write();
				plugin.read();
				System.out.println("Reloading Data...");
			}
		});
		options.put("mode", new Runnable()
		{
			@Override
			public void run()
			{
				if (!testmode)
				{
					System.out.println("ENTERING test mode...");
					taskID = plugin.getBukkitScheduler().scheduleSyncRepeatingTask(plugin, new Runnable()
					{
						@Override
						public void run()
						{
							reset();
						}
					}, CommonUtil.LONG_ZERO, 100L);
				}
				else
				{
					System.out.println("EXITING test mode...");
					plugin.getBukkitScheduler().cancelTask(taskID);
				}
				testmode = !testmode;
			}
		});

	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		for (String option : options.getOrder())
		{
			if (subCommand.equals(option))
			{
				options.get(option).run();
				return;
			}
		}
		System.out.println("Options are: team debug " + options.getOrder().toString().replaceAll("\\[", "\\{").replaceAll("\\]", "\\}").replaceAll(", ", "/"));
	}

	private static void reset()
	{
		for (Player player : plugin.getBukkitUtil().getOnlinePlayers())
		{
			player.setHealth(20);
			player.setFoodLevel(20);
			player.setNoDamageTicks(0);
			player.setFireTicks(0);
		}
		for (World world : Bukkit.getWorlds())
		{
			world.setStorm(false);
			world.setThundering(false);
			world.setTime(0);
		}
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		subCommand = commandContainer.getArgument(1);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.append("d")
				.oneOrMore("ebug")
				.optional(new PatternBuilder()
						.whiteSpace()
						.anyString())
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getUsage()
	{
		return "team debug {Option}";
	}

	@Override
	public String getDescription()
	{
		return "console debug menu for xTeam";
	}
}
