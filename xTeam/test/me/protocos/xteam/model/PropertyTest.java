package me.protocos.xteam.model;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.data.translator.IDataTranslator;
import me.protocos.xteam.data.translator.LocationDataTranslator;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.fakeobjects.FakeXTeam;
import me.protocos.xteam.model.Property;
import me.protocos.xteam.util.BukkitUtil;
import org.bukkit.Location;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PropertyTest
{
	private TeamPlugin teamPlugin;
	private BukkitUtil bukkitUtil;
	private Property property;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		bukkitUtil = teamPlugin.getBukkitUtil();
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
		property = Property.fromObject("location", location, new LocationDataTranslator(teamPlugin));
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
		Location originalLocation = new Location(bukkitUtil.getWorld("world"), 0, 0, 0, 0, 0);
		IDataTranslator<Location> formatter = new LocationDataTranslator(teamPlugin);
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
		property = Property.fromString("name", time);
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

	@Test
	public void ShouldBeUpdateKey()
	{
		//ASSEMBLE
		Property prop = Property.fromString("name:protocos");
		//ACT
		boolean updated = prop.updateKey("tag");
		//ASSERT
		Assert.assertTrue(updated);
	}

	@After
	public void takedown()
	{
	}
}