package me.protocos.xteam.command.console;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ConsoleCommand;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import org.bukkit.command.CommandSender;

public class ConsoleOpen extends ConsoleCommand
{
	String teamName;

	public ConsoleOpen()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		Team team = xTeam.getInstance().getTeamManager().getTeam(teamName);
		team.setOpenJoining(!team.isOpenJoining());
		if (team.isOpenJoining())
			originalSender.sendMessage("Open joining is now " + ChatColorUtil.positiveMessage("enabled") + " for team " + teamName);
		else
			originalSender.sendMessage("Open joining is now " + ChatColorUtil.negativeMessage("disabled") + " for team " + teamName);
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		teamName = parseCommand.get(1);
	}

	@Override
	public String getPattern()
	{
		return patternOneOrMore("open") + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}

	@Override
	public String getUsage()
	{
		return "/team open [Team]";
	}

	@Override
	public String getDescription()
	{
		return "open team to public joining";
	}
}
