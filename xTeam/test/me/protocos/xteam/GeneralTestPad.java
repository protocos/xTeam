package me.protocos.xteam;

import me.protocos.xteam.util.PatternBuilder;
import org.junit.Test;

public class GeneralTestPad
{
	@Test
	public void ShouldBeSomething()
	{
		System.out.println(new PatternBuilder().anyUnlimitedOptional(new PatternBuilder().alphaNumeric().append(",")).whiteSpaceOptional().matches("world,world"));
	}
}