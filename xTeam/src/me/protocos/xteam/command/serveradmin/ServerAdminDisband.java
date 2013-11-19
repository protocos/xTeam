package me.protocos.xteam.command.serveradmin;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ServerAdminCommand;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.command.CommandSender;

public class ServerAdminDisband extends ServerAdminCommand
{
	private String teamName;
	private Team changeTeam;

	public ServerAdminDisband()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		changeTeam.sendMessage("Your team has been " + ChatColorUtil.negativeMessage("disbanded") + " by an admin");
		xTeam.getInstance().getTeamManager().removeTeam(teamName);
		originalSender.sendMessage("You " + ChatColorUtil.negativeMessage("disbanded") + " " + changeTeam.getName() + (changeTeam.hasTag() ? " [" + changeTeam.getTag() + "]" : ""));
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		teamName = parseCommand.get(1);
		changeTeam = xTeam.getInstance().getTeamManager().getTeam(teamName);
		Requirements.checkTeamExists(teamName);
		Requirements.checkTeamIsDefault(changeTeam);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("dis")
				.oneOrMore("band")
				.whiteSpace()
				.anyString()
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.serveradmin.core.disband";
	}

	@Override
	public String getUsage()
	{
		return "/team disband [Team]";
	}

	@Override
	public String getDescription()
	{
		return "disband a team";
	}
}
