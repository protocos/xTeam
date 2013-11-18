package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.util.StringUtil.*;
import java.util.List;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.TeamUserCommand;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import org.bukkit.command.CommandSender;

public class TeamUserList extends TeamUserCommand
{
	public TeamUserList()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		List<String> teams = xTeam.getInstance().getTeamManager().getAllTeams().getOrder();
		String message = "Teams: " + teams.toString().replaceAll("\\[|\\]", "");
		if (teams.isEmpty())
			originalSender.sendMessage("There are " + ChatColorUtil.negativeMessage("no") + " teams");
		else
			originalSender.sendMessage(message);
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
	}

	@Override
	public String getPattern()
	{
		return patternOneOrMore("list") + OPTIONAL_WHITE_SPACE;
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.player.core.list";
	}

	@Override
	public String getUsage()
	{
		return "/team list";
	}

	@Override
	public String getDescription()
	{
		return "list all teams on the server";
	}
}
