package me.protocos.xteam.util;

import org.junit.*;

public class ErrorReporterUtilTest
{

	@Before
	public void setup()
	{
	}

	@Ignore
	@Test
	public void ShouldBeSomething()
	{
		//ASSEMBLE
		ErrorReporterUtil errorUtil = new ErrorReporterUtil();
		//ACT
		boolean response = errorUtil.report(new NullPointerException());
		//ASSERT
		Assert.assertTrue(response);
	}

	@After
	public void takedown()
	{
	}
}