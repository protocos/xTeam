package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.PlayerManager;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.TeamException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminTeleAllHQ extends ServerAdminCommand
{
	public AdminTeleAllHQ()
	{
		super();
	}
	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		Player[] players = Data.BUKKIT.getOnlinePlayers();
		for (Player p : players)
		{
			TeamPlayer otherPlayer = PlayerManager.getPlayer(p);
			Team playerTeam = otherPlayer.getTeam();
			{
				if (playerTeam == null)
				{
					originalSender.sendMessage(otherPlayer.getName() + " does not have a team and was not teleported");
				}
				else if (!playerTeam.hasHeadquarters())
				{
					originalSender.sendMessage("No team headquarters set for team " + playerTeam.getName() + " for " + p.getName());
				}
				else
				{
					otherPlayer.teleport(playerTeam.getHeadquarters());
					otherPlayer.sendMessage("You have been teleported to the team headquarters by an admin");
				}
			}
		}
		originalSender.sendMessage("Players teleported");
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		super.checkRequirements(originalSender, parseCommand);
	}
	@Override
	public String getPattern()
	{
		return "tele" + patternOneOrMore("allhq") + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.serveradmin.core.teleallhq";
	}
	@Override
	public String getUsage()
	{
		return "/team teleallhq";
	}
}
