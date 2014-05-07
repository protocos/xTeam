package me.protocos.xteam.command.serveradmin;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.MessageUtil;
import me.protocos.xteam.util.PatternBuilder;

public class ServerAdminRemove extends ServerAdminCommand
{
	private String teamName, playerName;
	private ITeamPlayer changePlayer;

	public ServerAdminRemove(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		ITeam changeTeam = changePlayer.getTeam();
		changeTeam.removePlayer(playerName);
		if (!playerName.equals(player.getName()))
			player.sendMessage("You " + MessageUtil.negativeMessage("removed") + " " + playerName + " from " + teamName);
		changePlayer.sendMessage("You have been " + MessageUtil.negativeMessage("removed") + " from " + changeTeam.getName() + " by an admin");
		if (changeTeam.isEmpty())
		{
			player.sendMessage(teamName + " has been " + MessageUtil.negativeMessage("disbanded"));
			teamManager.disbandTeam(changeTeam.getName());
		}
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		teamName = commandContainer.getArgument(1);
		playerName = commandContainer.getArgument(2);
		changePlayer = playerManager.getPlayer(playerName);
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
		return "xteam.core.serveradmin.remove";
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
