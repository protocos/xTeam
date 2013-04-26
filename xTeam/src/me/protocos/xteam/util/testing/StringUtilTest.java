package me.protocos.xteam.util.testing;

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
	public void ShouldBeAlphaNumeric()
	{
		//ASSEMBLE
		String alpha = "azwesxcdrftvbgyhnuijmkoplkmn1234567890";
		//ACT
		boolean check = alpha.matches(ALPHA_NUMERIC);
		//ASSERT
		Assert.assertEquals(true, check);
	}
	@Test
	public void ShouldBeAnyCharacters()
	{
		//ASSEMBLE
		String anychars = "unjm¨«§ ¨·xrdctfvygbh¿¿Æ¿µÄ?><?cfghjvb<>?@#$%^GHU";
		//ACT
		boolean check = anychars.matches(ANY_CHARS);
		//ASSERT
		Assert.assertEquals(true, check);
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
	public void ShouldBeNumbers()
	{
		//ASSEMBLE
		String numbers = "0123456789";
		//ACT
		boolean check = numbers.matches(NUMBERS);
		//ASSERT
		Assert.assertEquals(true, check);
	}
	@Test
	public void ShouldBeOptionalAlphaNumeric()
	{
		//ASSEMBLE
		String alpha = "";
		//ACT
		boolean check = alpha.matches(OPTIONAL_ALPHA_NUMERIC);
		//ASSERT
		Assert.assertEquals(true, check);
	}
	@Test
	public void ShouldBeOptionalAnyCharacters()
	{
		//ASSEMBLE
		String anychars = "";
		//ACT
		boolean check = anychars.matches(OPTIONAL_ANY_CHARS);
		//ASSERT
		Assert.assertEquals(true, check);
	}
	@Test
	public void ShouldBeOptionalNumbers()
	{
		//ASSEMBLE
		String numbers = "";
		//ACT
		boolean check = numbers.matches(OPTIONAL_NUMBERS);
		//ASSERT
		Assert.assertEquals(true, check);
	}
	@Test
	public void ShouldBeOptionalWhiteSpace()
	{
		//ASSEMBLE
		String whitespace = "";
		//ACT
		boolean check = whitespace.matches(OPTIONAL_WHITE_SPACE);
		//ASSERT
		Assert.assertEquals(true, check);
	}
	@Test
	public void ShouldBePatternOneOrMore()
	{
		//ASSEMBLE
		String string = "hlowrld";
		//ACT
		String pattern = patternOneOrMore("hello world");
		//ASSERT
		Assert.assertEquals(true, string.matches(pattern));
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
	@Test
	public void ShouldBeWhiteSpace()
	{
		//ASSEMBLE
		String whitespace = " \n\t\r\f";
		//ACT
		boolean check = whitespace.matches(WHITE_SPACE);
		//ASSERT
		Assert.assertEquals(true, check);
	}
	@After
	public void takedown()
	{
	}
}
