package me.protocos.xteam.command.serveradmin;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.MessageUtil;
import me.protocos.xteam.util.PatternBuilder;

public class ServerAdminOpen extends ServerAdminCommand
{
	private String teamName;
	private ITeam changeTeam;

	public ServerAdminOpen(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		changeTeam.setOpenJoining(!changeTeam.isOpenJoining());
		if (changeTeam.isOpenJoining())
			player.sendMessage("Open joining is now " + MessageUtil.green("enabled") + " for team " + teamName);
		else
			player.sendMessage("Open joining is now " + MessageUtil.red("disabled") + " for team " + teamName);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		teamName = commandContainer.getArgument(1);
		changeTeam = teamManager.getTeam(teamName);
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
		return "xteam.core.serveradmin.open";
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
