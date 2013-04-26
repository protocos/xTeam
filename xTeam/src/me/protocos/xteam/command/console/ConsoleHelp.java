package me.protocos.xteam.command.console;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.command.BaseCommand;
import me.protocos.xteam.command.BaseConsoleCommand;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.exception.TeamInvalidCommandException;
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
		pages.addLine(BaseCommand.baseCommand + " info [Player/ITeam] - get info on teamPlayer/team");
		pages.addLine(BaseCommand.baseCommand + " list - list all teams on the server");
		pages.addLine(BaseCommand.baseCommand + " set [Player] [ITeam] - set team of teamPlayer");
		pages.addLine(BaseCommand.baseCommand + " setleader [ITeam] [Player] - set leader of team");
		pages.addLine(BaseCommand.baseCommand + " promote [ITeam] [Player] - promote admin of team");
		pages.addLine(BaseCommand.baseCommand + " demote [ITeam] [Player] - demote admin of team");
		pages.addLine(BaseCommand.baseCommand + " rename [ITeam] [Name] - rename a team");
		pages.addLine(BaseCommand.baseCommand + " tag [ITeam] [Tag] - set team tag");
		pages.addLine(BaseCommand.baseCommand + " open [ITeam] - open team to public joining");
		pages.addLine(BaseCommand.baseCommand + " teleallhq - teleports everyone to their Headquarters");
		pages.addLine(BaseCommand.baseCommand + " delete - deletes a team");
		pages.addLine(BaseCommand.baseCommand + " reload - reloads the configuration file");
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