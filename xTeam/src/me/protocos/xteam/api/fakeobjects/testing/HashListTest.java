package me.protocos.xteam.api.fakeobjects.testing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import me.protocos.xteam.api.collections.HashList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HashListTest
{
	HashList<String, String> list;

	@Before
	public void SetUp()
	{
		list = new HashList<String, String>();
	}
	@Test
	public void ShouldBeClear()
	{
		//ASSEMBLE
		list.put("0", "zero");
		list.put("1", "one");
		list.put("2", "two");
		list.put("3", "three");
		list.put("4", "four");
		list.put("5", "five");
		list.put("6", "six");
		//ACT
		list.clear();
		//ASSERT
		Assert.assertEquals("{}", list.toString());
	}
	@Test
	public void ShouldBeGet()
	{
		//ASSEMBLE
		list.put("1", "one");
		//ACT
		String s = list.get("1");
		//ASSERT
		Assert.assertEquals("one", s);
		Assert.assertEquals(1, list.getOrder().size());
		Assert.assertEquals(1, list.size());
	}
	@Test
	public void ShouldBeGetFromIndex()
	{
		//ASSEMBLE
		list.put("1", "one");
		//ACT
		String s = list.get(0);
		//ASSERT
		Assert.assertEquals("one", s);
		Assert.assertEquals(1, list.getOrder().size());
		Assert.assertEquals(1, list.size());
	}
	@Test
	public void ShouldBeGetKeyFromIndex()
	{
		//ASSEMBLE
		list.put("1", "one");
		//ACT
		String s = list.getKey(0);
		//ASSERT
		Assert.assertEquals("1", s);
		Assert.assertEquals(1, list.getOrder().size());
		Assert.assertEquals(1, list.size());
	}
	@Test
	public void ShouldBeInOrder()
	{
		//ASSEMBLE
		list.put("3", "three");
		list.put("4", "four");
		list.put("5", "five");
		list.put("6", "six");
		list.put("0", "zero", 0);
		list.put("1", "one", 1);
		list.put("2", "two", 2);
		//ACT
		//ASSERT
		Assert.assertEquals("{0=zero, 1=one, 2=two, 3=three, 4=four, 5=five, 6=six}", list.toString());
		Assert.assertEquals(7, list.getOrder().size());
		Assert.assertEquals(7, list.size());
	}
	@Test
	public void ShouldBeNotSetOrder()
	{
		HashMap<String, String> map = new HashMap<String, String>();
		//ASSEMBLE
		map.put("0", "zero");
		map.put("1", "one");
		map.put("2", "two");
		map.put("3", "three");
		map.put("4", "four");
		map.put("5", "five");
		map.put("6", "six");
		list.putAll(map);
		//ACT
		ArrayList<String> newOrder = new ArrayList<String>();
		newOrder.add("0");
		newOrder.add("1");
		newOrder.add("2");
		newOrder.add("3");
		newOrder.add("4");
		newOrder.add("5");
		newOrder.add("6");
		newOrder.add("7");
		boolean ordered = list.setOrder(newOrder);
		//ASSERT
		Assert.assertEquals("{3=three, 2=two, 1=one, 0=zero, 6=six, 5=five, 4=four}", list.toString());
		Assert.assertEquals(7, list.getOrder().size());
		Assert.assertEquals(7, list.size());
		Assert.assertFalse(ordered);
	}
	@Test
	public void ShouldBePut()
	{
		//ASSEMBLE
		//ACT
		String s = list.put("1", "one");
		s = list.put("1", "two");
		//ASSERT
		Assert.assertEquals("one", s);
		Assert.assertEquals(1, list.getOrder().size());
		Assert.assertEquals(1, list.size());
	}
	@Test
	public void ShouldBePutAll()
	{
		HashMap<String, String> map = new HashMap<String, String>();
		//ASSEMBLE
		map.put("0", "zero");
		map.put("1", "one");
		map.put("2", "two");
		map.put("3", "three");
		map.put("4", "four");
		map.put("5", "five");
		map.put("6", "six");
		//ACT
		list.putAll(map);
		list.putAll(map);
		//ASSERT
		Assert.assertEquals("{3=three, 2=two, 1=one, 0=zero, 6=six, 5=five, 4=four}", list.toString());
		Assert.assertEquals(7, list.getOrder().size());
		Assert.assertEquals(7, list.size());
	}
	@Test
	public void ShouldBeRemove()
	{
		//ASSEMBLE
		list.put("1", "one");
		//ACT
		String s = list.remove("1");
		//ASSERT
		Assert.assertEquals("one", s);
		Assert.assertEquals(0, list.getOrder().size());
		Assert.assertEquals(0, list.size());
	}
	@Test
	public void ShouldBeSetOrder()
	{
		HashMap<String, String> map = new HashMap<String, String>();
		//ASSEMBLE
		map.put("0", "zero");
		map.put("1", "one");
		map.put("2", "two");
		map.put("3", "three");
		map.put("4", "four");
		map.put("5", "five");
		map.put("6", "six");
		list.putAll(map);
		//ACT
		ArrayList<String> newOrder = new ArrayList<String>();
		newOrder.add("0");
		newOrder.add("1");
		newOrder.add("2");
		newOrder.add("3");
		newOrder.add("4");
		newOrder.add("5");
		newOrder.add("6");
		boolean ordered = list.setOrder(newOrder);
		//ASSERT
		Assert.assertEquals("{0=zero, 1=one, 2=two, 3=three, 4=four, 5=five, 6=six}", list.toString());
		Assert.assertEquals(7, list.getOrder().size());
		Assert.assertEquals(7, list.size());
		Assert.assertTrue(ordered);
	}
	@Test
	public void ShouldBeSort()
	{
		HashMap<String, String> map = new HashMap<String, String>();
		//ASSEMBLE
		map.put("0", "zero");
		map.put("1", "one");
		map.put("2", "two");
		map.put("3", "three");
		map.put("4", "four");
		map.put("5", "five");
		map.put("6", "six");
		list.putAll(map);
		//ACT
		ArrayList<String> order = list.getOrder();
		Collections.sort(order);
		boolean ordered = list.setOrder(order);
		//ASSERT
		Assert.assertEquals("{0=zero, 1=one, 2=two, 3=three, 4=four, 5=five, 6=six}", list.toString());
		Assert.assertEquals(7, list.getOrder().size());
		Assert.assertEquals(7, list.size());
		Assert.assertTrue(ordered);
	}
	@Test
	public void ShouldBeSorted()
	{
		//ASSEMBLE
		list.setKeepSorted(true);
		list.put("6", "six");
		list.put("4", "four");
		list.put("1", "one");
		list.put("0", "zero");
		list.put("5", "five");
		list.put("3", "three");
		list.put("2", "two");
		//ACT
		//ASSERT
		Assert.assertEquals("{0=zero, 1=one, 2=two, 3=three, 4=four, 5=five, 6=six}", list.toString());
	}
	@Test
	public void ShouldBeUpdateKey()
	{
		//ASSEMBLE
		list.put("0", "zero");
		list.put("1", "one");
		list.put("2", "two");
		list.put("3", "three");
		list.put("4", "four");
		list.put("5", "five");
		list.put("6", "six");
		//ACT
		boolean updated = list.updateKey("0", "ZERO");
		//ASSERT
		Assert.assertEquals("{ZERO=zero, 1=one, 2=two, 3=three, 4=four, 5=five, 6=six}", list.toString());
		Assert.assertTrue(updated);
	}
	//	@Test
	//	public void ShouldBeContainsKeyIgnoreCase()
	//	{
	//		//ASSEMBLE
	//		list.put("ooo", "zero");
	//		list.put("1", "one");
	//		list.put("2", "two");
	//		list.put("3", "three");
	//		list.put("4", "four");
	//		list.put("5", "five");
	//		list.put("6", "six");
	//		//ACT
	//		boolean contains = list.containsKeyIgnoreCase("OoO");
	//		//ASSERT
	//		Assert.assertEquals("{ooo=zero, 1=one, 2=two, 3=three, 4=four, 5=five, 6=six}", list.toString());
	//		Assert.assertTrue(contains);
	//	}
	@Test
	public void ShouldBeUpdateKeyWithoutKey()
	{
		//ASSEMBLE
		list.put("0", "zero");
		list.put("1", "one");
		list.put("2", "two");
		list.put("3", "three");
		list.put("4", "four");
		list.put("5", "five");
		list.put("6", "six");
		//ACT
		boolean updated = list.updateKey("00", "ZERO");
		//ASSERT
		Assert.assertEquals("{0=zero, 1=one, 2=two, 3=three, 4=four, 5=five, 6=six}", list.toString());
		Assert.assertFalse(updated);
	}
}
