package me.protocos.xteam.event;

import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.core.IPlayerManager;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.model.InviteRequest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamAcceptEventTest
{
	private TeamPlugin fakePlugin;
	private IPlayerManager playerFactory;
	private ITeam team;
	private InviteRequest invite;
	private IEventDispatcher dispatcher;
	private AcceptHandler handler;
	private TeamAcceptEvent event;

	@Before
	public void setup()
	{
		//set up basics
		fakePlugin = FakeXTeam.asTeamPlugin();
		playerFactory = fakePlugin.getPlayerManager();
		team = fakePlugin.getTeamManager().getTeam("ONE");
		//set up fake invite
		invite = new InviteRequest(playerFactory.getPlayer("kmlanglois"), playerFactory.getPlayer("Lonely"), 0L);
		//set up dispatcher and handler
		dispatcher = new EventDispatcher();
		handler = new AcceptHandler();
		dispatcher.addTeamListener(handler);
	}

	@Test
	public void ShouldBeSomething()
	{
		//ASSEMBLE
		event = new TeamAcceptEvent(team, invite);
		//ACT
		dispatcher.dispatchEvent(event);
		//ASSERT
		Assert.assertEquals("ONE", handler.getInvitingTeam());
	}

	@After
	public void takedown()
	{
	}
}

class AcceptHandler implements IEventHandler
{
	private String team;

	@TeamEvent
	public void handleAccept(TeamAcceptEvent event)
	{
		team = event.getTeamName();
	}

	public String getInvitingTeam()
	{
		return team;
	}
}