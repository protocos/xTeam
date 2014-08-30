package me.protocos.api;

import me.protocos.api.collection.OrderedHashMap;
import org.junit.Test;

public class GeneralTestPad
{
	@Test
	public void ShouldBeSomething()
	{
		OrderedHashMap<String, String> map = new OrderedHashMap.Builder<String, String>().addEntry("key1", "value1").addEntry("key2", "value2").build();
		System.out.println(map);
	}
}
