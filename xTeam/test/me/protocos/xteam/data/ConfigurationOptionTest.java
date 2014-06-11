package me.protocos.xteam.data;

import me.protocos.xteam.data.configuration.ConfigurationOption;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConfigurationOptionTest
{
	@Before
	public void setup()
	{
	}

	@Test
	public void ShouldBeBooleanOption()
	{
		//ASSEMBLE
		ConfigurationOption<Boolean> option = new ConfigurationOption<Boolean>("booleanoption", false, "this is a boolean", true);
		//ACT
		//ASSERT
		Assert.assertTrue(option.getValue());
		Assert.assertFalse(option.getDefaultValue());
		Assert.assertEquals("# booleanoption - this is a boolean (default = false)", option.getComment());
		Assert.assertEquals("# booleanoption - this is a boolean (default = false)".length(), option.length());
	}

	@Test
	public void ShouldBeIntegerOption()
	{
		//ASSEMBLE
		ConfigurationOption<Integer> option = new ConfigurationOption<Integer>("integeroption", 10, "this is an integer", 0);
		//ACT
		//ASSERT
		Assert.assertEquals(new Integer(0), option.getValue());
		Assert.assertEquals(new Integer(10), option.getDefaultValue());
		Assert.assertEquals("# integeroption - this is an integer (default = 10)", option.getComment());
		Assert.assertEquals("# integeroption - this is an integer (default = 10)".length(), option.length());
	}

	@Test
	public void ShouldBeDoubleOption()
	{
		//ASSEMBLE
		ConfigurationOption<Double> option = new ConfigurationOption<Double>("doubleoption", 10.0D, "this is an double", 0.0D);
		//ACT
		//ASSERT
		Assert.assertEquals(new Double(0), option.getValue());
		Assert.assertEquals(new Double(10), option.getDefaultValue());
		Assert.assertEquals("# doubleoption - this is an double (default = 10.0)", option.getComment());
		Assert.assertEquals("# doubleoption - this is an double (default = 10.0)".length(), option.length());
	}

	@Test
	public void ShouldBeStringOption()
	{
		//ASSEMBLE
		ConfigurationOption<String> option = new ConfigurationOption<String>("stringoption", "", "this is an string", "helloworld");
		//ACT
		//ASSERT
		Assert.assertEquals("helloworld", option.getValue());
		Assert.assertEquals("", option.getDefaultValue());
		Assert.assertEquals("# stringoption - this is an string (default = )", option.getComment());
		Assert.assertEquals("# stringoption - this is an string (default = )".length(), option.length());
	}

	@After
	public void takedown()
	{
	}
}