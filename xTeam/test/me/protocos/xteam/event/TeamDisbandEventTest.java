package me.protocos.xteam.event;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.fakeobjects.FakeXTeam;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamDisbandEventTest
{
	private TeamPlugin fakePlugin;
	private ITeam team;
	private IEventDispatcher dispatcher;
	private DisbandHandler handler;
	private TeamDisbandEvent event;

	@Before
	public void setup()
	{
		//set up basics
		fakePlugin = FakeXTeam.asTeamPlugin();
		team = fakePlugin.getTeamCoordinator().getTeam("ONE");
		//set up dispatcher and handler
		dispatcher = new EventDispatcher();
		handler = new DisbandHandler();
		dispatcher.addTeamListener(handler);
	}

	@Test
	public void ShouldBeSomething()
	{
		//ASSEMBLE
		event = new TeamDisbandEvent(team);
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

class DisbandHandler implements IEventHandler
{
	private String team;

	@TeamEvent
	public void handleDisband(TeamDisbandEvent event)
	{
		team = event.getTeamName();
	}

	public String getInvitingTeam()
	{
		return team;
	}
}