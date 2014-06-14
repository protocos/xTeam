package me.protocos.xteam.command.serveradmin;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.Message;
import me.protocos.xteam.message.MessageUtil;
import me.protocos.xteam.util.PatternBuilder;

public class ServerAdminPromote extends ServerAdminCommand
{
	private String teamName, playerName;
	private ITeam changeTeam;

	public ServerAdminPromote(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		changeTeam.promote(playerName);
		new Message.Builder("You " + MessageUtil.green("promoted") + " " + playerName).addRecipients(serverAdmin).excludeRecipients(changeTeam).send(log);
		ITeamPlayer other = playerFactory.getPlayer(playerName);
		new Message.Builder("You have been " + MessageUtil.green("promoted") + " by an admin").addRecipients(other).send(log);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		teamName = commandContainer.getArgument(1);
		playerName = commandContainer.getArgument(2);
		changeTeam = teamCoordinator.getTeam(teamName);
		ITeamPlayer playerPromote = playerFactory.getPlayer(playerName);
		Requirements.checkPlayerHasPlayedBefore(playerPromote);
		Requirements.checkTeamExists(teamCoordinator, teamName);
		Requirements.checkPlayerHasTeam(playerPromote);
		Requirements.checkPlayerOnTeam(playerPromote, changeTeam);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("promote")
				.whiteSpace()
				.anyString()
				.whiteSpace()
				.anyString()
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.core.serveradmin.promote";
	}

	@Override
	public String getUsage()
	{
		return "/team promote [Team] [Player]";
	}

	@Override
	public String getDescription()
	{
		return "promote player to admin";
	}
}
