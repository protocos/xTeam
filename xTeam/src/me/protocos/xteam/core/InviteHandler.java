package me.protocos.xteam.core;

import java.util.ArrayList;
import java.util.List;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.collections.HashList;
import me.protocos.xteam.data.IDataContainer;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.entity.TeamPlayer;
import me.protocos.xteam.event.*;
import me.protocos.xteam.message.Message;
import me.protocos.xteam.message.MessageUtil;
import me.protocos.xteam.model.ILog;
import me.protocos.xteam.model.InviteRequest;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.scheduler.BukkitScheduler;

public class InviteHandler implements IEventHandler, IDataContainer
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
		this.dispatcher.addTeamListener(this);
		this.bukkitScheduler = teamPlugin.getBukkitScheduler();
		this.invites = new HashList<String, InviteRequest>();
		this.log = teamPlugin.getLog();
	}

	public void addInvite(final InviteRequest request)
	{
		final ITeamPlayer inviter = request.getInviter();
		final ITeamPlayer invitee = request.getInvitee();
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

	public ITeam getInviteTeam(String player)
	{
		if (invites.containsKey(player))
			return invites.get(player).getInviteTeam();
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

	public void removeTeamInvites(ITeam team)
	{
		for (InviteRequest invite : invites)
		{
			if (invite.getInviteTeam().equals(team))
				invites.remove(invite.getInviteeName());
		}
	}

	public String getTeamInviteRecipients(ITeam team)
	{
		ArrayList<String> names = new ArrayList<String>();
		for (InviteRequest request : invites)
		{
			if (request.getInviteTeam().equals(team))
				names.add(request.getInviteeName());
		}
		return CommonUtil.concatenate(names, ", ");
	}

	@TeamEvent
	public void onDisband(TeamDisbandEvent disbandEvent)
	{
		this.removeTeamInvites(disbandEvent.getTeam());
	}

	@Override
	public List<String> exportData()
	{
		List<String> list = CommonUtil.emptyList();
		for (InviteRequest request : invites)
			list.add(request.toString());
		return list;
	}
}
