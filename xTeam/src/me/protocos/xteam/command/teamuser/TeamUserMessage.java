package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.api.command.TeamUserCommand;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.ChatColor;

public class TeamUserMessage extends TeamUserCommand
{
	public TeamUserMessage()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		String message = "";
		for (int i = 1; i < commandContainer.size(); i++)
		{
			message += " " + commandContainer.getArgument(i);
		}
		for (ITeamPlayer teammate : teamPlayer.getOnlineTeammates())
		{
			teammate.sendMessage("[" + ChatColor.DARK_GREEN + teamPlayer.getName() + ChatColor.RESET + "]" + message);
		}
		teamPlayer.sendMessage("[" + ChatColor.DARK_GREEN + teamPlayer.getName() + ChatColor.RESET + "]" + message);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		Requirements.checkPlayerHasTeam(teamPlayer);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.or(new PatternBuilder()
						.oneOrMore("message"), new PatternBuilder()
						.append("tell"))
				.whiteSpace()
				.anyUnlimited(new PatternBuilder()
						.whiteSpace()
						.anyString())
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.player.core.chat";
	}

	@Override
	public String getUsage()
	{
		return "/team message [Message]";
	}

	@Override
	public String getDescription()
	{
		return "send message to teammates";
	}
}
