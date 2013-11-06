package me.protocos.xteam.command.teamadmin;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.PlayerManager;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import org.bukkit.command.CommandSender;

public class UserPromote extends UserCommand
{
	private String otherPlayer;

	public UserPromote()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		team.promote(otherPlayer);
		ITeamPlayer other = PlayerManager.getPlayer(otherPlayer);
		if (other.isOnline())
			other.sendMessage("You've been " + ChatColorUtil.positiveMessage("promoted"));
		originalSender.sendMessage("You" + ChatColorUtil.positiveMessage(" promoted ") + otherPlayer);
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		super.checkRequirements(originalSender, parseCommand);
		otherPlayer = parseCommand.get(1);
		ITeamPlayer other = PlayerManager.getPlayer(otherPlayer);
		Requirements.checkPlayerHasTeam(teamPlayer);
		Requirements.checkPlayerIsTeamAdmin(teamPlayer);
		Requirements.checkPlayerIsTeammate(teamPlayer, other);
	}

	@Override
	public String getPattern()
	{
		return patternOneOrMore("promote") + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.admin.core.promote";
	}

	@Override
	public String getUsage()
	{
		return "/team promote [Player]";
	}
}
