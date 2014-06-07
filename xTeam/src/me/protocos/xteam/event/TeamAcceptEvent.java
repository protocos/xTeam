package me.protocos.xteam.event;

import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.model.InviteRequest;

public class TeamAcceptEvent implements ITeamEvent
{
	private ITeam team;
	private InviteRequest request;

	public TeamAcceptEvent(ITeam team, InviteRequest request)
	{
		this.team = team;
		this.request = request;
	}

	@Override
	public String getTeamName()
	{
		return team.getName();
	}

	@Override
	public ITeam getTeam()
	{
		return team;
	}

	public ITeamPlayer getInviteSender()
	{
		return request.getInviteSender();
	}

	public ITeamPlayer getInviteReceiver()
	{
		return request.getInviteReceiver();
	}

	public InviteRequest getRequest()
	{
		return request;
	}
}
