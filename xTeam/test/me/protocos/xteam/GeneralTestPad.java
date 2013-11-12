package me.protocos.xteam;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.plugin.InvalidDescriptionException;
import org.junit.Test;

public class GeneralTestPad
{
	@Test
	public void test() throws FileNotFoundException, InvalidDescriptionException
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
}