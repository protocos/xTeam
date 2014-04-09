package me.protocos.xteam.util;

import static me.protocos.xteam.util.CommonUtil.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CommonUtilTest
{
	@Before
	public void setup()
	{
	}

	@Test
	public void assignFromType() throws IncompatibleClassChangeError
	{
		Number number = new Double(10);
		Double fromNumber = CommonUtil.assignFromType(number, Double.class);
		Assert.assertEquals(10.0, fromNumber, 0);
	}

	@Test
	public void subListOfType()
	{
		List<Number> list = new ArrayList<Number>();
		list.add(new Double(10));
		list.add(new Integer(12));
		list.add(new Long(14));
		List<Integer> newList = CommonUtil.subListOfType(list, Integer.class);
		Assert.assertTrue(newList.contains(12));
		Assert.assertEquals(1, newList.size());
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