package me.protocos.xteam.command.console;

import me.protocos.xteam.XTeam;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.model.HelpPages;
import me.protocos.xteam.util.MessageUtil;
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
		pages.setTitle(ChatColor.AQUA + "Console Commands: " + MessageUtil.highlightString(ChatColor.GRAY, "{optional} [required] pick/one"));
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
		pages.addLines(XTeam.getInstance().getCommandManager().getAvailableCommandsFor(sender));
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
