package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeamPlugin;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import org.bukkit.command.CommandSender;

public class AdminRemove extends ServerAdminCommand
{
	private String teamName, playerName;
	private ITeamPlayer changePlayer;

	public AdminRemove()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		Team changeTeam = changePlayer.getTeam();
		changeTeam.removePlayer(playerName);
		if (!playerName.equals(originalSender.getName()))
			originalSender.sendMessage("You " + ChatColorUtil.negativeMessage("removed") + " " + playerName + " from " + teamName);
		changePlayer.sendMessage("You have been " + ChatColorUtil.negativeMessage("removed") + " from " + changeTeam.getName() + " by an admin");
		if (changeTeam.isEmpty())
		{
			originalSender.sendMessage(teamName + " has been " + ChatColorUtil.negativeMessage("disbanded"));
			xTeamPlugin.getInstance().getTeamManager().removeTeam(changeTeam.getName());
		}
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		teamName = parseCommand.get(1);
		playerName = parseCommand.get(2);
		changePlayer = xTeamPlugin.getInstance().getPlayerManager().getPlayer(playerName);
		Requirements.checkPlayerHasPlayedBefore(changePlayer);
		Requirements.checkPlayerHasTeam(changePlayer);
		Requirements.checkPlayerLeaderLeaving(changePlayer);
	}

	@Override
	public String getPattern()
	{
		return patternOneOrMore("re") + patternOneOrMore("move") + WHITE_SPACE + ANY_CHARS + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.serveradmin.core.remove";
	}

	@Override
	public String getUsage()
	{
		return "/team remove [Team] [Player]";
	}

	@Override
	public String getDescription()
	{
		return "remove player from team";
	}
}
