package me.protocos.xteam.model;

import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.ITeamPlayer;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class InviteRequest
{
	private final ITeamPlayer inviteSender;
	private final ITeamPlayer inviteReceiver;
	private final Long timeSent;
	private final ITeam inviteTeam;

	public InviteRequest(ITeamPlayer inviteSender, ITeamPlayer inviteReceiver, Long timeSent)
	{
		this.inviteSender = inviteSender;
		this.inviteTeam = inviteSender.getTeam();
		this.inviteReceiver = inviteReceiver;
		this.timeSent = timeSent;
	}

	public ITeamPlayer getInviter()
	{
		return this.inviteSender;
	}

	public ITeamPlayer getInvitee()
	{
		return this.inviteReceiver;
	}

	public String getInviterName()
	{
		return inviteSender.getName();
	}

	public String getInviteeName()
	{
		return inviteReceiver.getName();
	}

	public ITeam getInviteTeam()
	{
		return inviteTeam;
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

	@Override
	public String toString()
	{
		return "Inviter:" + inviteSender + " Invitee:" + inviteReceiver + " Team:" + inviteTeam.getName() + " Time:" + timeSent;
	}
}
