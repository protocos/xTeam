package me.protocos.xteam.util;

import java.util.regex.Pattern;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PatternBuilderTest
{
	@Before
	public void setup()
	{
	}

	@Test
	public void ShouldBeStringMatch()
	{
		//ASSEMBLE
		String pattern = new PatternBuilder().append("hello world").toString();
		//ACT
		//ASSERT
		Assert.assertTrue("hello world".matches(pattern));
		Assert.assertFalse("hello".matches(pattern));
	}

	@Test
	public void ShouldBePattern()
	{
		//ASSEMBLE
		Pattern pattern = new PatternBuilder().append("hello world").build();
		//ACT
		//ASSERT
		Assert.assertTrue("hello world".matches(pattern.pattern()));
		Assert.assertFalse("hello".matches(pattern.pattern()));
	}

	@Test
	public void ShouldBeComplexPattern1()
	{
		//ASSEMBLE
		String pattern = new PatternBuilder()
				.oneOrMoreIgnoreCase("hello")
				.whiteSpace(true)
				.append("YOLO")
				.numbers(false)
				.append("!")
				.whiteSpace(false)
				.repeat("HO", 3)
				.toString();
		//ACT
		//ASSERT
		Assert.assertTrue("HELLOYOLO111!    HOHOHO".matches(pattern));
	}

	@Test
	public void ShouldBeComplexPattern2()
	{
		//ASSEMBLE
		String pattern = new PatternBuilder()
				.oneOrMore("protocos")
				.numbers(false)
				.anyOne("ab")
				.anyUnlimited("ab")
				.lowerCase("bbbb")
				.noneOrMore("help")
				.toString();
		//ACT
		//ASSERT
		Assert.assertTrue("proto123aaaabbbb".matches(pattern));
	}

	@After
	public void takedown()
	{
	}
}