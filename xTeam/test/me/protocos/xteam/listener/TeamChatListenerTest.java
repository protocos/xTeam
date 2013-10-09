package me.protocos.xteam.listener;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import java.util.HashSet;
import java.util.Set;
import junit.framework.Assert;
import me.protocos.xteam.api.fakeobjects.FakePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TeamChatListenerTest
{
	Player mockPlayer;
	Set<Player> recipients;
	AsyncPlayerChatEvent playerChatEvent;
	TeamChatListener chatListener;

	@Before
	public void setup()
	{
		mockData();
		mockPlayer = new FakePlayer("protocos");
		recipients = new HashSet<Player>();
		chatListener = new TeamChatListener();
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