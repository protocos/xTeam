package me.protocos.xteam.data.translator;

import java.util.Set;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.fakeobjects.FakeXTeam;
import me.protocos.xteam.model.Headquarters;
import me.protocos.xteam.model.IHeadquarters;
import me.protocos.xteam.model.NullHeadquarters;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.Location;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IDataTranslatorTest
{
	private TeamPlugin teamPlugin;
	private BukkitUtil bukkitUtil;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		bukkitUtil = teamPlugin.getBukkitUtil();
	}

	@Test
	public void ShouldBeBooleanTranslator()
	{
		//ASSEMBLE
		Boolean original = true;
		IDataTranslator<Boolean> strategy = new BooleanDataTranslator();
		//ACT
		String decompiled = strategy.decompile(original);
		Boolean compiled = strategy.compile(decompiled);
		//ASSERT
		Assert.assertEquals(original, compiled);
	}

	@Test
	public void ShouldBeByteTranslator()
	{
		//ASSEMBLE
		Byte original = 10;
		IDataTranslator<Byte> strategy = new ByteDataTranslator();
		//ACT
		String decompiled = strategy.decompile(original);
		Byte compiled = strategy.compile(decompiled);
		//ASSERT
		Assert.assertEquals(original, compiled);
	}

	@Test
	public void ShouldBeShortTranslator()
	{
		//ASSEMBLE
		Short original = 10;
		IDataTranslator<Short> strategy = new ShortDataTranslator();
		//ACT
		String decompiled = strategy.decompile(original);
		Short compiled = strategy.compile(decompiled);
		//ASSERT
		Assert.assertEquals(original, compiled);
	}

	@Test
	public void ShouldBeIntegerTranslator()
	{
		//ASSEMBLE
		Integer original = 10;
		IDataTranslator<Integer> strategy = new IntegerDataTranslator();
		//ACT
		String decompiled = strategy.decompile(original);
		Integer compiled = strategy.compile(decompiled);
		//ASSERT
		Assert.assertEquals(original, compiled);
	}

	@Test
	public void ShouldBeLongTranslator()
	{
		//ASSEMBLE
		Long original = 10L;
		IDataTranslator<Long> strategy = new LongDataTranslator();
		//ACT
		String decompiled = strategy.decompile(original);
		Long compiled = strategy.compile(decompiled);
		//ASSERT
		Assert.assertEquals(original, compiled);
	}

	@Test
	public void ShouldBeFloatTranslator()
	{
		//ASSEMBLE
		Float original = 10.0F;
		IDataTranslator<Float> strategy = new FloatDataTranslator();
		//ACT
		String decompiled = strategy.decompile(original);
		Float compiled = strategy.compile(decompiled);
		//ASSERT
		Assert.assertEquals(original, compiled);
	}

	@Test
	public void ShouldBeDoubleTranslator()
	{
		//ASSEMBLE
		Double original = 10.0;
		IDataTranslator<Double> strategy = new DoubleDataTranslator();
		//ACT
		String decompiled = strategy.decompile(original);
		Double compiled = strategy.compile(decompiled);
		//ASSERT
		Assert.assertEquals(original, compiled);
	}

	@Test
	public void ShouldBeCharacterTranslator()
	{
		//ASSEMBLE
		Character original = ' ';
		IDataTranslator<Character> strategy = new CharacterDataTranslator();
		//ACT
		String decompiled = strategy.decompile(original);
		Character compiled = strategy.compile(decompiled);
		//ASSERT
		Assert.assertEquals(original, compiled);
	}

	@Test
	public void ShouldBeLocationTranslator()
	{
		//ASSEMBLE
		Location original = new FakeLocation(bukkitUtil.getWorld("world")).toLocation();
		IDataTranslator<Location> strategy = new LocationDataTranslator(teamPlugin);
		//ACT
		String decompiled = strategy.decompile(original);
		Location compiled = strategy.compile(decompiled);
		//ASSERT
		Assert.assertEquals(original, compiled);
	}

	@Test
	public void ShouldBeNullHeadquartersTranslator()
	{
		//ASSEMBLE
		IHeadquarters original = new NullHeadquarters();
		IDataTranslator<IHeadquarters> strategy = new HeadquartersDataTranslator(teamPlugin);
		//ACT
		String decompiled = strategy.decompile(original);
		IHeadquarters compiled = strategy.compile(decompiled);
		//ASSERT
		Assert.assertEquals(original, compiled);
	}

	@Test
	public void ShouldBeHeadquartersTranslator()
	{
		//ASSEMBLE
		IHeadquarters original = new Headquarters(teamPlugin, new FakeLocation(bukkitUtil.getWorld("world")).toLocation());
		IDataTranslator<IHeadquarters> strategy = new HeadquartersDataTranslator(teamPlugin);
		//ACT
		String decompiled = strategy.decompile(original);
		IHeadquarters compiled = strategy.compile(decompiled);
		//ASSERT
		Assert.assertEquals(original, compiled);
	}

	@Test
	public void ShouldBeSetTranslator()
	{
		//ASSEMBLE
		Set<String> original = CommonUtil.emptySet();
		original.add("string1");
		original.add("string2");
		IDataTranslator<Set<String>> strategy = new SetDataTranslator();
		//ACT
		String decompiled = strategy.decompile(original);
		Set<String> compiled = strategy.compile(decompiled);
		//ASSERT
		Assert.assertEquals(original, compiled);
	}

	@Test
	public void ShouldBeEmptySetTranslator()
	{
		//ASSEMBLE
		Set<String> original = CommonUtil.emptySet();
		IDataTranslator<Set<String>> strategy = new SetDataTranslator();
		//ACT
		String decompiled = strategy.decompile(original);
		Set<String> compiled = strategy.compile(decompiled);
		//ASSERT
		Assert.assertEquals(original, compiled);
	}

	@Test
	public void ShouldBeEmptySetFromStringTranslator()
	{
		//ASSEMBLE
		IDataTranslator<Set<String>> strategy = new SetDataTranslator();
		//ACT
		Set<String> compiled = strategy.compile("");
		//ASSERT
		Assert.assertEquals(CommonUtil.emptySet(), compiled);
	}

	@After
	public void takedown()
	{
	}
}