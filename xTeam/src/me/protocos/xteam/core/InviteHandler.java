package me.protocos.xteam.core;

import java.util.ArrayList;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.collections.HashList;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.entity.TeamPlayer;
import me.protocos.xteam.event.IEventDispatcher;
import me.protocos.xteam.event.TeamAcceptEvent;
import me.protocos.xteam.event.TeamInviteEvent;
import me.protocos.xteam.message.Message;
import me.protocos.xteam.message.MessageUtil;
import me.protocos.xteam.model.ILog;
import me.protocos.xteam.model.InviteRequest;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.scheduler.BukkitScheduler;

public class InviteHandler
{
	private TeamPlugin teamPlugin;
	private IEventDispatcher dispatcher;
	private BukkitScheduler bukkitScheduler;
	private HashList<String, InviteRequest> invites;
	private ILog log;

	public InviteHandler(TeamPlugin teamPlugin)
	{
		this.teamPlugin = teamPlugin;
		this.dispatcher = teamPlugin.getEventDispatcher();
		this.bukkitScheduler = teamPlugin.getBukkitScheduler();
		this.invites = new HashList<String, InviteRequest>();
		this.log = teamPlugin.getLog();
	}

	public void addInvite(final InviteRequest request)
	{
		final ITeamPlayer inviter = request.getInviteSender();
		final ITeamPlayer invitee = request.getInviteReceiver();
		invites.put(invitee.getName(), request);
		dispatcher.dispatchEvent(new TeamInviteEvent(inviter.getTeam(), request));
		class InviteExpire implements Runnable
		{
			@Override
			public void run()
			{
				if (invites.containsKey(invitee.getName()))
				{
					new Message.Builder("Invite from " + inviter.getName() + " has " + MessageUtil.red("expired")).addRecipients(invitee).send(log);
					invites.remove(invitee.getName());
				}
			}
		}
		bukkitScheduler.scheduleSyncDelayedTask(teamPlugin, new InviteExpire(), BukkitUtil.ONE_MINUTE_IN_TICKS);
	}

	public void acceptInvite(TeamPlayer teamPlayer)
	{
		ITeam inviteTeam = this.getInviteTeam(teamPlayer.getName());
		inviteTeam.addPlayer(teamPlayer.getName());
		this.removeInvite(teamPlayer.getName());
		dispatcher.dispatchEvent(new TeamAcceptEvent(inviteTeam, invites.get(teamPlayer.getName())));
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

	public String getInvitesFromTeam(ITeam team)
	{
		ArrayList<String> names = new ArrayList<String>();
		for (InviteRequest request : invites)
		{
			if (request.getSenderTeam().equals(team))
				names.add(request.getReceiverName());
		}
		return CommonUtil.concatenate(names, ", ");
	}
}
