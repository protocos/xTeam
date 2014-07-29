package me.protocos.xteam.data.translator;

import java.util.List;
import java.util.Set;
import me.protocos.api.util.CommonUtil;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.fakeobjects.FakeXTeam;
import me.protocos.xteam.model.Headquarters;
import org.bukkit.Location;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DataTranslatorFactoryTest
{
	@Before
	public void setup()
	{
	}

	@Test
	public void ShouldBeByteDataTranslator()
	{
		//ASSEMBLE
		IDataTranslator<Byte> dataTranslator = DataTranslatorFactory.getTranslator(Byte.class);
		Byte expected = 0;
		//ACT
		Byte actual = dataTranslator.compile(dataTranslator.decompile(expected));
		//ASSERT
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void ShouldBeBooleanDataTranslator()
	{
		//ASSEMBLE
		IDataTranslator<Boolean> dataTranslator = DataTranslatorFactory.getTranslator(Boolean.class);
		Boolean expected = true;
		//ACT
		Boolean actual = dataTranslator.compile(dataTranslator.decompile(expected));
		//ASSERT
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void ShouldBeCharacterDataTranslator()
	{
		//ASSEMBLE
		IDataTranslator<Character> dataTranslator = DataTranslatorFactory.getTranslator(Character.class);
		Character expected = 'c';
		//ACT
		Character actual = dataTranslator.compile(dataTranslator.decompile(expected));
		//ASSERT
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void ShouldBeDoubleDataTranslator()
	{
		//ASSEMBLE
		IDataTranslator<Double> dataTranslator = DataTranslatorFactory.getTranslator(Double.class);
		Double expected = 10.1D;
		//ACT
		Double actual = dataTranslator.compile(dataTranslator.decompile(expected));
		//ASSERT
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void ShouldBeFloatDataTranslator()
	{
		//ASSEMBLE
		IDataTranslator<Float> dataTranslator = DataTranslatorFactory.getTranslator(Float.class);
		Float expected = 10.1F;
		//ACT
		Float actual = dataTranslator.compile(dataTranslator.decompile(expected));
		//ASSERT
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void ShouldBeIntegerDataTranslator()
	{
		//ASSEMBLE
		IDataTranslator<Integer> dataTranslator = DataTranslatorFactory.getTranslator(Integer.class);
		Integer expected = 10;
		//ACT
		Integer actual = dataTranslator.compile(dataTranslator.decompile(expected));
		//ASSERT
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void ShouldBeLongDataTranslator()
	{
		//ASSEMBLE
		IDataTranslator<Long> dataTranslator = DataTranslatorFactory.getTranslator(Long.class);
		Long expected = 10L;
		//ACT
		Long actual = dataTranslator.compile(dataTranslator.decompile(expected));
		//ASSERT
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void ShouldBeShortDataTranslator()
	{
		//ASSEMBLE
		IDataTranslator<Short> dataTranslator = DataTranslatorFactory.getTranslator(Short.class);
		Short expected = 10;
		//ACT
		Short actual = dataTranslator.compile(dataTranslator.decompile(expected));
		//ASSERT
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void ShouldBeStringDataTranslator()
	{
		//ASSEMBLE
		IDataTranslator<String> dataTranslator = DataTranslatorFactory.getTranslator(String.class);
		String expected = "String";
		//ACT
		String actual = dataTranslator.compile(dataTranslator.decompile(expected));
		//ASSERT
		Assert.assertEquals(expected, actual);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void ShouldBeNullDataTranslator()
	{
		//ASSEMBLE
		IDataTranslator<Object> dataTranslator = DataTranslatorFactory.getTranslator(Object.class);
		Object expected = "NullPointerException";
		//ACT
		dataTranslator.compile(dataTranslator.decompile(expected));
		//ASSERT
	}

	@Test
	public void ShouldBeLocationDataTranslator()
	{
		//ASSEMBLE
		IDataTranslator<Location> dataTranslator = DataTranslatorFactory.getTranslator(Location.class, FakeXTeam.asTeamPlugin());
		Location expected = new FakeLocation();
		//ACT
		Location actual = new FakeLocation(dataTranslator.compile(dataTranslator.decompile(expected)));
		//ASSERT
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void ShouldBeHeadquartersDataTranslator()
	{
		//ASSEMBLE
		IDataTranslator<Headquarters> dataTranslator = DataTranslatorFactory.getTranslator(Headquarters.class, FakeXTeam.asTeamPlugin());
		Headquarters expected = new Headquarters(FakeXTeam.asTeamPlugin(), new FakeLocation());
		//ACT
		Headquarters actual = dataTranslator.compile(dataTranslator.decompile(expected));
		//ASSERT
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void ShouldBeSetDataTranslator()
	{
		//ASSEMBLE
		IDataTranslator<Set<String>> dataTranslator = DataTranslatorFactory.getSetTranslator();
		Set<String> expected = CommonUtil.emptySet();
		expected.add("item1");
		expected.add("item2");
		//ACT
		Set<String> actual = dataTranslator.compile(dataTranslator.decompile(expected));
		//ASSERT
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void ShouldBeListDataTranslator()
	{
		//ASSEMBLE
		IDataTranslator<List<String>> dataTranslator = DataTranslatorFactory.getListTranslator();
		List<String> expected = CommonUtil.emptyList();
		expected.add("item1");
		expected.add("item2");
		//ACT
		List<String> actual = dataTranslator.compile(dataTranslator.decompile(expected));
		//ASSERT
		Assert.assertEquals(expected, actual);
	}

	@After
	public void takedown()
	{
	}
}