package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.util.StringUtil.*;
import java.util.List;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.TeamUserCommand;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.HelpPages;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class TeamUserHelp extends TeamUserCommand
{
	private HelpPages pages;
	private int pageNum;

	public TeamUserHelp()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		pages.setTitle(ChatColor.AQUA + "Team Commands: [Page " + pageNum + "/" + pages.getTotalPages() + "] " + ChatColorUtil.highlightString(ChatColor.GRAY, "{optional} [required] pick/one"));
		pageNum--;
		originalSender.sendMessage(pages.getPage(pageNum));
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		if (parseCommand.size() == 1 && parseCommand.get(0).matches("[0-9]+"))
		{
			pageNum = Integer.parseInt(parseCommand.get(0));
		}
		else if (parseCommand.size() == 2 && parseCommand.get(1).matches("[0-9]+"))
		{
			pageNum = Integer.parseInt(parseCommand.get(1));
		}
		else if (parseCommand.size() == 1)
		{
			pageNum = 1;
		}
		pages = new HelpPages();
		List<String> availableCommands = xTeam.getInstance().getCommandManager().getAvailableCommandsFor(teamPlayer);
		pages.addLines(availableCommands);
		Requirements.checkPlayerHasCommands(pages);
		Requirements.checkPlayerCommandPageRange(pages, pageNum);
	}

	@Override
	public String getPattern()
	{
		return "(((" + patternOneOrMore("help") + "|\\?+)" + WHITE_SPACE + NUMBERS + ")" + "|" + NUMBERS + ")" + OPTIONAL_WHITE_SPACE;
	}

	@Override
	public String getPermissionNode()
	{
		return "help";
	}

	@Override
	public String getUsage()
	{
		return "/team {help} [Page]";
	}

	@Override
	public String getDescription()
	{
		return "user help page for xTeam";
	}
}
