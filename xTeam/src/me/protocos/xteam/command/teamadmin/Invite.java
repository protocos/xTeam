package me.protocos.xteam.command.teamadmin;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.command.BaseUserCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.exception.*;
import me.protocos.xteam.util.PermissionUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Invite extends BaseUserCommand
{
	private String otherPlayer;

	public Invite()
	{
		super();
	}
	public Invite(Player sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		Data.invites.put(otherPlayer, team);
		Data.BUKKIT.getScheduler().scheduleSyncDelayedTask(Data.BUKKIT.getPluginManager().getPlugin("xTeam"), new Runnable()
		{
			@Override
			public void run()
			{
				Data.invites.remove(otherPlayer);
			}
		}, 30 * 20L);
		Player other = Data.BUKKIT.getPlayer(otherPlayer);
		if (other != null)
			other.sendMessage("You've been " + ChatColor.GREEN + "invited " + ChatColor.WHITE + "to join " + ChatColor.AQUA + team.getName());
		originalSender.sendMessage("You " + ChatColor.GREEN + "invited " + ChatColor.WHITE + otherPlayer);
	}
	@Override
	public void checkRequirements() throws TeamException
	{
		if (teamPlayer == null)
		{
			throw new TeamPlayerDoesNotExistException();
		}
		if (parseCommand.size() == 2)
		{
			otherPlayer = parseCommand.get(1);
		}
		else
		{
			throw new TeamInvalidCommandException();
		}
		if (!PermissionUtil.hasPermission(originalSender, getPermissionNode()))
		{
			throw new TeamPlayerPermissionException();
		}
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
		TeamPlayer p = new TeamPlayer(otherPlayer);
		if (!p.hasPlayedBefore())
		{
			throw new TeamPlayerNeverPlayedException();
		}
		if (Data.invites.containsKey(otherPlayer))
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
		return baseCommand + " invite [Player]";
	}
}
