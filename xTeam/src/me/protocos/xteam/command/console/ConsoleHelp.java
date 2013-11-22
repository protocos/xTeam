package me.protocos.xteam.command.console;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ConsoleCommand;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.HelpPages;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.ChatColor;

public class ConsoleHelp extends ConsoleCommand
{
	private HelpPages pages;

	public ConsoleHelp()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		pages.setTitle(ChatColor.AQUA + "Console Commands: " + ChatColorUtil.highlightString(ChatColor.GRAY, "{optional} [required] pick/one"));
		sender.sendMessage(pages.getTitle());
		for (int index = 0; index < pages.getNumLines(); index++)
		{
			sender.sendMessage(pages.getLine(index));
		}
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		pages = new HelpPages();
		pages.addLines(xTeam.getInstance().getCommandManager().getAvailableConsoleCommands(sender));
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.optional(new PatternBuilder()
						.or(new PatternBuilder().oneOrMore("help"), new PatternBuilder("\\?+")))
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getUsage()
	{
		return "/team {help}";
	}

	@Override
	public String getDescription()
	{
		return "console help menu for xTeam";
	}
}
