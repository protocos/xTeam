package me.protocos.xteam;

import me.protocos.xteam.command.BaseCommand;
import me.protocos.xteam.command.teamuser.TeamUserMessage;
import me.protocos.xteam.util.PatternBuilder;
import org.junit.Assert;
import org.junit.Test;

public class GeneralTestPad
{
	@Test
	public void ShouldBeSomething()
	{
		BaseCommand command = new TeamUserMessage(FakeXTeam.asTeamPlugin());
		System.out.println(modifyPattern(command.getPattern()));
		Assert.assertTrue("message ?".matches(modifyPattern(command.getPattern())));
	}

	private String modifyPattern(String commandPattern)
	{
		return new PatternBuilder().append(commandPattern.replaceAll("\\\\(s|S)(\\+|\\*)", "").replaceAll("\\[0\\-9\\-\\]\\+|\\[\\]\\+", "")).whiteSpaceOptional().repeatUnlimited(new PatternBuilder().append("\\?+").whiteSpaceOptional()).toString();
	}
}