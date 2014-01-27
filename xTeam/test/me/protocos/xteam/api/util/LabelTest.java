package me.protocos.xteam.api.util;

import me.protocos.xteam.model.Label;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LabelTest
{
	@Before
	public void setup()
	{
	}

	@Test
	public void ShouldBeLabelEquals()
	{
		//ASSEMBLE
		Label label1 = new Label("testLabel1");
		Label label2 = new Label("testLabel1");
		//ACT
		boolean equal = label1.equals(label2);
		//ASSERT
		Assert.assertTrue(equal);
	}

	@Test
	public void ShouldBeAliasesEquals()
	{
		//ASSEMBLE
		Label label1 = new Label("testLabel1");
		Label label2 = new Label("testLabel2");
		label1.addAlias("alias");
		label2.addAlias("alias");
		//ACT
		boolean equal = label1.equals(label2);
		//ASSERT
		Assert.assertTrue(equal);
	}

	@Test
	public void ShouldBeAliasEqualsName()
	{
		//ASSEMBLE
		Label label1 = new Label("testLabel1");
		Label label2 = new Label("testLabel2");
		label2.addAlias("testLabel1");
		//ACT
		boolean equal = label1.equals(label2);
		//ASSERT
		Assert.assertTrue(equal);
	}

	@After
	public void takedown()
	{
	}
}