package me.protocos.xteam.command.teamleader;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.TeamLeaderCommand;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.command.CommandSender;

public class TeamLeaderRename extends TeamLeaderCommand
{
	private String desiredName;

	public TeamLeaderRename()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		xTeam.getInstance().getTeamManager().removeTeam(team.getName());
		team.setName(desiredName);
		xTeam.getInstance().getTeamManager().addTeam(team);
		for (ITeamPlayer mate : teamPlayer.getOnlineTeammates())
		{
			mate.sendMessage("The team has been " + ChatColorUtil.positiveMessage("renamed") + " to " + desiredName);
		}
		originalSender.sendMessage("You " + ChatColorUtil.positiveMessage("renamed") + " the team to " + desiredName);
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		desiredName = parseCommand.get(1);
		Requirements.checkTeamNameTooLong(desiredName);
		Requirements.checkTeamNameAlphaNumeric(desiredName);
		Requirements.checkTeamNameAlreadyUsed(desiredName, team);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("re")
				.oneOrMore("name")
				.whiteSpace()
				.anyString()
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.leader.core.rename";
	}

	@Override
	public String getUsage()
	{
		return "/team rename [Name]";
	}

	@Override
	public String getDescription()
	{
		return "rename the team";
	}
}
