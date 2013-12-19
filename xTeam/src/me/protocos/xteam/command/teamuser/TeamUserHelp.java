package me.protocos.xteam.command.teamuser;

import java.util.List;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.TeamUserCommand;
import me.protocos.xteam.api.model.HelpPages;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.ChatColor;

public class TeamUserHelp extends TeamUserCommand
{
	private HelpPages pages;
	private int pageNum;

	public TeamUserHelp()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		pages.setTitle(ChatColor.AQUA + "Team Commands: [Page " + pageNum + "/" + pages.getTotalPages() + "] " + ChatColorUtil.highlightString(ChatColor.GRAY, "{optional} [required] pick/one"));
		pageNum--;
		teamPlayer.sendMessage(pages.getPage(pageNum));
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		if (commandContainer.size() == 1 && commandContainer.getArgument(0).matches("[0-9]+"))
		{
			pageNum = Integer.parseInt(commandContainer.getArgument(0));
		}
		else if (commandContainer.size() == 2 && commandContainer.getArgument(1).matches("[0-9]+"))
		{
			pageNum = Integer.parseInt(commandContainer.getArgument(1));
		}
		else if (commandContainer.size() == 1)
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
		return new PatternBuilder()
				.or(new PatternBuilder()
						.or(new PatternBuilder()
								.oneOrMore("help"), new PatternBuilder()
								.append("\\?+"))
						.whiteSpace()
						.numbers(), new PatternBuilder()
						.numbers())
				.whiteSpaceOptional()
				.toString();
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
