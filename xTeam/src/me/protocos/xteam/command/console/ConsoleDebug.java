package me.protocos.xteam.command.console;

import java.util.List;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ConsoleCommand;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.core.Configuration;
import me.protocos.xteam.core.InviteHandler;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.command.CommandSender;
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
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		if (subCommand.equalsIgnoreCase("chat"))
			originalSender.sendMessage("Chat statuses: " + Configuration.chatStatus.toString());
		else if (subCommand.equalsIgnoreCase("invites"))
			originalSender.sendMessage("Invites: " + InviteHandler.data());
		else if (subCommand.equalsIgnoreCase("spies"))
			originalSender.sendMessage("Spies: " + Configuration.spies.toString());
		else if (subCommand.equalsIgnoreCase("created"))
			originalSender.sendMessage("Last created: " + Configuration.lastCreated.toString());
		else if (subCommand.equalsIgnoreCase("players"))
			originalSender.sendMessage("Players: \n" + xTeam.getInstance().getPlayerManager().toString());
		else if (subCommand.equalsIgnoreCase("teams"))
			originalSender.sendMessage("Teams: \n" + xTeam.getInstance().getTeamManager().toString());
		else if (subCommand.equalsIgnoreCase("perms"))
			originalSender.sendMessage("Debugging permissions: \n" + printPermissions());
		else if (subCommand.equalsIgnoreCase("resetplayers"))
		{
			for (Player player : BukkitUtil.getOnlinePlayers())
			{
				player.setHealth(20);
				player.setFoodLevel(20);
			}
			originalSender.sendMessage("All players health reset");
		}
		else if (subCommand.equalsIgnoreCase("email"))
		{
			try
			{
				xTeam.getInstance().getLog().exception(new Exception("Test message!"));
				originalSender.sendMessage("Email sent!");
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
			originalSender.sendMessage("Options are: debug [chat, invites, spies, created, players, teams, perms, resetplayers, email]");
	}

	private String printPermissions()
	{
		String output = "";
		List<Permission> perms = xTeam.getInstance().getPermissions();
		for (Permission perm : perms)
		{
			output += perm.getName() + " - " + perm.getDescription() + "\n";
		}
		return output;
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		if (parseCommand.size() == 1)
		{
			subCommand = "";
		}
		else if (parseCommand.size() == 2)
		{
			subCommand = parseCommand.get(1);
		}
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
		return "Console debug menu for xTeam";
	}
}
