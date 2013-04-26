package me.protocos.xteam.command.console;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.command.BaseConsoleCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.core.exception.TeamInvalidCommandException;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ConsoleTeleAllHQ extends BaseConsoleCommand
{
	public ConsoleTeleAllHQ()
	{
		super();
	}
	public ConsoleTeleAllHQ(ConsoleCommandSender sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		Player[] players = Data.BUKKIT.getOnlinePlayers();
		for (Player p : players)
		{
			TeamPlayer player = new TeamPlayer(p);
			Team team = player.getTeam();
			{
				if (team == null)
				{
					originalSender.sendMessage(player.getName() + " does not have a team and was not teleported");
				}
				else if (!team.hasHQ())
				{
					originalSender.sendMessage("No team headquarters set for team " + team.getName() + " for " + p.getName());
				}
				else
				{
					player.teleport(team.getHeadquarters());
					player.sendMessage("You have been teleported to the team headquarters by an admin");
				}
			}
		}
		originalSender.sendMessage("Players teleported");
	}
	@Override
	public void checkRequirements() throws TeamException
	{
		if (parseCommand.size() == 1)
		{
		}
		else
		{
			throw new TeamInvalidCommandException();
		}
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("teleallhq") + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getUsage()
	{
		return baseCommand + " teleallhq";
	}
}
