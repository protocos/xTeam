package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseCommand;
import me.protocos.xteam.command.BaseServerAdminCommand;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.util.HelpPages;
import me.protocos.xteam.util.PermissionUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class AdminHelp extends BaseServerAdminCommand
{
	private HelpPages pages;
	private int pageNum;

	public AdminHelp()
	{
		super();
	}
	public AdminHelp(Player sender, String command, String commandID)
	{
		super(sender, command);
		BaseCommand.baseCommand = commandID;
		pages = new HelpPages();
	}
	@Override
	protected void act()
	{
		pages.setTitle(ChatColor.GREEN + "Admin Commands: [Page " + pageNum + "/" + pages.getTotalPages() + "]" + " " + ChatColor.RED + "{" + ChatColor.GRAY + "optional" + ChatColor.RED + "}" + ChatColor.GRAY + " " + ChatColor.RED + "[" + ChatColor.GRAY + "required" + ChatColor.RED + "]" + ChatColor.GRAY + " pick" + ChatColor.RED + "/" + ChatColor.GRAY + "one");
		pageNum--;
		originalSender.sendMessage(pages.getPage(pageNum));
	}
	@Override
	public void checkRequirements() throws TeamException
	{
		if (player == null)
		{
			throw new TeamPlayerDoesNotExistException();
		}
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
		else
		{
			throw new TeamInvalidCommandException();
		}
		String command;
		if (PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(command = "serveradmin_set")))
			pages.addLine(ChatColor.RED + xTeam.cm.getUsage(command) + " - <admin> set team of teamPlayer");
		if (PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(command = "serveradmin_hq")))
			pages.addLine(ChatColor.RED + xTeam.cm.getUsage(command) + " - <admin> teleport to team headquarters");
		if (PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(command = "serveradmin_sethq")))
			pages.addLine(ChatColor.RED + xTeam.cm.getUsage(command) + " - <admin> set team headquarters for team");
		if (PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(command = "serveradmin_setleader")))
			pages.addLine(ChatColor.RED + xTeam.cm.getUsage(command) + " - <admin> set leader of team");
		if (PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(command = "serveradmin_promote")))
			pages.addLine(ChatColor.RED + xTeam.cm.getUsage(command) + " - <admin> promote admin of team");
		if (PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(command = "serveradmin_demote")))
			pages.addLine(ChatColor.RED + xTeam.cm.getUsage(command) + " - <admin> demote admin of team");
		if (PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(command = "serveradmin_remove")))
			pages.addLine(ChatColor.RED + xTeam.cm.getUsage(command) + " - <admin> remove player of team");
		if (PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(command = "serveradmin_teleallhq")))
			pages.addLine(ChatColor.RED + xTeam.cm.getUsage(command) + " - <admin> teleports everyone to their HQ");
		if (PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(command = "serveradmin_tpall")))
			pages.addLine(ChatColor.RED + xTeam.cm.getUsage(command) + " - <admin> teleports team to yourself");
		if (PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(command = "serveradmin_chatspy")))
			pages.addLine(ChatColor.RED + xTeam.cm.getUsage(command) + " - <admin> spy on team chat");
		if (PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(command = "serveradmin_rename")))
			pages.addLine(ChatColor.RED + xTeam.cm.getUsage(command) + " - <admin> rename a team");
		if (PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(command = "serveradmin_tag")))
			pages.addLine(ChatColor.RED + xTeam.cm.getUsage(command) + " - <admin> set team tag");
		if (PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(command = "serveradmin_disband")))
			pages.addLine(ChatColor.RED + xTeam.cm.getUsage(command) + " - <admin> disband a team");
		if (PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(command = "serveradmin_open")))
			pages.addLine(ChatColor.RED + xTeam.cm.getUsage(command) + " - <admin> open team to public joining");
		if (PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(command = "serveradmin_reload")))
			pages.addLine(ChatColor.RED + xTeam.cm.getUsage(command) + " - <admin> reload the config files");
		if (pages.getTotalPages() == 0)
		{
			throw new TeamPlayerPermissionException();
		}
		if (pageNum > pages.getTotalPages())
		{
			throw new TeamInvalidPageException();
		}
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("admin") + "(" + WHITE_SPACE + NUMBERS + "|" + WHITE_SPACE + patternOneOrMore("help") + ")?" + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return null;
	}
	@Override
	public String getUsage()
	{
		return baseCommand + " admin";
	}
}
