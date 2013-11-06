package me.protocos.xteam.command.console;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.HelpPages;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ConsoleHelp extends ConsoleCommand
{
	private HelpPages pages;

	public ConsoleHelp()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		pages = new HelpPages();
		pages.setTitle(ChatColor.AQUA + "Console Commands: " + ChatColorUtil.highlightString(ChatColor.GRAY, "{optional} [required] pick/one"));
		pages.addLine(format(xTeam.getCommandManager().getUsage("console_info") + " - get info on player/team"));
		pages.addLine(format(xTeam.getCommandManager().getUsage("console_list") + " - list all teams on the server"));
		pages.addLine(format(xTeam.getCommandManager().getUsage("console_set") + " - set team of player"));
		pages.addLine(format(xTeam.getCommandManager().getUsage("console_setleader") + " - set leader of team"));
		pages.addLine(format(xTeam.getCommandManager().getUsage("console_promote") + " - promote admin of team"));
		pages.addLine(format(xTeam.getCommandManager().getUsage("console_demote") + " - demote admin of team"));
		pages.addLine(format(xTeam.getCommandManager().getUsage("console_remove") + " - remove member of team"));
		pages.addLine(format(xTeam.getCommandManager().getUsage("console_rename") + " - rename a team"));
		pages.addLine(format(xTeam.getCommandManager().getUsage("console_tag") + " - set team tag"));
		pages.addLine(format(xTeam.getCommandManager().getUsage("console_disband") + " - disband a team"));
		pages.addLine(format(xTeam.getCommandManager().getUsage("console_open") + " - open team to public joining"));
		pages.addLine(format(xTeam.getCommandManager().getUsage("console_teleallhq") + " - teleports everyone to their Headquarters"));
		pages.addLine(format(xTeam.getCommandManager().getUsage("console_reload") + " - reloads the configuration file"));
		originalSender.sendMessage(pages.getTitle());
		for (int index = 0; index < pages.getNumLines(); index++)
		{
			originalSender.sendMessage(pages.getLine(index));
		}
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		super.checkRequirements(originalSender, parseCommand);
	}

	@Override
	public String getPattern()
	{
		return "(" + patternOneOrMore("help") + "|\\?+)?" + OPTIONAL_WHITE_SPACE;
	}

	@Override
	public String getUsage()
	{
		return "/team {help}";
	}

	private String format(String text)
	{
		return ChatColorUtil.highlightString(ChatColor.GRAY, text);
	}
}
