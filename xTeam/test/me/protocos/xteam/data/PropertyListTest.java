package me.protocos.xteam.data;

import me.protocos.xteam.data.translator.LocationDataTranslator;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.fakeobjects.FakeXTeam;
import me.protocos.xteam.model.Property;
import me.protocos.xteam.model.PropertyList;
import org.bukkit.Location;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PropertyListTest
{
	private PropertyList list;

	@Before
	public void setup()
	{
		list = new PropertyList();
	}

	@Test
	public void ShouldBePropertyListAsString()
	{
		//ASSEMBLE
		//ACT
		list.put("name", "protocos");
		list.put("lastAttacked", "0");
		list.put("lastTeleported", "0");
		//ASSERT
		Assert.assertEquals("name:protocos lastAttacked:0 lastTeleported:0", list.toString());
	}

	@Test
	public void ShouldBeRemove()
	{
		//ASSEMBLE
		list.put("name", "protocos");
		list.put("lastAttacked", "0");
		list.put("lastTeleported", "0");
		//ACT
		list.remove("name");
		//ASSERT
		Assert.assertEquals("lastAttacked:0 lastTeleported:0", list.toString());
	}

	@Test
	public void ShouldBeGetProperty()
	{
		//ASSEMBLE
		list = new PropertyList();
		Property property = new Property("name", "protocos");
		list.put(property);
		//ACT
		Property name = list.get("name");
		//ASSERT
		Assert.assertEquals(property, name);
	}

	@Test
	public void ShouldBePutProperty()
	{
		//ASSEMBLE
		list = new PropertyList();
		list.put("name", "protocos");
		//ACT
		Property name = list.get("name");
		//ASSERT
		Assert.assertEquals(new Property("name", "protocos"), name);
	}

	@Test
	public void ShouldBePutPropertyAsType()
	{
		//ASSEMBLE
		list = new PropertyList();
		Location fakeLocation = new FakeLocation();
		list.put("location", fakeLocation, new LocationDataTranslator(FakeXTeam.asTeamPlugin()));
		//ACT
		Property location = list.get("location");
		//ASSERT
		Assert.assertEquals(new Property("location", fakeLocation, new LocationDataTranslator(FakeXTeam.asTeamPlugin())), location);
	}

	@Test
	public void ShouldBeGetPropertyAsString()
	{
		//ASSEMBLE
		list = new PropertyList();
		Location fakeLocation = new FakeLocation();
		list.put("location", fakeLocation, new LocationDataTranslator(FakeXTeam.asTeamPlugin()));
		//ACT
		String location = list.getAsString("location");
		//ASSERT
		Assert.assertEquals(new LocationDataTranslator(FakeXTeam.asTeamPlugin()).decompile(fakeLocation), location);
	}

	@Test
	public void ShouldBeGetPropertyAsType()
	{
		//ASSEMBLE
		list = new PropertyList();
		Location fakeLocation = new FakeLocation();
		list.put("location", fakeLocation, new LocationDataTranslator(FakeXTeam.asTeamPlugin()));
		//ACT
		Location location = new FakeLocation(list.getAsType("location", new LocationDataTranslator(FakeXTeam.asTeamPlugin())));
		//ASSERT
		Assert.assertEquals(fakeLocation, location);
	}

	@Test
	public void ShouldBeRemoveReturnsObject()
	{
		//ASSEMBLE
		list = new PropertyList();
		Property property = new Property("name", "protocos");
		list.put(property);
		//ACT
		Property removedProperty = list.remove("name");
		//ASSERT
		Assert.assertEquals(property, removedProperty);
		Assert.assertFalse(list.contains(property));
	}

	@Test
	public void ShouldBeEquals()
	{
		//ASSEMBLE
		PropertyList list1 = PropertyList.fromString("name:protocos lastAttacked:0 lastTeleported:0");
		PropertyList list2 = PropertyList.fromString("name:protocos lastAttacked:0 lastTeleported:0");
		//ACT
		boolean equals = list1.equals(list2);
		//ASSERT
		Assert.assertTrue(equals);
	}

	@Test
	public void ShouldBeEqualsOutOfOrder()
	{
		//ASSEMBLE
		PropertyList list1 = PropertyList.fromString("name:protocos lastAttacked:0 lastTeleported:0");
		PropertyList list2 = PropertyList.fromString("lastTeleported:0 lastAttacked:0 name:protocos");
		//ACT
		boolean equals = list1.equals(list2);
		//ASSERT
		Assert.assertTrue(equals);
	}

	@Test
	public void ShouldBeUpdateKey()
	{
		//ASSEMBLE
		list.put("name", "protocos");
		//ACT
		boolean updated = list.updateKey("name", "tag");
		//ASSERT
		Assert.assertTrue(updated);
		Assert.assertEquals(new Property("tag", "protocos"), list.get("tag"));
	}

	@Test
	public void ShouldBeUpdateKeyNotExists()
	{
		//ASSEMBLE
		//ACT
		boolean updated = list.updateKey("name", "tag");
		//ASSERT
		Assert.assertFalse(updated);
	}

	@Test
	public void ShouldBePropertyListFromPropertyWithSpace()
	{
		//ASSEMBLE
		list = PropertyList.fromString("name:one tag:one openJoining:false defaultTeam:false timeHeadquartersLastSet:1404097928711 headquarters:My World,-236.0,77.0,-184.0,17.999998,12.0 leader:protocos admins: players:protocos,kmlanglois");
		//ACT
		//ASSERT
		Assert.assertEquals("one", list.getAsString("name"));
		Assert.assertEquals("one", list.getAsString("tag"));
		Assert.assertEquals("false", list.getAsString("openJoining"));
		Assert.assertEquals("false", list.getAsString("defaultTeam"));
		Assert.assertEquals("1404097928711", list.getAsString("timeHeadquartersLastSet"));
		Assert.assertEquals("My World,-236.0,77.0,-184.0,17.999998,12.0", list.getAsString("headquarters"));
		Assert.assertEquals("protocos", list.getAsString("leader"));
		Assert.assertEquals("", list.getAsString("admins"));
		Assert.assertEquals("protocos,kmlanglois", list.getAsString("players"));
	}

	@After
	public void takedown()
	{
	}
}