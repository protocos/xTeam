package me.protocos.xteam.util;

import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import org.junit.*;

public class ErrorReporterUtilTest
{
	private TeamPlugin teamPlugin;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
	}

	@Ignore
	@Test
	public void ShouldBeReportError()
	{
		//ASSEMBLE
		ErrorReporterUtil errorUtil = new ErrorReporterUtil(teamPlugin);
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