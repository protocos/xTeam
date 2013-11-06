package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.HelpPages;
import me.protocos.xteam.util.PermissionUtil;
import me.protocos.xteam.util.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class AdminHelp extends ServerAdminCommand
{
	private HelpPages pages;
	private int pageNum;

	public AdminHelp()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		pages.setTitle(ChatColor.AQUA + "Admin Commands: [Page " + pageNum + "/" + pages.getTotalPages() + "] " + ChatColorUtil.highlightString(ChatColor.GRAY, "{optional} [required] pick/one"));
		pageNum--;
		originalSender.sendMessage(pages.getPage(pageNum));
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		super.checkRequirements(originalSender, parseCommand);
		if (parseCommand.size() == 1 && parseCommand.get(0).matches(StringUtil.NUMBERS))
		{
			pageNum = Integer.parseInt(parseCommand.get(0));
		}
		else if (parseCommand.size() == 2 && parseCommand.get(1).matches(StringUtil.NUMBERS))
		{
			pageNum = Integer.parseInt(parseCommand.get(1));
		}
		else if (parseCommand.size() == 1)
		{
			pageNum = 1;
		}
		pages = new HelpPages();
		String command;
		if (PermissionUtil.hasPermission(originalSender, xTeam.getCommandManager().getPermissionNode(command = "serveradmin_set")))
			pages.addLine(ChatColorUtil.formatForUser(xTeam.getCommandManager().getUsage(command) + " - <admin> set team of player"));
		if (PermissionUtil.hasPermission(originalSender, xTeam.getCommandManager().getPermissionNode(command = "serveradmin_hq")))
			pages.addLine(ChatColorUtil.formatForUser(xTeam.getCommandManager().getUsage(command) + " - <admin> teleport to team headquarters"));
		if (PermissionUtil.hasPermission(originalSender, xTeam.getCommandManager().getPermissionNode(command = "serveradmin_sethq")))
			pages.addLine(ChatColorUtil.formatForUser(xTeam.getCommandManager().getUsage(command) + " - <admin> set team headquarters for team"));
		if (PermissionUtil.hasPermission(originalSender, xTeam.getCommandManager().getPermissionNode(command = "serveradmin_setleader")))
			pages.addLine(ChatColorUtil.formatForUser(xTeam.getCommandManager().getUsage(command) + " - <admin> set leader of team"));
		if (PermissionUtil.hasPermission(originalSender, xTeam.getCommandManager().getPermissionNode(command = "serveradmin_promote")))
			pages.addLine(ChatColorUtil.formatForUser(xTeam.getCommandManager().getUsage(command) + " - <admin> promote admin of team"));
		if (PermissionUtil.hasPermission(originalSender, xTeam.getCommandManager().getPermissionNode(command = "serveradmin_demote")))
			pages.addLine(ChatColorUtil.formatForUser(xTeam.getCommandManager().getUsage(command) + " - <admin> demote admin of team"));
		if (PermissionUtil.hasPermission(originalSender, xTeam.getCommandManager().getPermissionNode(command = "serveradmin_remove")))
			pages.addLine(ChatColorUtil.formatForUser(xTeam.getCommandManager().getUsage(command) + " - <admin> remove player of team"));
		if (PermissionUtil.hasPermission(originalSender, xTeam.getCommandManager().getPermissionNode(command = "serveradmin_teleallhq")))
			pages.addLine(ChatColorUtil.formatForUser(xTeam.getCommandManager().getUsage(command) + " - <admin> teleports everyone to their HQ"));
		if (PermissionUtil.hasPermission(originalSender, xTeam.getCommandManager().getPermissionNode(command = "serveradmin_tpall")))
			pages.addLine(ChatColorUtil.formatForUser(xTeam.getCommandManager().getUsage(command) + " - <admin> teleports team to yourself"));
		if (PermissionUtil.hasPermission(originalSender, xTeam.getCommandManager().getPermissionNode(command = "serveradmin_chatspy")))
			pages.addLine(ChatColorUtil.formatForUser(xTeam.getCommandManager().getUsage(command) + " - <admin> spy on team chat"));
		if (PermissionUtil.hasPermission(originalSender, xTeam.getCommandManager().getPermissionNode(command = "serveradmin_rename")))
			pages.addLine(ChatColorUtil.formatForUser(xTeam.getCommandManager().getUsage(command) + " - <admin> rename a team"));
		if (PermissionUtil.hasPermission(originalSender, xTeam.getCommandManager().getPermissionNode(command = "serveradmin_tag")))
			pages.addLine(ChatColorUtil.formatForUser(xTeam.getCommandManager().getUsage(command) + " - <admin> set team tag"));
		if (PermissionUtil.hasPermission(originalSender, xTeam.getCommandManager().getPermissionNode(command = "serveradmin_disband")))
			pages.addLine(ChatColorUtil.formatForUser(xTeam.getCommandManager().getUsage(command) + " - <admin> disband a team"));
		if (PermissionUtil.hasPermission(originalSender, xTeam.getCommandManager().getPermissionNode(command = "serveradmin_open")))
			pages.addLine(ChatColorUtil.formatForUser(xTeam.getCommandManager().getUsage(command) + " - <admin> open team to public joining"));
		if (PermissionUtil.hasPermission(originalSender, xTeam.getCommandManager().getPermissionNode(command = "serveradmin_reload")))
			pages.addLine(ChatColorUtil.formatForUser(xTeam.getCommandManager().getUsage(command) + " - <admin> reload the config files"));
		Requirements.checkPlayerHasCommands(pages);
		Requirements.checkPlayerCommandPageRange(pages, pageNum);
	}

	@Override
	public String getPattern()
	{
		return patternOneOrMore("admin") + "(" + WHITE_SPACE + patternOneOrMore("help") + ")?" + "(" + WHITE_SPACE + NUMBERS + ")?" + OPTIONAL_WHITE_SPACE;
	}

	@Override
	public String getPermissionNode()
	{
		//TODO probably should not return null
		return null;
	}

	@Override
	public String getUsage()
	{
		return "/team admin {Page}";
	}
}
