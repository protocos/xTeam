package me.protocos.xteam.event;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.fakeobjects.FakeXTeam;
import me.protocos.xteam.model.InviteRequest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamInviteEventTest
{
	private TeamPlugin fakePlugin;
	private IPlayerFactory playerFactory;
	private ITeam team;
	private InviteRequest invite;
	private IEventDispatcher dispatcher;
	private InviteHandler handler;
	private TeamInviteEvent event;

	@Before
	public void setup()
	{
		//set up basics
		fakePlugin = FakeXTeam.asTeamPlugin();
		playerFactory = fakePlugin.getPlayerFactory();
		team = fakePlugin.getTeamCoordinator().getTeam("ONE");
		//set up fake invite
		invite = new InviteRequest(playerFactory.getPlayer("kmlanglois"), playerFactory.getPlayer("Lonely"), 0L);
		//set up dispatcher and handler
		dispatcher = new EventDispatcher();
		handler = new InviteHandler();
		dispatcher.addTeamListener(handler);
	}

	@Test
	public void ShouldBeSomething()
	{
		//ASSEMBLE
		event = new TeamInviteEvent(team, invite);
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

class InviteHandler implements IEventHandler
{
	private String team;

	@TeamEvent
	public void handleInvite(TeamInviteEvent event)
	{
		team = event.getTeamName();
	}

	public String getInvitingTeam()
	{
		return team;
	}
}