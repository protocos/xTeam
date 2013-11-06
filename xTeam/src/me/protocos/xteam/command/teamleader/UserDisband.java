package me.protocos.xteam.command.teamleader;

import static me.protocos.xteam.util.StringUtil.OPTIONAL_WHITE_SPACE;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import org.bukkit.command.CommandSender;

public class UserDisband extends UserCommand
{
	public UserDisband()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		for (ITeamPlayer playerDisband : teamPlayer.getOnlineTeammates())
		{
			playerDisband.sendMessage("Team has been " + ChatColorUtil.negativeMessage("disbanded") + " by the team leader");
		}
		xTeam.getTeamManager().removeTeam(team.getName());
		originalSender.sendMessage("You " + ChatColorUtil.negativeMessage("disbanded") + " your team");
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		super.checkRequirements(originalSender, parseCommand);
		Requirements.checkPlayerHasTeam(teamPlayer);
		Requirements.checkPlayerIsTeamLeader(teamPlayer);
	}

	@Override
	public String getPattern()
	{
		return "disband" + OPTIONAL_WHITE_SPACE;
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.leader.core.disband";
	}

	@Override
	public String getUsage()
	{
		return "/team disband";
	}
}
