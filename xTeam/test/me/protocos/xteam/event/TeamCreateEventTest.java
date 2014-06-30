package me.protocos.xteam.event;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.fakeobjects.FakeXTeam;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamCreateEventTest
{
	private TeamPlugin fakePlugin;
	private ITeam team;
	private IEventDispatcher dispatcher;
	private CreateHandler handler;
	private TeamCreateEvent event;

	@Before
	public void setup()
	{
		//set up basics
		fakePlugin = FakeXTeam.asTeamPlugin();
		team = fakePlugin.getTeamCoordinator().getTeam("ONE");
		//set up dispatcher and handler
		dispatcher = new EventDispatcher();
		handler = new CreateHandler();
		dispatcher.addTeamListener(handler);
	}

	@Test
	public void ShouldBeSomething()
	{
		//ASSEMBLE
		event = new TeamCreateEvent(team);
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

class CreateHandler implements IEventHandler
{
	private String team;

	@TeamEvent
	public void handleCreate(TeamCreateEvent event)
	{
		team = event.getTeamName();
	}

	public String getInvitingTeam()
	{
		return team;
	}
}