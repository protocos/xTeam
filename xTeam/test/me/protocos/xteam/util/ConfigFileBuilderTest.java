package me.protocos.xteam.util;

import java.io.IOException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConfigFileBuilderTest
{
	private ConfigFileBuilder configBuilder;

	@Before
	public void setup() throws IOException
	{
		configBuilder = new ConfigFileBuilder("test.txt");
	}

	@Test
	public void ShouldBeAddParameter()
	{
		//ASSEMBLE
		//ACT
		configBuilder.add("variable", "value", "test variable");
		//ASSERT
		Assert.assertEquals("# variable - test variable (default = value)", configBuilder.getComment("variable"));
		Assert.assertEquals("variable = value", configBuilder.get("variable"));
	}

	@Test
	public void ShouldBeRemoveParamater()
	{
		//ASSEMBLE
		configBuilder.add("variable", "value", "test variable");
		//ACT
		configBuilder.remove("variable");
		//ASSERT
		Assert.assertEquals("variable = null", configBuilder.get("variable"));
	}

	//	@Test
	//	public void ShouldBeLineBreak()
	//	{
	//		//ASSEMBLE
	//		configBuilder.add("variable3", "value3", "test variable3");
	//		configBuilder.add("variable1", "value1", "test variable1");
	//		configBuilder.add("variable2", "value2", "test variable2");
	//		configBuilder.add("variable4", "value4", "test variable4");
	//		//ACT
	//		//ASSERT
	//		Assert.assertEquals(48, configBuilder.getLineBreak().length());
	//	}
	//
	//	@Test
	//	public void ShouldBeWriteToFile() throws IOException
	//	{
	//		//ASSEMBLE
	//		configBuilder.add("variable3", "value3", "test variable3");
	//		configBuilder.add("variable1", "value1", "test variable1");
	//		configBuilder.add("variable2", "value2", "test variable2");
	//		configBuilder.add("variable4", "value4", "test variable4");
	//		//ACT
	//		configBuilder.write();
	//		//ASSERT
	//	}

	@After
	public void takedown()
	{
	}
}