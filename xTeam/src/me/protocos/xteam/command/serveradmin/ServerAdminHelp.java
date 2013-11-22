package me.protocos.xteam.command.serveradmin;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ServerAdminCommand;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.HelpPages;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.ChatColor;

public class ServerAdminHelp extends ServerAdminCommand
{
	private HelpPages pages;
	private int pageNum;

	public ServerAdminHelp()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		pages.setTitle(ChatColor.AQUA + "Admin Commands: [Page " + pageNum + "/" + pages.getTotalPages() + "] " + ChatColorUtil.highlightString(ChatColor.GRAY, "{optional} [required] pick/one"));
		pageNum--;
		player.sendMessage(pages.getPage(pageNum));
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		if (commandContainer.size() == 1 && commandContainer.getArgument(0).matches(new PatternBuilder().numbers().toString()))
		{
			pageNum = Integer.parseInt(commandContainer.getArgument(0));
		}
		else if (commandContainer.size() == 2 && commandContainer.getArgument(1).matches(new PatternBuilder().numbers().toString()))
		{
			pageNum = Integer.parseInt(commandContainer.getArgument(1));
		}
		else if (commandContainer.size() == 1)
		{
			pageNum = 1;
		}
		pages = new HelpPages();
		pages.addLines(xTeam.getInstance().getCommandManager().getAvailableAdminCommandsFor(teamPlayer));
		//		String command;
		//		if (PermissionUtil.hasPermission(player, xTeam.getInstance().getCommandManager().getPermissionNode(command = "serveradmin_set")))
		//			pages.addLine(ChatColorUtil.formatForUser(xTeam.getInstance().getCommandManager().getUsage(command) + " - <admin> set team of player"));
		//		if (PermissionUtil.hasPermission(player, xTeam.getInstance().getCommandManager().getPermissionNode(command = "serveradmin_hq")))
		//			pages.addLine(ChatColorUtil.formatForUser(xTeam.getInstance().getCommandManager().getUsage(command) + " - <admin> teleport to team headquarters"));
		//		if (PermissionUtil.hasPermission(player, xTeam.getInstance().getCommandManager().getPermissionNode(command = "serveradmin_sethq")))
		//			pages.addLine(ChatColorUtil.formatForUser(xTeam.getInstance().getCommandManager().getUsage(command) + " - <admin> set team headquarters for team"));
		//		if (PermissionUtil.hasPermission(player, xTeam.getInstance().getCommandManager().getPermissionNode(command = "serveradmin_setleader")))
		//			pages.addLine(ChatColorUtil.formatForUser(xTeam.getInstance().getCommandManager().getUsage(command) + " - <admin> set leader of team"));
		//		if (PermissionUtil.hasPermission(player, xTeam.getInstance().getCommandManager().getPermissionNode(command = "serveradmin_promote")))
		//			pages.addLine(ChatColorUtil.formatForUser(xTeam.getInstance().getCommandManager().getUsage(command) + " - <admin> promote player to team admin"));
		//		if (PermissionUtil.hasPermission(player, xTeam.getInstance().getCommandManager().getPermissionNode(command = "serveradmin_demote")))
		//			pages.addLine(ChatColorUtil.formatForUser(xTeam.getInstance().getCommandManager().getUsage(command) + " - <admin> demote team admin"));
		//		if (PermissionUtil.hasPermission(player, xTeam.getInstance().getCommandManager().getPermissionNode(command = "serveradmin_remove")))
		//			pages.addLine(ChatColorUtil.formatForUser(xTeam.getInstance().getCommandManager().getUsage(command) + " - <admin> remove player of team"));
		//		if (PermissionUtil.hasPermission(player, xTeam.getInstance().getCommandManager().getPermissionNode(command = "serveradmin_teleallhq")))
		//			pages.addLine(ChatColorUtil.formatForUser(xTeam.getInstance().getCommandManager().getUsage(command) + " - <admin> teleports everyone to their HQ"));
		//		if (PermissionUtil.hasPermission(player, xTeam.getInstance().getCommandManager().getPermissionNode(command = "serveradmin_tpall")))
		//			pages.addLine(ChatColorUtil.formatForUser(xTeam.getInstance().getCommandManager().getUsage(command) + " - <admin> teleports team to yourself"));
		//		if (PermissionUtil.hasPermission(player, xTeam.getInstance().getCommandManager().getPermissionNode(command = "serveradmin_chatspy")))
		//			pages.addLine(ChatColorUtil.formatForUser(xTeam.getInstance().getCommandManager().getUsage(command) + " - <admin> spy on team chat"));
		//		if (PermissionUtil.hasPermission(player, xTeam.getInstance().getCommandManager().getPermissionNode(command = "serveradmin_rename")))
		//			pages.addLine(ChatColorUtil.formatForUser(xTeam.getInstance().getCommandManager().getUsage(command) + " - <admin> rename a team"));
		//		if (PermissionUtil.hasPermission(player, xTeam.getInstance().getCommandManager().getPermissionNode(command = "serveradmin_tag")))
		//			pages.addLine(ChatColorUtil.formatForUser(xTeam.getInstance().getCommandManager().getUsage(command) + " - <admin> set team tag"));
		//		if (PermissionUtil.hasPermission(player, xTeam.getInstance().getCommandManager().getPermissionNode(command = "serveradmin_disband")))
		//			pages.addLine(ChatColorUtil.formatForUser(xTeam.getInstance().getCommandManager().getUsage(command) + " - <admin> disband a team"));
		//		if (PermissionUtil.hasPermission(player, xTeam.getInstance().getCommandManager().getPermissionNode(command = "serveradmin_open")))
		//			pages.addLine(ChatColorUtil.formatForUser(xTeam.getInstance().getCommandManager().getUsage(command) + " - <admin> open team to public joining"));
		//		if (PermissionUtil.hasPermission(player, xTeam.getInstance().getCommandManager().getPermissionNode(command = "serveradmin_reload")))
		//			pages.addLine(ChatColorUtil.formatForUser(xTeam.getInstance().getCommandManager().getUsage(command) + " - <admin> reload the config files"));
		Requirements.checkPlayerHasCommands(pages);
		Requirements.checkPlayerCommandPageRange(pages, pageNum);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("admin")
				.optional(new PatternBuilder().whiteSpace().oneOrMore("help"))
				.optional(new PatternBuilder()
						.whiteSpace()
						.numbers())
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "help";
	}

	@Override
	public String getUsage()
	{
		return "/team admin {Page}";
	}

	@Override
	public String getDescription()
	{
		return "server admin help menu for xTeam";
	}
}
