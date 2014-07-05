package me.protocos.xteam.core;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.entity.TeamPlayer;
import me.protocos.xteam.fakeobjects.FakePlayer;
import me.protocos.xteam.fakeobjects.FakeXTeam;
import me.protocos.xteam.model.InviteRequest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InviteHandlerTest
{
	private TeamPlugin teamPlugin;
	private ITeamCoordinator teamCoordinator;
	private InviteHandler inviteHandler;
	private ITeamPlayer protocos;
	private ITeamPlayer kmlanglois;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		teamCoordinator = teamPlugin.getTeamCoordinator();
		inviteHandler = new InviteHandler(teamPlugin);
		protocos = new TeamPlayer(teamPlugin, FakePlayer.get("protocos"));
		kmlanglois = new TeamPlayer(teamPlugin, FakePlayer.get("kmlanglois"));
	}

	@Test
	public void ShouldBeInvitePlayer()
	{
		//ASSEMBLE
		InviteRequest inviteRequest = new InviteRequest(protocos, kmlanglois, System.currentTimeMillis());
		//ACT
		inviteHandler.addInvite(inviteRequest);
		//ASSERT
		Assert.assertTrue(inviteHandler.hasInvite("kmlanglois"));
	}

	@Test
	public void ShouldBeExpiredInvite()
	{
		//ASSEMBLE
		InviteRequest inviteRequest = new InviteRequest(protocos, kmlanglois, 0L);
		//ACT
		inviteHandler.addInvite(inviteRequest);
		//ASSERT
		Assert.assertFalse(inviteHandler.hasInvite("kmlanglois"));
	}

	@Test
	public void ShouldBeTeamInvites()
	{
		//ASSEMBLE
		InviteRequest inviteRequest = new InviteRequest(protocos, kmlanglois, System.currentTimeMillis());
		inviteHandler.addInvite(inviteRequest);
		//ACT
		String invites = inviteHandler.getTeamInviteRecipients(protocos.getTeam());
		//ASSERT
		Assert.assertEquals("kmlanglois", invites);
	}

	@Test
	public void ShouldBeDisbandNoInvite()
	{
		//ASSEMBLE
		InviteRequest inviteRequest = new InviteRequest(protocos, kmlanglois, System.currentTimeMillis());
		inviteHandler.addInvite(inviteRequest);
		//ACT
		teamCoordinator.disbandTeam("ONE");
		//ASSERT
		Assert.assertFalse(inviteHandler.hasInvite("kmlanglois"));
	}

	@After
	public void takedown()
	{
	}
}