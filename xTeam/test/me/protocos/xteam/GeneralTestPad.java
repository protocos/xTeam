package me.protocos.xteam;

import me.protocos.xteam.model.XTeamWebPage;

import org.junit.Test;

public class GeneralTestPad
{
	@Test
	public void ShouldBeSomething()
	{
		XTeamWebPage page = new XTeamWebPage("http://dev.bukkit.org/bukkit-plugins/xteam/files/");
		System.out.println(page.getMostRecentVersion());
	}
}
