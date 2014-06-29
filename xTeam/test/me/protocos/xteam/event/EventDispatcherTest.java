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
	private ITeamEvent spawnedEvent;
	private ITeam team;
	private InviteRequest inviteRequest;
	private ITeamPlayer player1;
	private ITeamPlayer player2;

	@Before
	public void setup()
	{
		dispatcher = new EventDispatcher();
		class TeamListener implements IEventHandler
		{
			@TeamEvent
			public void onRenameEvent(TeamRenameEvent event)
			{
				spawnedEvent = event;
			}

			@TeamEvent
			public void onCreateEvent(TeamCreateEvent event)
			{
				spawnedEvent = event;
			}

			@TeamEvent
			public void onDisbandEvent(TeamDisbandEvent event)
			{
				spawnedEvent = event;
			}

			@TeamEvent
			public void onInviteEvent(TeamInviteEvent event)
			{
				spawnedEvent = event;
			}

			@TeamEvent
			public void onAcceptEvent(TeamAcceptEvent event)
			{
				spawnedEvent = event;
			}

			@TeamEvent
			public void onJoinEvent(TeamJoinEvent event)
			{
				spawnedEvent = event;
			}

			@TeamEvent
			public void onLeaveEvent(TeamLeaveEvent event)
			{
				spawnedEvent = event;
			}
		}
		listener = new TeamListener();
		dispatcher.addTeamListener(listener);
		team = new Team.Builder(teamPlugin, "one").build();
		player1 = new TeamPlayer(teamPlugin, FakePlayer.get("protocos"));
		player2 = new TeamPlayer(teamPlugin, FakePlayer.get("kmlanglois"));
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
		Assert.assertEquals(TeamRenameEvent.class, spawnedEvent.getClass());
	}

	@Test
	public void ShouldBeCreateEvent()
	{
		//ASSEMBLE
		ITeamEvent event = new TeamCreateEvent(team);
		//ACT
		dispatcher.dispatchEvent(event);
		//ASSERT
		Assert.assertEquals(TeamCreateEvent.class, spawnedEvent.getClass());
	}

	@Test
	public void ShouldBeDisbandEvent()
	{
		//ASSEMBLE
		ITeamEvent event = new TeamDisbandEvent(team);
		//ACT
		dispatcher.dispatchEvent(event);
		//ASSERT
		Assert.assertEquals(TeamDisbandEvent.class, spawnedEvent.getClass());
	}

	@Test
	public void ShouldBeInviteEvent()
	{
		//ASSEMBLE
		ITeamEvent event = new TeamInviteEvent(team, inviteRequest);
		//ACT
		dispatcher.dispatchEvent(event);
		//ASSERT
		Assert.assertEquals(TeamInviteEvent.class, spawnedEvent.getClass());
	}

	@Test
	public void ShouldBeAcceptEvent()
	{
		//ASSEMBLE
		ITeamEvent event = new TeamAcceptEvent(team, inviteRequest);
		//ACT
		dispatcher.dispatchEvent(event);
		//ASSERT
		Assert.assertEquals(TeamAcceptEvent.class, spawnedEvent.getClass());
	}

	@Test
	public void ShouldBeJoinEvent()
	{
		//ASSEMBLE
		ITeamEvent event = new TeamJoinEvent(team, player1);
		//ACT
		dispatcher.dispatchEvent(event);
		//ASSERT
		Assert.assertEquals(TeamJoinEvent.class, spawnedEvent.getClass());
	}

	@Test
	public void ShouldBeLeaveEvent()
	{
		//ASSEMBLE
		ITeamEvent event = new TeamLeaveEvent(team, player1);
		//ACT
		dispatcher.dispatchEvent(event);
		//ASSERT
		Assert.assertEquals(TeamLeaveEvent.class, spawnedEvent.getClass());
	}

	@After
	public void takedown()
	{
	}
}