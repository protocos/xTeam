package me.protocos.xteam.event;

import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.entity.Team;
import me.protocos.xteam.entity.TeamPlayer;
import me.protocos.xteam.fakeobjects.FakePlayer;
import me.protocos.xteam.model.InviteRequest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EventDispatcherTest
{
	private TeamPlugin teamPlugin = FakeXTeam.asTeamPlugin();
	private IEventDispatcher dispatcher;
	private IEventHandler listener;
	private String responseMessage;
	private ITeam team;
	private InviteRequest inviteRequest;

	@Before
	public void setup()
	{
		dispatcher = new EventDispatcher();
		class TeamListener implements IEventHandler
		{
			@TeamEvent
			public void onRenameEvent(@SuppressWarnings("unused") TeamRenameEvent event)
			{
				responseMessage = "Rename Event";
			}

			@TeamEvent
			public void onCreateEvent(@SuppressWarnings("unused") TeamCreateEvent event)
			{
				responseMessage = "Create Event";
			}

			@TeamEvent
			public void onDisbandEvent(@SuppressWarnings("unused") TeamDisbandEvent event)
			{
				responseMessage = "Disband Event";
			}

			@TeamEvent
			public void onInviteEvent(@SuppressWarnings("unused") TeamInviteEvent event)
			{
				responseMessage = "Invite Event";
			}

			@TeamEvent
			public void onAcceptEvent(@SuppressWarnings("unused") TeamAcceptEvent event)
			{
				responseMessage = "Accept Event";
			}
		}
		listener = new TeamListener();
		dispatcher.addTeamListener(listener);
		team = new Team.Builder(teamPlugin, "one").build();
		ITeamPlayer player1 = new TeamPlayer(teamPlugin, new FakePlayer("protocos"));
		ITeamPlayer player2 = new TeamPlayer(teamPlugin, new FakePlayer("kmlanglois"));
		inviteRequest = new InviteRequest(player1, player2, System.currentTimeMillis());
	}

	@Test
	public void ShouldBeRenameEvent()
	{
		//ASSEMBLE
		ITeamEvent event = new TeamRenameEvent(team, "newname");
		//ACT
		dispatcher.dispatchEvent(event);
		//ASSERT
		Assert.assertEquals("Rename Event", responseMessage);
	}

	@Test
	public void ShouldBeCreateEvent()
	{
		//ASSEMBLE
		ITeamEvent event = new TeamCreateEvent(team);
		//ACT
		dispatcher.dispatchEvent(event);
		//ASSERT
		Assert.assertEquals("Create Event", responseMessage);
	}

	@Test
	public void ShouldBeDisbandEvent()
	{
		//ASSEMBLE
		ITeamEvent event = new TeamDisbandEvent(team);
		//ACT
		dispatcher.dispatchEvent(event);
		//ASSERT
		Assert.assertEquals("Disband Event", responseMessage);
	}

	@Test
	public void ShouldBeInviteEvent()
	{
		//ASSEMBLE
		ITeamEvent event = new TeamInviteEvent(team, inviteRequest);
		//ACT
		dispatcher.dispatchEvent(event);
		//ASSERT
		Assert.assertEquals("Invite Event", responseMessage);
	}

	@Test
	public void ShouldBeAcceptEvent()
	{
		//ASSEMBLE
		ITeamEvent event = new TeamAcceptEvent(team, inviteRequest);
		//ACT
		dispatcher.dispatchEvent(event);
		//ASSERT
		Assert.assertEquals("Accept Event", responseMessage);
	}

	@After
	public void takedown()
	{
	}
}