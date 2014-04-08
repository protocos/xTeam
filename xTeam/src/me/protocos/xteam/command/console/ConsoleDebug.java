package me.protocos.xteam.command.console;

import java.util.List;
import me.protocos.xteam.XTeam;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.command.action.InviteHandler;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class ConsoleDebug extends ConsoleCommand
{
	private String subCommand;

	public ConsoleDebug()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		if (subCommand.equalsIgnoreCase("chat"))
			sender.sendMessage("Chat statuses: " + Configuration.chatStatus.toString());
		else if (subCommand.equalsIgnoreCase("invites"))
			sender.sendMessage("Invites: " + InviteHandler.data());
		else if (subCommand.equalsIgnoreCase("spies"))
			sender.sendMessage("Spies: " + Configuration.spies.toString());
		else if (subCommand.equalsIgnoreCase("created"))
			sender.sendMessage("Last created: " + Configuration.lastCreated.toString());
		else if (subCommand.equalsIgnoreCase("players"))
			sender.sendMessage("Players: \n" + XTeam.getInstance().getPlayerManager().toString());
		else if (subCommand.equalsIgnoreCase("teams"))
			sender.sendMessage("Teams: \n" + XTeam.getInstance().getTeamManager().toString());
		else if (subCommand.equalsIgnoreCase("perms"))
			sender.sendMessage("Debugging permissions: \n" + printPermissions());
		else if (subCommand.equalsIgnoreCase("reset"))
		{
			for (Player player : BukkitUtil.getOnlinePlayers())
			{
				player.setHealth(20);
				player.setFoodLevel(20);
				player.setNoDamageTicks(0);
			}
			sender.sendMessage("All players hunger reset");
			sender.sendMessage("All players health reset");
			sender.sendMessage("All players damage reset");
			for (World world : Bukkit.getWorlds())
			{
				world.setStorm(false);
				world.setThundering(false);
				world.setTime(0);
				sender.sendMessage("environment reset for \"" + world.getName() + "\"");
			}
		}
		else if (subCommand.equalsIgnoreCase("live"))
			sender.sendMessage("Bukkit server is " + (BukkitUtil.isLive() ? "live!" : "offline."));
		else if (subCommand.equalsIgnoreCase("error"))
		{
			try
			{
				XTeam.getInstance().getLog().exception(new Exception("Test message!"));
				sender.sendMessage("Error sent!");
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
			sender.sendMessage("Options are: debug [chat, invites, spies, created, players, teams, perms, reset, live, error]");
	}

	private String printPermissions()
	{
		String output = "";
		List<Permission> perms = XTeam.getInstance().getPermissions();
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
