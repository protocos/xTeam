package me.protocos.xteam.util.testing;

import me.protocos.xteam.util.ColorUtil;
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
	public void ShouldBeGreen()
	{
		//ASSEMBLE
		//ACT
		//ASSERT
		Assert.assertEquals(ChatColor.GREEN, ColorUtil.getColor("green"));
	}
	@Test
	public void ShouldBeColor()
	{
		//ASSEMBLE
		//ACT
		//ASSERT
		Assert.assertEquals(ChatColor.RESET, ColorUtil.getColor("notacolor"));
	}
	@After
	public void takedown()
	{
	}
}