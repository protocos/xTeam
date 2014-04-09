package me.protocos.xteam.command.console;

import me.protocos.xteam.XTeam;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.MessageUtil;
import me.protocos.xteam.util.PatternBuilder;

public class ConsoleDisband extends ConsoleCommand
{
	private String teamName;
	private ITeam changeTeam;

	public ConsoleDisband()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		changeTeam.sendMessage("Your team has been " + MessageUtil.negativeMessage("disbanded") + " by an admin");
		XTeam.getInstance().getTeamManager().disbandTeam(teamName);
		sender.sendMessage("You " + MessageUtil.negativeMessage("disbanded") + " " + changeTeam.getName() + (changeTeam.hasTag() ? " [" + changeTeam.getTag() + "]" : ""));
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		teamName = commandContainer.getArgument(1);
		changeTeam = XTeam.getInstance().getTeamManager().getTeam(teamName);
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