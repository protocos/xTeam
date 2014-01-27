package me.protocos.xteam.data;

import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.fakeobjects.FakeServer;
import me.protocos.xteam.data.translator.IDataTranslator;
import me.protocos.xteam.data.translator.LocationDataTranslator;
import me.protocos.xteam.util.BukkitUtil;
import org.bukkit.Location;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PropertyTest
{
	private Property property;

	@Before
	public void setup()
	{
	}

	@Test
	public void ShouldBeGetdecompiledValue()
	{
		//ASSEMBLE
		property = new Property("name", "protocos");
		//ACT
		String name = property.getValue();
		//ASSERT
		Assert.assertEquals("protocos", name);
	}

	@Test
	public void ShouldBeGetdecompiledLocation()
	{
		//ASSEMBLE
		Location location = new FakeLocation(1, 2, 3, 4, 5);
		property = Property.fromObject("location", location, new LocationDataTranslator());
		//ACT
		String decompiledLocation = property.getValue();
		//ASSERT
		Assert.assertEquals("world,1.0,2.0,3.0,4.0,5.0", decompiledLocation);
	}

	//Test for formatting/deformatting that is really only used in conjunction with Property
	@Test
	public void ShouldBeGetUndecompiledLocation()
	{
		//ASSEMBLE
		BukkitUtil.setServer(new FakeServer());
		Location originalLocation = new Location(BukkitUtil.getWorld("world"), 0, 0, 0, 0, 0);
		IDataTranslator<Location> formatter = new LocationDataTranslator();
		//ACT
		String compiledLocation = formatter.decompile(originalLocation);
		Location decompiledLocation = formatter.compile(compiledLocation);
		//ASSERT
		Assert.assertEquals(originalLocation, decompiledLocation);
	}

	@Test
	public void ShouldBeGetdecompiledLong()
	{
		//ASSEMBLE
		Long time = System.currentTimeMillis();
		property = Property.fromObject("name", time);
		//ACT
		String decompiledLocation = property.getValue();
		//ASSERT
		Assert.assertEquals("" + time, decompiledLocation);
	}

	@Test
	public void ShouldBeEquals()
	{
		//ASSEMBLE
		Property prop1 = Property.fromString("name:protocos");
		Property prop2 = Property.fromString("name:protocos");
		//ACT
		boolean equals = prop1.equals(prop2);
		//ASSERT
		Assert.assertTrue(equals);
	}

	@After
	public void takedown()
	{
	}
}