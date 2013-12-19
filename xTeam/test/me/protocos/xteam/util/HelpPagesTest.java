package me.protocos.xteam.util;

import me.protocos.xteam.api.model.HelpPages;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HelpPagesTest
{
	HelpPages help;

	@Before
	public void setup()
	{
		help = new HelpPages();
	}

	@Test
	public void ShouldBeAddLine()
	{
		//ASSEMBLE
		//ACT
		help.addLine("info item 1");
		//ASSERT
		Assert.assertEquals("info item 1", help.getLine(0));
		Assert.assertEquals(1, help.getNumLines());
	}

	@Test
	public void ShouldBeGetBlankLinesOnPage1()
	{
		//ASSEMBLE
		help.addLine("info item 1");
		help.addLine("info item 2");
		help.addLine("info item 3");
		help.addLine("info item 4");
		help.addLine("info item 5");
		help.addLine("info item 6");
		help.addLine("info item 7");
		help.addLine("info item 8");
		help.addLine("info item 9");
		help.addLine("info item 10");
		help.addLine("info item 11");
		help.addLine("info item 12");
		//ACT
		//ASSERT
		Assert.assertEquals(0, help.getNumBlankLines(0));
	}

	@Test
	public void ShouldBeGetBlankLinesOnPage2()
	{
		//ASSEMBLE
		help.addLine("info item 1");
		help.addLine("info item 2");
		help.addLine("info item 3");
		help.addLine("info item 4");
		help.addLine("info item 5");
		help.addLine("info item 6");
		help.addLine("info item 7");
		help.addLine("info item 8");
		help.addLine("info item 9");
		help.addLine("info item 10");
		help.addLine("info item 11");
		help.addLine("info item 12");
		//ACT
		//ASSERT
		Assert.assertEquals(6, help.getNumBlankLines(1));
	}

	@Test
	public void ShouldBeGetBlankLinesOnPage3()
	{
		//ASSEMBLE
		help.addLine("info item 1");
		help.addLine("info item 2");
		help.addLine("info item 3");
		help.addLine("info item 4");
		help.addLine("info item 5");
		help.addLine("info item 6");
		help.addLine("info item 7");
		help.addLine("info item 8");
		help.addLine("info item 9");
		help.addLine("info item 10");
		help.addLine("info item 11");
		help.addLine("info item 12");
		//ACT
		//ASSERT
		Assert.assertEquals(9, help.getNumBlankLines(2));
	}

	@Test
	public void ShouldBeGetLinesPerPage()
	{
		//ASSEMBLE
		//ACT
		//ASSERT
		Assert.assertEquals(10, help.getLinesPerPage());
	}

	@Test
	public void ShouldBeGetPage1()
	{
		//ASSEMBLE
		help.addLine("info item 1");
		help.addLine("info item 2");
		help.addLine("info item 3");
		help.addLine("info item 4");
		help.addLine("info item 5");
		help.addLine("info item 6");
		help.addLine("info item 7");
		help.addLine("info item 8");
		help.addLine("info item 9");
		help.addLine("info item 10");
		help.addLine("info item 11");
		help.addLine("info item 12");
		//ACT
		//ASSERT
		Assert.assertEquals("Title\n" +
				"info item 1\n" +
				"info item 2\n" +
				"info item 3\n" +
				"info item 4\n" +
				"info item 5\n" +
				"info item 6\n" +
				"info item 7\n" +
				"info item 8\n" +
				"info item 9", help.getPage(0));
	}

	@Test
	public void ShouldBeGetPage2()
	{
		//ASSEMBLE
		help.addLine("info item 1");
		help.addLine("info item 2");
		help.addLine("info item 3");
		help.addLine("info item 4");
		help.addLine("info item 5");
		help.addLine("info item 6");
		help.addLine("info item 7");
		help.addLine("info item 8");
		help.addLine("info item 9");
		help.addLine("info item 10");
		help.addLine("info item 11");
		help.addLine("info item 12");
		//ACT
		//ASSERT
		Assert.assertEquals("Title\n" +
				"info item 10\n" +
				"info item 11\n" +
				"info item 12\n" +
				" \n" +
				" \n" +
				" \n" +
				" \n" +
				" \n" +
				" ", help.getPage(1));
	}

	@Test
	public void ShouldBeGetPageTotalPagesEquals0()
	{
		//ASSEMBLE
		//ACT
		//ASSERT
		Assert.assertEquals(0, help.getTotalPages());
	}

	@Test
	public void ShouldBeGetPageTotalPagesEquals1()
	{
		//ASSEMBLE
		help.addLine("info item 1");
		help.addLine("info item 2");
		help.addLine("info item 3");
		help.addLine("info item 4");
		help.addLine("info item 5");
		help.addLine("info item 6");
		help.addLine("info item 7");
		help.addLine("info item 8");
		help.addLine("info item 9");
		//ACT
		//ASSERT
		Assert.assertEquals(1, help.getTotalPages());
	}

	@Test
	public void ShouldBeGetPageTotalPagesEquals2()
	{
		//ASSEMBLE
		help.addLine("info item 1");
		help.addLine("info item 2");
		help.addLine("info item 3");
		help.addLine("info item 4");
		help.addLine("info item 5");
		help.addLine("info item 6");
		help.addLine("info item 7");
		help.addLine("info item 8");
		help.addLine("info item 9");
		help.addLine("info item 10");
		help.addLine("info item 11");
		help.addLine("info item 12");
		//ACT
		//ASSERT
		Assert.assertEquals(2, help.getTotalPages());
	}

	@Test
	public void ShouldBeGetPageTotalPagesEquals3()
	{
		//ASSEMBLE
		help.addLine("info item 1");
		help.addLine("info item 2");
		help.addLine("info item 3");
		help.addLine("info item 4");
		help.addLine("info item 5");
		help.addLine("info item 6");
		help.addLine("info item 7");
		help.addLine("info item 8");
		help.addLine("info item 9");
		help.addLine("info item 10");
		help.addLine("info item 11");
		help.addLine("info item 12");
		help.addLine("info item 13");
		help.addLine("info item 14");
		help.addLine("info item 15");
		help.addLine("info item 16");
		help.addLine("info item 17");
		help.addLine("info item 18");
		help.addLine("info item 19");
		//ACT
		//ASSERT
		Assert.assertEquals(3, help.getTotalPages());
	}

	@Test
	public void ShouldBeGetPageWithNoPages()
	{
		//ASSEMBLE
		//ACT
		//ASSERT
		Assert.assertEquals("Title\n" +
				" \n" +
				" \n" +
				" \n" +
				" \n" +
				" \n" +
				" \n" +
				" \n" +
				" \n" +
				" ", help.getPage(100));
	}

	@Test
	public void ShouldBeGetTitle()
	{
		//ASSEMBLE
		//ACT
		//ASSERT
		Assert.assertEquals("Title", help.getTitle());
	}

	@Test
	public void ShouldBeRemoveLine()
	{
		//ASSEMBLE
		help.addLine("info item 1");
		help.addLine("info item 2");
		//ACT
		help.removeLine(0);
		//ASSERT
		Assert.assertEquals("info item 2", help.getLine(0));
		Assert.assertEquals(1, help.getNumLines());
	}

	@After
	public void takedown()
	{
	}
}