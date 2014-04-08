package me.protocos.xteam.util;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ErrorReporterUtilTest
{

	@Before
	public void setup()
	{
	}

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