package me.protocos.xteam.command.teamadmin;

import static me.protocos.xteam.util.StringUtil.*;
import java.io.InvalidClassException;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.core.InviteHandler;
import me.protocos.xteam.core.PlayerManager;
import me.protocos.xteam.core.exception.*;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class UserInvite extends UserCommand
{
	private String otherPlayer;

	public UserInvite()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		InviteHandler.addInvite(otherPlayer, team);
		ITeamPlayer other = PlayerManager.getPlayer(otherPlayer);
		if (other.isOnline())
			other.sendMessage("You've been " + ChatColor.GREEN + "invited " + ChatColor.RESET + "to join " + ChatColor.AQUA + team.getName());
		originalSender.sendMessage("You " + ChatColor.GREEN + "invited " + ChatColor.RESET + other.getName());
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, InvalidClassException
	{
		super.checkRequirements(originalSender, parseCommand);
		otherPlayer = parseCommand.get(1);
		if (!teamPlayer.hasTeam())
		{
			throw new TeamPlayerHasNoTeamException();
		}
		if (!teamPlayer.isAdmin())
		{
			throw new TeamPlayerNotAdminException();
		}
		if (teamPlayer.getName().equalsIgnoreCase(otherPlayer))
		{
			throw new TeamPlayerInviteException("Player cannot invite self");
		}
		ITeamPlayer p = PlayerManager.getPlayer(otherPlayer);
		if (!p.hasPlayedBefore())
		{
			throw new TeamPlayerNeverPlayedException();
		}
		if (InviteHandler.hasInvite(otherPlayer))
		{
			throw new TeamPlayerHasInviteException();
		}
	}
	@Override
	public String getPattern()
	{
		return "in" + patternOneOrMore("vite") + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.admin.core.invite";
	}
	@Override
	public String getUsage()
	{
		return "/team invite [Player]";
	}
}
