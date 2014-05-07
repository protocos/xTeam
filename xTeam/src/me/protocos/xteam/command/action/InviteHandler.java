package me.protocos.xteam.command.action;

import java.util.HashMap;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.model.InviteRequest;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.MessageUtil;
import org.bukkit.scheduler.BukkitScheduler;

public class InviteHandler
{
	private BukkitScheduler bukkitScheduler;
	private TeamPlugin teamPlugin;
	private HashMap<String, InviteRequest> invites;

	public InviteHandler(TeamPlugin teamPlugin)
	{
		this.teamPlugin = teamPlugin;
		bukkitScheduler = teamPlugin.getBukkitScheduler();
		invites = new HashMap<String, InviteRequest>();
	}

	public void addInvite(final InviteRequest request)
	{
		final ITeamPlayer inviter = request.getInviteSender();
		final ITeamPlayer invitee = request.getInviteReceiver();
		invites.put(invitee.getName(), request);
		class InviteExpire implements Runnable
		{
			@Override
			public void run()
			{
				if (invites.containsKey(invitee.getName()))
				{
					invitee.sendMessage("Invite from " + inviter.getName() + " has " + MessageUtil.negativeMessage("expired"));
					invites.remove(invitee.getName());
				}
			}
		}
		bukkitScheduler.scheduleSyncDelayedTask(teamPlugin, new InviteExpire(), BukkitUtil.ONE_MINUTE_IN_TICKS);
	}

	public void clear()
	{
		invites.clear();
	}

	public String data()
	{
		return invites.toString();
	}

	public ITeam getInviteTeam(String player)
	{
		if (invites.containsKey(player))
			return invites.get(player).getSenderTeam();
		return null;
	}

	public long getInviteTime(String player)
	{
		if (invites.containsKey(player))
			return invites.get(player).getTimeSent();
		return 0;
	}

	public boolean hasInvite(String player)
	{
		long currentTime = System.currentTimeMillis();
		long timeStamp = getInviteTime(player);
		if (currentTime - timeStamp > 30 * 1000)
			invites.remove(player);
		return invites.containsKey(player);
	}

	public void removeInvite(String player)
	{
		invites.remove(player);
	}
}
