package me.protocos.xteam.command.serveradmin;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ServerAdminCommand;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.command.CommandSender;

public class ServerAdminOpen extends ServerAdminCommand
{
	private String teamName;
	private Team changeTeam;

	public ServerAdminOpen()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		changeTeam.setOpenJoining(!changeTeam.isOpenJoining());
		if (changeTeam.isOpenJoining())
			originalSender.sendMessage("Open joining is now " + ChatColorUtil.positiveMessage("enabled") + " for team " + teamName);
		else
			originalSender.sendMessage("Open joining is now " + ChatColorUtil.negativeMessage("disabled") + " for team " + teamName);
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		teamName = parseCommand.get(1);
		changeTeam = xTeam.getInstance().getTeamManager().getTeam(teamName);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("open")
				.whiteSpace()
				.anyString()
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.serveradmin.core.open";
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
