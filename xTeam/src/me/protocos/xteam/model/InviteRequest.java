package me.protocos.xteam.model;

import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.ITeamPlayer;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class InviteRequest
{
	private final ITeamPlayer inviteSender;
	private final ITeamPlayer inviteReceiver;
	private final Long timeSent;

	public InviteRequest(ITeamPlayer inviteSender, ITeamPlayer inviteReceiver, Long timeSent)
	{
		this.inviteSender = inviteSender;
		this.inviteReceiver = inviteReceiver;
		this.timeSent = timeSent;
	}

	public String getReceiverName()
	{
		return inviteReceiver.getName();
	}

	public ITeam getSenderTeam()
	{
		return inviteSender.getTeam();
	}

	public ITeamPlayer getInviteSender()
	{
		return inviteSender;
	}

	public ITeamPlayer getInviteReceiver()
	{
		return inviteReceiver;
	}

	public Long getTimeSent()
	{
		return timeSent;
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder(13, 47).append(inviteSender).append(inviteReceiver).append(timeSent).toHashCode();
	}
}
