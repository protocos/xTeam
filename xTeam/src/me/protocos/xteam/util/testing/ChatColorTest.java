package me.protocos.xteam.util.testing;

import me.protocos.xteam.util.ChatColorUtil;
import org.bukkit.ChatColor;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ChatColorTest
{
	@Before
	public void setup()
	{
	}
	@Test
	public void ShouldBeColor()
	{
		//ASSEMBLE
		//ACT
		//ASSERT
		Assert.assertEquals(ChatColor.RESET, ChatColorUtil.getColor("notacolor"));
	}
	@Test
	public void ShouldBeGreen()
	{
		//ASSEMBLE
		//ACT
		//ASSERT
		Assert.assertEquals(ChatColor.GREEN, ChatColorUtil.getColor("green"));
	}
	@After
	public void takedown()
	{
	}
}