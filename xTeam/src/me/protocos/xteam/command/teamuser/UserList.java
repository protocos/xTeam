package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.util.StringUtil.*;
import java.util.List;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.core.exception.TeamException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class UserList extends UserCommand
{
	public UserList()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		List<String> teams = xTeam.getTeamManager().getAllTeamNames();
		String message = "Teams: " + teams.toString().replaceAll("\\[|\\]", "");
		if (teams.isEmpty())
			originalSender.sendMessage(ChatColor.RED + "There are no teams");
		else
			originalSender.sendMessage(message);
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		super.checkRequirements(originalSender, parseCommand);
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
}
