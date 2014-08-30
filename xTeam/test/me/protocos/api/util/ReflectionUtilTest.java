package me.protocos.api.util;

import java.util.Collection;
import java.util.Map;
import me.protocos.api.collection.OrderedHashMap;
import me.protocos.api.util.CommonUtil;
import me.protocos.api.util.ReflectionUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReflectionUtilTest
{
	@Before
	public void setup()
	{
	}

	@Test
	public void ShouldBeGetMapOfFields()
	{
		//ASSEMBLE
		Map<String, Object> expected = new OrderedHashMap<String, Object>();
		expected.put("name", "protocos");
		expected.put("value", 22);
		TestObject object = new TestObject("protocos", 22);
		//ACT
		Map<String, Object> actual = ReflectionUtil.getPropertiesOf(object);
		//ASSERT
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void ShouldBeDecomposeCompoundObject()
	{
		//ASSEMBLE
		TestObject testObject = new TestObject("protocos", 22);
		CompoundObject compoundObject = new CompoundObject("compound", testObject);
		String expected = "CompoundObject{name=String{value=compound}, object=TestObject{name=String{value=protocos}, value=Integer{value=22}}}";
		//ACT
		String actual = ReflectionUtil.decompose(compoundObject);
		//ASSERT
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void ShouldBeDecomposePrimitiveType()
	{
		//ASSEMBLE
		String expected = "String{value=string}";
		//ACT
		String actual = ReflectionUtil.decompose("string");
		//ASSERT
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void ShouldBeDecomposeObjectWithCollection()
	{
		//ASSEMBLE
		String expected = "ObjectWithCollection{collection=ArrayList{String{value=item1}, String{value=item2}}}";
		ObjectWithCollection collectionObject = new ObjectWithCollection(CommonUtil.toList("item1", "item2"));
		//ACT
		String actual = ReflectionUtil.decompose(collectionObject);
		//ASSERT
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void ShouldBeDecomposeObjectWithMap()
	{
		//ASSEMBLE
		String expected = "ObjectWithMap{map=OrderedHashMap{String{value=key2}=String{value=value2}, String{value=key1}=String{value=value1}}}";
		ObjectWithMap mapObject = new ObjectWithMap(new OrderedHashMap.Builder<String, String>().addEntry("key1", "value1").addEntry("key2", "value2").build());
		//ACT
		String actual = ReflectionUtil.decompose(mapObject);
		//ASSERT
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void ShouldBeDecomposeObjectWithNulls()
	{
		//ASSEMBLE
		String expected = "ObjectWithNulls{name=null, value=Integer{value=0}}";
		ObjectWithNulls object = new ObjectWithNulls();
		//ACT
		String actual = ReflectionUtil.decompose(object);
		//ASSERT
		Assert.assertEquals(expected, actual);
	}

	@After
	public void takedown()
	{
	}
}

class TestObject
{
	private String name;
	private int value;

	public TestObject(String name, int value)
	{
		this.name = name;
		this.value = value;
	}

	public String getName()
	{
		return name;
	}

	public int getValue()
	{
		return value;
	}
}

class CompoundObject
{
	private String name;
	private TestObject object;

	public CompoundObject(String name, TestObject object)
	{
		this.name = name;
		this.object = object;
	}

	public String getName()
	{
		return name;
	}

	public TestObject getObject()
	{
		return object;
	}
}

class ObjectWithCollection
{
	private Collection<String> collection;

	public ObjectWithCollection(Collection<String> collection)
	{
		this.collection = collection;
	}

	public Collection<String> getCollection()
	{
		return collection;
	}
}

class ObjectWithMap
{
	private Map<String, String> map;

	public ObjectWithMap(Map<String, String> map)
	{
		this.map = map;
	}

	public Map<String, String> getCollection()
	{
		return map;
	}
}

class ObjectWithNulls
{
	@SuppressWarnings("unused")
	private String name;
	@SuppressWarnings("unused")
	private int value;
}