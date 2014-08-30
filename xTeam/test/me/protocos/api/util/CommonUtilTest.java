package me.protocos.api.util;

import static me.protocos.api.util.CommonUtil.*;
import java.util.ArrayList;
import java.util.List;
import me.protocos.api.collection.OrderedHashMap;
import me.protocos.api.util.CommonUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CommonUtilTest
{
	@Before
	public void setup()
	{
	}

	@Test
	public void assignFromType() throws IncompatibleClassChangeError
	{
		Number number = new Double(10);
		Double fromNumber = CommonUtil.assignFromType(number, Double.class);
		Assert.assertEquals(10.0, fromNumber, 0);
	}

	@Test
	public void subListOfType()
	{
		List<Number> list = new ArrayList<Number>();
		list.add(new Double(10));
		list.add(new Integer(12));
		list.add(new Long(14));
		List<Integer> newList = CommonUtil.subListOfType(list, Integer.class);
		Assert.assertTrue(newList.contains(12));
		Assert.assertEquals(1, newList.size());
	}

	@Test
	public void ShouldBeConcatenate()
	{
		//ASSEMBLE
		String[] string = { "hello", "world" };
		//ACT
		String concat = concatenate(string);
		//ASSERT
		Assert.assertEquals("hello world", concat);
	}

	@Test
	public void ShouldBeConcatenateGlue()
	{
		//ASSEMBLE
		String[] string = { "hello", "world" };
		//ACT
		String concat = concatenate(string, ",");
		//ASSERT
		Assert.assertEquals("hello,world", concat);
	}

	@Test
	public void ShouldBeMatchesLowerCase()
	{
		//ASSEMBLE
		String string = "HeLlO WoRlD";
		//ACT
		boolean matches = matchesLowerCase(string, "hello world");
		//ASSERT
		Assert.assertEquals(true, matches);
	}

	@Test
	public void ShouldBeMatchesUpperCase()
	{
		//ASSEMBLE
		String string = "HeLlO WoRlD";
		//ACT
		boolean matches = matchesUpperCase(string, "HELLO WORLD");
		//ASSERT
		Assert.assertEquals(true, matches);
	}

	@Test
	public void ShouldBeReverse()
	{
		//ASSEMBLE
		String string = "hello world";
		//ACT
		String reverse = reverse(string);
		//ASSERT
		Assert.assertEquals("dlrow olleh", reverse);
	}

	@Test
	public void ShouldBeInsideRange()
	{
		//ASSEMBLE
		int rangeCheck = 5;
		int lowerBound = 0;
		int upperBound = 10;
		//ACT
		boolean insideRange = insideRange(rangeCheck, lowerBound, upperBound);
		//ASSERT
		Assert.assertTrue(insideRange);
	}

	@Test
	public void ShouldBeNotInsideRange()
	{
		//ASSEMBLE
		int rangeCheck = 5;
		int lowerBound = 0;
		int upperBound = 5;
		//ACT
		boolean insideRange = insideRange(rangeCheck, lowerBound, upperBound);
		//ASSERT
		Assert.assertFalse(insideRange);
	}

	@Test
	public void ShouldBeNotInsideRangeEdgeCase()
	{
		//ASSEMBLE
		int rangeCheck = 5;
		int lowerBound = 5;
		int upperBound = 6;
		//ACT
		boolean insideRange = insideRange(rangeCheck, lowerBound, upperBound);
		//ASSERT
		Assert.assertTrue(insideRange);
	}

	@Test
	public void ShouldBeNotInsideRangeModuloHighValue()
	{
		//ASSEMBLE
		double rangeCheck = 1;
		double lowerBound = 315;
		double upperBound = 45;
		double modulo = 360;
		//ACT
		boolean insideRange = insideRange(rangeCheck, lowerBound, upperBound, modulo);
		//ASSERT
		Assert.assertTrue(insideRange);
	}

	@Test
	public void ShouldBeNotInsideRangeModuloLowValue()
	{
		//ASSEMBLE
		double rangeCheck = 359;
		double lowerBound = 315;
		double upperBound = 45;
		double modulo = 360;
		//ACT
		boolean insideRange = insideRange(rangeCheck, lowerBound, upperBound, modulo);
		//ASSERT
		Assert.assertTrue(insideRange);
	}

	@Test
	public void ShouldBeNotInsideRangeModuloEdgeCaseZero()
	{
		//ASSEMBLE
		double rangeCheck = 0;
		double lowerBound = 315;
		double upperBound = 45;
		double modulo = 360;
		//ACT
		boolean insideRange = insideRange(rangeCheck, lowerBound, upperBound, modulo);
		//ASSERT
		Assert.assertTrue(insideRange);
	}

	@Test
	public void ShouldBeNotInsideRangeModuloEdgeCaseModulo()
	{
		//ASSEMBLE
		double rangeCheck = 360;
		double lowerBound = 315;
		double upperBound = 45;
		double modulo = 360;
		//ACT
		boolean insideRange = insideRange(rangeCheck, lowerBound, upperBound, modulo);
		//ASSERT
		Assert.assertTrue(insideRange);
	}

	@Test
	public void ShouldBeNotInsideRangeModuloNotInRangeLow()
	{
		//ASSEMBLE
		double rangeCheck = 300;
		double lowerBound = 315;
		double upperBound = 45;
		double modulo = 360;
		//ACT
		boolean insideRange = insideRange(rangeCheck, lowerBound, upperBound, modulo);
		//ASSERT
		Assert.assertFalse(insideRange);
	}

	@Test
	public void ShouldBeNotInsideRangeModuloNotInRangeHigh()
	{
		//ASSEMBLE
		double rangeCheck = 60;
		double lowerBound = 315;
		double upperBound = 45;
		double modulo = 360;
		//ACT
		boolean insideRange = insideRange(rangeCheck, lowerBound, upperBound, modulo);
		//ASSERT
		Assert.assertFalse(insideRange);
	}

	@Test
	public void ShouldBeNotInsideRangeModuloRegularRange()
	{
		//ASSEMBLE
		double rangeCheck = 0;
		double lowerBound = 22.5;
		double upperBound = 67.5;
		double modulo = 360;
		//ACT
		boolean insideRange = insideRange(rangeCheck, lowerBound, upperBound, modulo);
		//ASSERT
		Assert.assertFalse(insideRange);
	}

	@Test
	public void ShouldBeRoundDouble()
	{
		//ASSEMBLE
		double number = 3.141592764;
		//ACT
		double rounded = CommonUtil.round(number, 2);
		//ASSERT
		Assert.assertEquals(3.14D, rounded, 0);
	}

	@Test
	public void ShouldBeRoundInt()
	{
		//ASSEMBLE
		double number = 3.141592764;
		//ACT
		int rounded = CommonUtil.round(number);
		//ASSERT
		Assert.assertEquals(3, rounded);
	}

	@Test
	public void ShouldBeSplitList()
	{
		//ASSEMBLE
		String list = "item1, item2";
		//ACT
		List<String> items = CommonUtil.split(list, ", ");
		//ASSERT
		Assert.assertEquals("item1", items.get(0));
		Assert.assertEquals("item2", items.get(1));
	}

	@Test
	public void ShouldBeListToString()
	{
		//ASSEMBLE
		//ACT
		List<String> list = CommonUtil.split("item1, item2", ", ");
		//ASSERT
		Assert.assertEquals("item1\n"
				+ "item2", CommonUtil.toString(list));
	}

	@Test
	public void ShouldBeOrderedHashMapToString()
	{
		//ASSEMBLE
		//ACT
		OrderedHashMap<String, String> map = new OrderedHashMap.Builder<String, String>()
				.addEntry("key1", "value1")
				.addEntry("key2", "value2")
				.build();
		//ASSERT
		Assert.assertEquals("key1 = value1\n"
				+ "key2 = value2", CommonUtil.toString(map));
	}

	@Test
	public void ShouldBeBooleansToList()
	{
		//ASSEMBLE
		//ACT
		//ASSERT
		Assert.assertEquals(true, false);
	}

	@After
	public void takedown()
	{
	}
}