package me.protocos.xteam.util;

import me.protocos.xteam.message.MessageUtil;
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
		Assert.assertEquals(ChatColor.RESET, MessageUtil.getColor("notacolor"));
	}

	@Test
	public void ShouldBeGreen()
	{
		//ASSEMBLE
		//ACT
		//ASSERT
		Assert.assertEquals(ChatColor.GREEN, MessageUtil.getColor("green"));
	}

	@After
	public void takedown()
	{
	}
}