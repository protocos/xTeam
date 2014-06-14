package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.command.TeamUserCommand;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.Message;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.ChatColor;

public class TeamUserMessage extends TeamUserCommand
{
	public TeamUserMessage(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		String message = "";
		for (int i = 1; i < commandContainer.size(); i++)
		{
			message += " " + commandContainer.getArgument(i);
		}
		for (ITeamPlayer teammate : teamUser.getOnlineTeammates())
		{
			new Message.Builder("[" + ChatColor.DARK_GREEN + teamUser.getName() + ChatColor.RESET + "]" + message).addRecipients(teammate).send(log);
		}
		new Message.Builder("[" + ChatColor.DARK_GREEN + teamUser.getName() + ChatColor.RESET + "]" + message).addRecipients(teamUser).send(log);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		Requirements.checkPlayerHasTeam(teamUser);
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
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.core.user.chat";
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
