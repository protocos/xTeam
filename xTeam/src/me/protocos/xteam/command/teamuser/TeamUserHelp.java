package me.protocos.xteam.command.teamuser;

import java.util.List;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.*;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.Message;
import me.protocos.xteam.message.MessageUtil;
import me.protocos.xteam.model.HelpPages;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.ChatColor;

public class TeamUserHelp extends TeamUserCommand
{
	private ICommandManager commandManager;
	private HelpPages pages;
	private int pageNum;

	public TeamUserHelp(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
		this.commandManager = teamPlugin.getCommandManager();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		pages.setTitle(ChatColor.AQUA + "Team Commands: [Page " + pageNum + "/" + pages.getTotalPages() + "] " + MessageUtil.highlightString(ChatColor.GRAY, "{optional} [required] pick/one"));
		pageNum--;
		new Message.Builder(pages.getPage(pageNum)).addRecipients(teamUser).disableFormatting().send(log);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		if (commandContainer.size() == 1 && commandContainer.getArgument(0).matches("(-)?[0-9]+"))
		{
			pageNum = Integer.parseInt(commandContainer.getArgument(0));
		}
		else if (commandContainer.size() == 2 && commandContainer.getArgument(1).matches("(-)?[0-9]+"))
		{
			pageNum = Integer.parseInt(commandContainer.getArgument(1));
		}
		else if (commandContainer.size() == 1)
		{
			pageNum = 1;
		}
		pages = new HelpPages();
		List<PlayerCommand> availableCommands = commandManager.getAvailableCommandsFor(teamUser);
		pages.addDescriptions(availableCommands);
		Requirements.checkPlayerHasCommands(pages);
		Requirements.checkPlayerCommandPageRange(pages, pageNum);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.or(new PatternBuilder().oneOrMore("help").optional(new PatternBuilder().whiteSpace().numbers()),
						new PatternBuilder("\\?+").optional(new PatternBuilder().whiteSpace().numbers()),
						new PatternBuilder().numbers())
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
		return "/team {help} {Page #}";
	}

	@Override
	public String getDescription()
	{
		return "user help page for xTeam";
	}
}
