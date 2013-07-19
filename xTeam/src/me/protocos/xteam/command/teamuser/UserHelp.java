package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.util.StringUtil.*;
import java.io.InvalidClassException;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.exception.*;
import me.protocos.xteam.util.HelpPages;
import me.protocos.xteam.util.PermissionUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class UserHelp extends UserCommand
{
	private HelpPages pages;
	private int pageNum;

	public UserHelp()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		pages.setTitle(ChatColor.AQUA + "Team Commands: [Page " + pageNum + "/" + pages.getTotalPages() + "]" + " " + ChatColor.RED + "{" + ChatColor.GRAY + "optional" + ChatColor.RED + "}" + ChatColor.GRAY + " " + ChatColor.RED + "[" + ChatColor.GRAY + "required" + ChatColor.RED + "]" + ChatColor.GRAY + " pick" + ChatColor.RED + "/" + ChatColor.GRAY + "one");
		pageNum--;
		originalSender.sendMessage(pages.getPage(pageNum));
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, InvalidClassException
	{
		super.checkRequirements(originalSender, parseCommand);
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
		pages = new HelpPages();
		String temp = "user_info";
		pages.addLine(ChatColor.GRAY + xTeam.cm.getUsage(temp) + " - Get team info or other team's info");
		if (PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(temp = "user_list")))
			pages.addLine(ChatColor.GRAY + xTeam.cm.getUsage(temp) + " - UserList all teams on the server");
		if (PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(temp = "user_create")))
			pages.addLine(ChatColor.GRAY + xTeam.cm.getUsage(temp) + " - UserCreate a team");
		if (PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(temp = "user_join")))
			pages.addLine(ChatColor.GRAY + xTeam.cm.getUsage(temp) + " - UserJoin a team");
		if (PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(temp = "user_leave")))
			pages.addLine(ChatColor.GRAY + xTeam.cm.getUsage(temp) + " - UserLeave your team");
		if (PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(temp = "user_accept")))
			pages.addLine(ChatColor.GRAY + xTeam.cm.getUsage(temp) + " - UserAccept the most recent team invite");
		if (PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(temp = "user_hq")))
			pages.addLine(ChatColor.GRAY + xTeam.cm.getUsage(temp) + " - UserTeleport to the team headquarters");
		if (PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(temp = "user_tele")))
			pages.addLine(ChatColor.GRAY + xTeam.cm.getUsage(temp) + " - UserTeleport to nearest or specific teammate");
		if (PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(temp = "user_return")))
			pages.addLine(ChatColor.GRAY + xTeam.cm.getUsage(temp) + " - UserTeleport to saved return location (1 use)");
		if (PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(temp = "user_chat")))
			pages.addLine(ChatColor.GRAY + xTeam.cm.getUsage(temp) + " - Toggle chatting with teammates");
		if (PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(temp = "user_chat")))
			pages.addLine(ChatColor.GRAY + xTeam.cm.getUsage("user_message") + " - Send message to teammates");
		if (Data.LOCATIONS_ENABLED && PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(temp = "user_listloc")))
			pages.addLine(ChatColor.GRAY + xTeam.cm.getUsage(temp) + " - UserList team locations");
		if (Data.LOCATIONS_ENABLED && PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(temp = "user_trackloc")))
			pages.addLine(ChatColor.GRAY + xTeam.cm.getUsage(temp) + " - Track team location");
		if (teamPlayer.isAdmin())
		{
			if (Data.LOCATIONS_ENABLED && PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(temp = "admin_addloc")))
				pages.addLine(ChatColor.YELLOW + xTeam.cm.getUsage(temp) + " - Add a new location");
			if (Data.LOCATIONS_ENABLED && PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(temp = "admin_removeloc")))
				pages.addLine(ChatColor.YELLOW + xTeam.cm.getUsage(temp) + " - UserRemove a location");
			if (PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(temp = "admin_sethq")))
				pages.addLine(ChatColor.YELLOW + xTeam.cm.getUsage(temp) + " - Set headquarters of team" + (Data.HQ_INTERVAL > 0 ? " (every " + Data.HQ_INTERVAL + " hours)" : ""));
			if (PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(temp = "admin_invite")))
				pages.addLine(ChatColor.YELLOW + xTeam.cm.getUsage(temp) + " - UserInvite sender to your team");
			if (PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(temp = "admin_promote")))
				pages.addLine(ChatColor.YELLOW + xTeam.cm.getUsage(temp) + " - UserPromote sender on your team");
		}
		if (teamPlayer.isLeader())
		{
			if (PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(temp = "leader_demote")))
				pages.addLine(ChatColor.LIGHT_PURPLE + xTeam.cm.getUsage(temp) + " - UserDemote sender on your team");
			if (PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(temp = "leader_disband")))
				pages.addLine(ChatColor.LIGHT_PURPLE + xTeam.cm.getUsage(temp) + " - UserDisband the team");
			if (PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(temp = "leader_open")))
				pages.addLine(ChatColor.LIGHT_PURPLE + xTeam.cm.getUsage(temp) + " - UserOpen team to public joining");
			if (PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(temp = "leader_remove")))
				pages.addLine(ChatColor.LIGHT_PURPLE + xTeam.cm.getUsage(temp) + " - UserRemove sender from your team");
			if (PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(temp = "leader_rename")))
				pages.addLine(ChatColor.LIGHT_PURPLE + xTeam.cm.getUsage(temp) + " - UserRename the team");
			if (PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(temp = "leader_tag")))
				pages.addLine(ChatColor.LIGHT_PURPLE + xTeam.cm.getUsage(temp) + " - Set the team tag");
			if (PermissionUtil.hasPermission(originalSender, xTeam.cm.getPermissionNode(temp = "leader_setleader")))
				pages.addLine(ChatColor.LIGHT_PURPLE + xTeam.cm.getUsage(temp) + " - Set new leader for the team");
		}
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
		return "(((" + patternOneOrMore("help") + "|\\?+)(" + WHITE_SPACE + NUMBERS + ")?)?" + "|" + NUMBERS + ")" + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		//TODO needs to not return null
		return null;
	}
	@Override
	public String getUsage()
	{
		return "/team help {Page}";
	}
}
