package me.protocos.xteam.command.teamleader;

import me.protocos.xteam.XTeam;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.command.TeamLeaderCommand;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.MessageUtil;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.entity.Player;

public class TeamLeaderDemote extends TeamLeaderCommand
{
	private String otherPlayer;

	public TeamLeaderDemote()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		team.demote(otherPlayer);
		Player other = BukkitUtil.getPlayer(otherPlayer);
		if (other != null)
			other.sendMessage("You've been " + MessageUtil.negativeMessage("demoted"));
		teamPlayer.sendMessage("You" + MessageUtil.negativeMessage(" demoted ") + otherPlayer);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		otherPlayer = commandContainer.getArgument(1);
		ITeamPlayer other = XTeam.getInstance().getPlayerManager().getPlayer(otherPlayer);
		ITeamPlayer demotePlayer = XTeam.getInstance().getPlayerManager().getPlayer(otherPlayer);
		Requirements.checkPlayerIsTeammate(teamPlayer, other);
		Requirements.checkPlayerLeaderDemote(demotePlayer);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("demote")
				.whiteSpace()
				.anyString()
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.core.leader.demote";
	}

	@Override
	public String getUsage()
	{
		return "/team demote [Player]";
	}

	@Override
	public String getDescription()
	{
		return "demote team admin";
	}
}
