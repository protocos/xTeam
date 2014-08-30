package me.protocos.api.builder;

import java.util.regex.Pattern;
import me.protocos.api.builder.PatternBuilder;
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
				.whiteSpaceOptional()
				.append("YOLO")
				.numbers()
				.append("!")
				.whiteSpace()
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
				.noneOrMore("protocos")
				.numbers()
				.anyOne(new PatternBuilder("ab"))
				.anyUnlimited(new PatternBuilder("ab"))
				.lowerCase("bbbb")
				.or(new PatternBuilder("\\?+"), new PatternBuilder("help"), new PatternBuilder("poop"))
				.toString();
		//ACT
		//ASSERT
		Assert.assertTrue("proto123aaaabbbbhelp".matches(pattern));
	}

	@After
	public void takedown()
	{
	}
}