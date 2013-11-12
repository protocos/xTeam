package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.util.StringUtil.*;
import java.util.List;
import me.protocos.xteam.xTeamPlugin;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.HelpPages;
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
		pages.setTitle(ChatColor.AQUA + "Team Commands: [Page " + pageNum + "/" + pages.getTotalPages() + "] " + ChatColorUtil.highlightString(ChatColor.GRAY, "{optional} [required] pick/one"));
		pageNum--;
		originalSender.sendMessage(pages.getPage(pageNum));
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
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
		List<String> availableCommands = xTeamPlugin.getInstance().getCommandManager().getAvailableCommandsFor(teamPlayer);
		pages.addLines(availableCommands);
		//		String temp = "user_info";
		//		pages.addLine(ChatColorUtil.formatForUser(xTeamPlugin.getInstance().getCommandManager().getUsage(temp) + " - Get team info or other team's info"));
		//		if (PermissionUtil.hasPermission(originalSender, xTeamPlugin.getInstance().getCommandManager().getPermissionNode(temp = "user_list")))
		//			pages.addLine(ChatColorUtil.formatForUser(xTeamPlugin.getInstance().getCommandManager().getUsage(temp) + " - List all teams on the server"));
		//		if (PermissionUtil.hasPermission(originalSender, xTeamPlugin.getInstance().getCommandManager().getPermissionNode(temp = "user_create")))
		//			pages.addLine(ChatColorUtil.formatForUser(xTeamPlugin.getInstance().getCommandManager().getUsage(temp) + " - Create a team"));
		//		if (PermissionUtil.hasPermission(originalSender, xTeamPlugin.getInstance().getCommandManager().getPermissionNode(temp = "user_join")))
		//			pages.addLine(ChatColorUtil.formatForUser(xTeamPlugin.getInstance().getCommandManager().getUsage(temp) + " - Join a team"));
		//		if (PermissionUtil.hasPermission(originalSender, xTeamPlugin.getInstance().getCommandManager().getPermissionNode(temp = "user_leave")))
		//			pages.addLine(ChatColorUtil.formatForUser(xTeamPlugin.getInstance().getCommandManager().getUsage(temp) + " - Leave your team"));
		//		if (PermissionUtil.hasPermission(originalSender, xTeamPlugin.getInstance().getCommandManager().getPermissionNode(temp = "user_accept")))
		//			pages.addLine(ChatColorUtil.formatForUser(xTeamPlugin.getInstance().getCommandManager().getUsage(temp) + " - Accept the most recent team invite"));
		//		if (PermissionUtil.hasPermission(originalSender, xTeamPlugin.getInstance().getCommandManager().getPermissionNode(temp = "user_hq")))
		//			pages.addLine(ChatColorUtil.formatForUser(xTeamPlugin.getInstance().getCommandManager().getUsage(temp) + " - Teleport to the team headquarters"));
		//		if (PermissionUtil.hasPermission(originalSender, xTeamPlugin.getInstance().getCommandManager().getPermissionNode(temp = "user_tele")))
		//			pages.addLine(ChatColorUtil.formatForUser(xTeamPlugin.getInstance().getCommandManager().getUsage(temp) + " - Teleport to nearest or specific teammate"));
		//		if (PermissionUtil.hasPermission(originalSender, xTeamPlugin.getInstance().getCommandManager().getPermissionNode(temp = "user_return")))
		//			pages.addLine(ChatColorUtil.formatForUser(xTeamPlugin.getInstance().getCommandManager().getUsage(temp) + " - Teleport to saved return location (1 use)"));
		//		if (PermissionUtil.hasPermission(originalSender, xTeamPlugin.getInstance().getCommandManager().getPermissionNode(temp = "user_chat")))
		//			pages.addLine(ChatColorUtil.formatForUser(xTeamPlugin.getInstance().getCommandManager().getUsage(temp) + " - Toggle chatting with teammates"));
		//		if (PermissionUtil.hasPermission(originalSender, xTeamPlugin.getInstance().getCommandManager().getPermissionNode(temp = "user_chat")))
		//			pages.addLine(ChatColorUtil.formatForUser(xTeamPlugin.getInstance().getCommandManager().getUsage("user_message") + " - Send message to teammates"));
		//		if (Data.LOCATIONS_ENABLED && PermissionUtil.hasPermission(originalSender, xTeamPlugin.getInstance().getCommandManager().getPermissionNode(temp = "user_listloc")))
		//			pages.addLine(ChatColorUtil.formatForUser(xTeamPlugin.getInstance().getCommandManager().getUsage(temp) + " - List team locations"));
		//		if (Data.LOCATIONS_ENABLED && PermissionUtil.hasPermission(originalSender, xTeamPlugin.getInstance().getCommandManager().getPermissionNode(temp = "user_trackloc")))
		//			pages.addLine(ChatColorUtil.formatForUser(xTeamPlugin.getInstance().getCommandManager().getUsage(temp) + " - Track team location"));
		//		if (teamPlayer.isAdmin())
		//		{
		//			if (Data.LOCATIONS_ENABLED && PermissionUtil.hasPermission(originalSender, xTeamPlugin.getInstance().getCommandManager().getPermissionNode(temp = "admin_addloc")))
		//				pages.addLine(ChatColorUtil.formatForAdmin(xTeamPlugin.getInstance().getCommandManager().getUsage(temp) + " - Add a new location"));
		//			if (Data.LOCATIONS_ENABLED && PermissionUtil.hasPermission(originalSender, xTeamPlugin.getInstance().getCommandManager().getPermissionNode(temp = "admin_removeloc")))
		//				pages.addLine(ChatColorUtil.formatForAdmin(xTeamPlugin.getInstance().getCommandManager().getUsage(temp) + " - Remove a location"));
		//			if (PermissionUtil.hasPermission(originalSender, xTeamPlugin.getInstance().getCommandManager().getPermissionNode(temp = "admin_sethq")))
		//				pages.addLine(ChatColorUtil.formatForAdmin(xTeamPlugin.getInstance().getCommandManager().getUsage(temp) + " - Set headquarters of team" + (Data.HQ_INTERVAL > 0 ? " (every " + Data.HQ_INTERVAL + " hours)" : "")));
		//			if (PermissionUtil.hasPermission(originalSender, xTeamPlugin.getInstance().getCommandManager().getPermissionNode(temp = "admin_invite")))
		//				pages.addLine(ChatColorUtil.formatForAdmin(xTeamPlugin.getInstance().getCommandManager().getUsage(temp) + " - Invite player to your team"));
		//			if (PermissionUtil.hasPermission(originalSender, xTeamPlugin.getInstance().getCommandManager().getPermissionNode(temp = "admin_promote")))
		//				pages.addLine(ChatColorUtil.formatForAdmin(xTeamPlugin.getInstance().getCommandManager().getUsage(temp) + " - Promote player on your team"));
		//		}
		//		if (teamPlayer.isLeader())
		//		{
		//			if (PermissionUtil.hasPermission(originalSender, xTeamPlugin.getInstance().getCommandManager().getPermissionNode(temp = "leader_demote")))
		//				pages.addLine(ChatColorUtil.formatForLeader(xTeamPlugin.getInstance().getCommandManager().getUsage(temp) + " - Demote player on your team"));
		//			if (PermissionUtil.hasPermission(originalSender, xTeamPlugin.getInstance().getCommandManager().getPermissionNode(temp = "leader_disband")))
		//				pages.addLine(ChatColorUtil.formatForLeader(xTeamPlugin.getInstance().getCommandManager().getUsage(temp) + " - Disband the team"));
		//			if (PermissionUtil.hasPermission(originalSender, xTeamPlugin.getInstance().getCommandManager().getPermissionNode(temp = "leader_open")))
		//				pages.addLine(ChatColorUtil.formatForLeader(xTeamPlugin.getInstance().getCommandManager().getUsage(temp) + " - Open team to public joining"));
		//			if (PermissionUtil.hasPermission(originalSender, xTeamPlugin.getInstance().getCommandManager().getPermissionNode(temp = "leader_remove")))
		//				pages.addLine(ChatColorUtil.formatForLeader(xTeamPlugin.getInstance().getCommandManager().getUsage(temp) + " - Remove player from your team"));
		//			if (PermissionUtil.hasPermission(originalSender, xTeamPlugin.getInstance().getCommandManager().getPermissionNode(temp = "leader_rename")))
		//				pages.addLine(ChatColorUtil.formatForLeader(xTeamPlugin.getInstance().getCommandManager().getUsage(temp) + " - Rename the team"));
		//			if (PermissionUtil.hasPermission(originalSender, xTeamPlugin.getInstance().getCommandManager().getPermissionNode(temp = "leader_tag")))
		//				pages.addLine(ChatColorUtil.formatForLeader(xTeamPlugin.getInstance().getCommandManager().getUsage(temp) + " - Set the team tag"));
		//			if (PermissionUtil.hasPermission(originalSender, xTeamPlugin.getInstance().getCommandManager().getPermissionNode(temp = "leader_setleader")))
		//				pages.addLine(ChatColorUtil.formatForLeader(xTeamPlugin.getInstance().getCommandManager().getUsage(temp) + " - Set new leader for the team"));
		//		}
		Requirements.checkPlayerHasCommands(pages);
		Requirements.checkPlayerCommandPageRange(pages, pageNum);
	}

	@Override
	public String getPattern()
	{
		return "(((" + patternOneOrMore("help") + "|\\?+)" + WHITE_SPACE + NUMBERS + ")" + "|" + NUMBERS + ")" + OPTIONAL_WHITE_SPACE;
	}

	@Override
	public String getPermissionNode()
	{
		return "help";
	}

	@Override
	public String getUsage()
	{
		return "/team {help} [Page]";
	}

	@Override
	public String getDescription()
	{
		return "user help menu for xTeamPlugin";
	}
}
