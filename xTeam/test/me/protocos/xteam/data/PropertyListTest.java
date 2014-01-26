package me.protocos.xteam.data;

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

	@After
	public void takedown()
	{
	}
}