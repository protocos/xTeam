package me.protocos.xteam.util;

import static me.protocos.xteam.util.StringUtil.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StringUtilTest
{
	@Before
	public void setup()
	{
	}

	@Test
	public void ShouldBeConcatenate()
	{
		//ASSEMBLE
		String[] string = { "hello", "world" };
		//ACT
		String concat = concatenate(string);
		//ASSERT
		Assert.assertEquals("hello world", concat);
	}

	@Test
	public void ShouldBeConcatenateGlue()
	{
		//ASSEMBLE
		String[] string = { "hello", "world" };
		//ACT
		String concat = concatenate(string, ",");
		//ASSERT
		Assert.assertEquals("hello,world", concat);
	}

	@Test
	public void ShouldBeMatchesLowerCase()
	{
		//ASSEMBLE
		String string = "HeLlO WoRlD";
		//ACT
		boolean matches = matchesLowerCase(string, "hello world");
		//ASSERT
		Assert.assertEquals(true, matches);
	}

	@Test
	public void ShouldBeMatchesUpperCase()
	{
		//ASSEMBLE
		String string = "HeLlO WoRlD";
		//ACT
		boolean matches = matchesUpperCase(string, "HELLO WORLD");
		//ASSERT
		Assert.assertEquals(true, matches);
	}

	@Test
	public void ShouldBeReverse()
	{
		//ASSEMBLE
		String string = "hello world";
		//ACT
		String reverse = reverse(string);
		//ASSERT
		Assert.assertEquals("dlrow olleh", reverse);
	}

	@After
	public void takedown()
	{
	}
}
