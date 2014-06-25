package me.protocos.xteam.listener;

import java.util.HashSet;
import java.util.Set;
import org.junit.Assert;
import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.fakeobjects.FakePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TeamChatListenerTest
{
	private TeamPlugin teamPlugin;
	private Player mockPlayer;
	private Set<Player> recipients;
	private AsyncPlayerChatEvent playerChatEvent;
	private TeamChatListener chatListener;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		mockPlayer = new FakePlayer("protocos");
		recipients = new HashSet<Player>();
		chatListener = new TeamChatListener(teamPlugin);
	}

	@Test
	public void ShouldBeNormalChatEvent()
	{
		//ASSEMBLE
		playerChatEvent = new AsyncPlayerChatEvent(true, mockPlayer, "Hello World!", recipients);
		//ACT
		chatListener.onPlayerChat(playerChatEvent);
		//ASSERT
		Assert.assertEquals("Hello World!", playerChatEvent.getMessage());
		Assert.assertEquals(mockPlayer, playerChatEvent.getPlayer());
	}

	@After
	public void takedown()
	{
	}
}