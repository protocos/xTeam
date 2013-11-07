package me.protocos.xteam.command.console;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.InviteHandler;
import me.protocos.xteam.core.exception.TeamException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
			originalSender.sendMessage("Chat statuses: " + Data.chatStatus.toString());
		else if (subCommand.equalsIgnoreCase("invites"))
			originalSender.sendMessage("Invites: " + InviteHandler.data());
		else if (subCommand.equalsIgnoreCase("spies"))
			originalSender.sendMessage("Spies: " + Data.spies.toString());
		else if (subCommand.equalsIgnoreCase("created"))
			originalSender.sendMessage("Last created: " + Data.lastCreated.toString());
		else if (subCommand.equalsIgnoreCase("players"))
			originalSender.sendMessage("Players: \n" + xTeam.getPlayerManager().toString());
		else if (subCommand.equalsIgnoreCase("teams"))
			originalSender.sendMessage("Teams: \n" + xTeam.getTeamManager().toString());
		else if (subCommand.equalsIgnoreCase("resetplayers"))
		{
			for (Player player : Data.BUKKIT.getOnlinePlayers())
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
				xTeam.getLog().exception(new Exception("Test message!"));
				originalSender.sendMessage("Email sent!");
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
			originalSender.sendMessage("Options are: debug [chat, invites, spies, created, players, teams, resetplayers, email]");
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		super.checkRequirements(originalSender, parseCommand);
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
		return "d" + patternOneOrMore("ebug") + "(" + WHITE_SPACE + ANY_CHARS + ")?" + OPTIONAL_WHITE_SPACE;
	}

	@Override
	public String getUsage()
	{
		return "/team debug [Option]";
	}
}
