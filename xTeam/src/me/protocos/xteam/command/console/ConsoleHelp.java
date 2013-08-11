package me.protocos.xteam.command.console;

import static me.protocos.xteam.util.StringUtil.*;
import java.io.InvalidClassException;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.HelpPages;
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
		pages.setTitle("Console Commands: {optional} [required] pick/one");
		pages.addLine(xTeam.cm.getUsage("console_info") + " - get info on sender/team");
		pages.addLine(xTeam.cm.getUsage("console_list") + " - list all teams on the server");
		pages.addLine(xTeam.cm.getUsage("console_set") + " - set team of sender");
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
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, InvalidClassException
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
}
