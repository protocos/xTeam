package me.protocos.xteam.event;

import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.entity.ITeam;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamRenameEventTest
{
	private TeamPlugin fakePlugin;
	private ITeam team;
	private IEventDispatcher dispatcher;
	private RenameHandler handler;
	private TeamRenameEvent event;

	@Before
	public void setup()
	{
		//set up basics
		fakePlugin = FakeXTeam.asTeamPlugin();
		team = fakePlugin.getTeamManager().getTeam("ONE");
		//set up fake Rename
		//set up dispatcher and handler
		dispatcher = new EventDispatcher();
		handler = new RenameHandler();
		dispatcher.addTeamListener(handler);
	}

	@Test
	public void ShouldBeSomething()
	{
		//ASSEMBLE
		event = new TeamRenameEvent(team, "two");
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

class RenameHandler implements IEventHandler
{
	private String team;

	@TeamEvent
	public void handleRename(TeamRenameEvent event)
	{
		team = event.getTeamName();
	}

	public String getInvitingTeam()
	{
		return team;
	}
}