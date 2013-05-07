package me.protocos.xteam.command.console;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseCommand;
import me.protocos.xteam.command.BaseConsoleCommand;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.core.exception.TeamInvalidCommandException;
import me.protocos.xteam.util.HelpPages;
import org.bukkit.command.ConsoleCommandSender;

public class ConsoleHelp extends BaseConsoleCommand
{
	private HelpPages pages;

	public ConsoleHelp()
	{
		super();
	}

	public ConsoleHelp(ConsoleCommandSender sender, String command, String commandID)
	{
		super(sender, command);
		BaseCommand.baseCommand = commandID;
		pages = new HelpPages();
	}
	@Override
	protected void act()
	{
		pages.setTitle("Console Commands: {optional} [required] pick/one");
		pages.addLine(xTeam.cm.getUsage("console_info") + " - get info on teamPlayer/team");
		pages.addLine(xTeam.cm.getUsage("console_list") + " - list all teams on the server");
		pages.addLine(xTeam.cm.getUsage("console_set") + " - set team of teamPlayer");
		pages.addLine(xTeam.cm.getUsage("console_setleader") + " - set leader of team");
		pages.addLine(xTeam.cm.getUsage("console_promote") + " - promote admin of team");
		pages.addLine(xTeam.cm.getUsage("console_demote") + " - demote admin of team");
		pages.addLine(xTeam.cm.getUsage("console_remove") + " - remove member of team");
		pages.addLine(xTeam.cm.getUsage("console_rename") + " - rename a team");
		pages.addLine(xTeam.cm.getUsage("console_tag") + " - set team tag");
		pages.addLine(xTeam.cm.getUsage("console_disband") + " - disband a team");
		pages.addLine(xTeam.cm.getUsage("console_open") + " - open team to public joining");
		pages.addLine(xTeam.cm.getUsage("console_teleallhq") + " - teleports everyone to their Headquarters");
		pages.addLine(xTeam.cm.getUsage("console_reload") + " - reloads the configuration file");
		originalSender.sendMessage(pages.getTitle());
		for (int index = 0; index < pages.size(); index++)
		{
			originalSender.sendMessage(pages.get(index));
		}
	}
	@Override
	public void checkRequirements() throws TeamException
	{
		if (parseCommand.size() == 0 || parseCommand.size() == 1)
		{
		}
		else
		{
			throw new TeamInvalidCommandException();
		}
	}
	@Override
	public String getPattern()
	{
		return "(" + patternOneOrMore("help") + "|\\?+)?" + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getUsage()
	{
		return baseCommand + " {help}";
	}
}
