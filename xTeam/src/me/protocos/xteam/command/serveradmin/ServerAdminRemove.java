package me.protocos.xteam.command.serveradmin;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ServerAdminCommand;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;

public class ServerAdminRemove extends ServerAdminCommand
{
	private String teamName, playerName;
	private ITeamPlayer changePlayer;

	public ServerAdminRemove()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		Team changeTeam = changePlayer.getTeam();
		changeTeam.removePlayer(playerName);
		if (!playerName.equals(player.getName()))
			player.sendMessage("You " + ChatColorUtil.negativeMessage("removed") + " " + playerName + " from " + teamName);
		changePlayer.sendMessage("You have been " + ChatColorUtil.negativeMessage("removed") + " from " + changeTeam.getName() + " by an admin");
		if (changeTeam.isEmpty())
		{
			player.sendMessage(teamName + " has been " + ChatColorUtil.negativeMessage("disbanded"));
			xTeam.getInstance().getTeamManager().removeTeam(changeTeam.getName());
		}
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		teamName = commandContainer.getArgument(1);
		playerName = commandContainer.getArgument(2);
		changePlayer = xTeam.getInstance().getPlayerManager().getPlayer(playerName);
		Requirements.checkPlayerHasPlayedBefore(changePlayer);
		Requirements.checkPlayerHasTeam(changePlayer);
		Requirements.checkPlayerLeaderLeaving(changePlayer);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("re")
				.oneOrMore("move")
				.whiteSpace()
				.anyString()
				.whiteSpace()
				.anyString()
				.whiteSpaceOptional()
				.toString();
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
