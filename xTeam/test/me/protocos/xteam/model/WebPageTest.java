package me.protocos.xteam.model;

import me.protocos.xteam.fakeobjects.FakeLog;

import org.junit.Assert;
import org.junit.Test;

public class WebPageTest {

	@Test
	public void ShouldBeDownloadPage()
	{
		//ASSEMBLE
		WebPage page = new WebPage("https://www.google.com/", new FakeLog());
		//ACT
		boolean result = page.download();
		//ASSERT
		Assert.assertTrue(result);
	}

	@Test
	public void ShouldBeSearchLine()
	{
		//ASSEMBLE
		WebPage page = new WebPage("https://www.google.com/", new FakeLog());
		//ACT
		String result = page.searchLine("google").pruneTags().toString();
		//ASSERT
		Assert.assertNotNull(result);
	}
}


