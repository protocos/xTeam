package me.protocos.xteam.command.console;

import java.util.List;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.command.action.InviteHandler;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.CommonUtil;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.scheduler.BukkitScheduler;

public class ConsoleDebug extends ConsoleCommand
{
	private BukkitUtil bukkitUtil;
	private InviteHandler inviteHandler;
	private BukkitScheduler bukkitScheduler;
	private String subCommand;
	private int taskID;
	private boolean testmode;

	public ConsoleDebug(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
		bukkitUtil = teamPlugin.getBukkitUtil();
		inviteHandler = teamPlugin.getInviteHandler();
		bukkitScheduler = teamPlugin.getBukkitScheduler();
		testmode = false;
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		if (subCommand.equalsIgnoreCase("chat"))
			this.log.info("Chat statuses: " + Configuration.chatStatus.toString());
		else if (subCommand.equalsIgnoreCase("invites"))
			this.log.info("Invites: " + inviteHandler.data());
		else if (subCommand.equalsIgnoreCase("spies"))
			this.log.info("Spies: " + Configuration.spies.toString());
		else if (subCommand.equalsIgnoreCase("created"))
			this.log.info("Last created: " + Configuration.lastCreated.toString());
		else if (subCommand.equalsIgnoreCase("players"))
		{
			this.log.info("Players:");
			for (String line : playerFactory.exportData())
				this.log.info(line);
		}
		else if (subCommand.equalsIgnoreCase("teams"))
		{
			this.log.info("Teams:");
			for (String line : teamCoordinator.exportData())
				this.log.info(line);
		}
		else if (subCommand.equalsIgnoreCase("perms"))
			this.log.info("Debugging permissions: \n" + printPermissions());
		else if (subCommand.equalsIgnoreCase("reset"))
		{
			reset();
			this.log.info("All players hunger reset");
			this.log.info("All players health reset");
			this.log.info("All players damage reset");
			for (World world : Bukkit.getWorlds())
			{
				this.log.info("environment reset for \"" + world.getName() + "\"");
			}
		}
		else if (subCommand.equalsIgnoreCase("live"))
			this.log.info("Bukkit server is " + (BukkitUtil.serverIsLive() ? "live!" : "offline."));
		else if (subCommand.equalsIgnoreCase("error"))
		{
			try
			{
				log.exception(new Exception("Test exception!"));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else if (subCommand.equalsIgnoreCase("tasks"))
			this.log.info("Tasks: " + teamPlugin.getBukkitScheduler().getPendingTasks());
		else if (subCommand.equalsIgnoreCase("testmode"))
		{
			if (!testmode)
			{
				this.log.info("Entering test mode");
				taskID = bukkitScheduler.scheduleSyncRepeatingTask(teamPlugin, new Runnable()
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
				this.log.info("Exiting test mode");
				bukkitScheduler.cancelTask(taskID);
			}
		}
		else
			this.log.info("Options are: debug [chat, invites, spies, created, players, teams, perms, reset, live, error, tasks, testmode]");
	}

	private void reset()
	{
		for (Player player : bukkitUtil.getOnlinePlayers())
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

	private String printPermissions()
	{
		String output = "";
		List<Permission> perms = teamPlugin.getPermissions();
		for (Permission perm : perms)
		{
			output += perm.getName() + " - " + perm.getDescription() + "\n";
		}
		return output;
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
		return "/team debug [Option]";
	}

	@Override
	public String getDescription()
	{
		return "console debug menu for xTeam";
	}
}
