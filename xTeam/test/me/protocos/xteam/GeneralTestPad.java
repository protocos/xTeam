package me.protocos.xteam;

import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import me.protocos.xteam.util.CommonUtil;
import org.junit.Test;

public class GeneralTestPad
{
	@Test
	public void test()
	{
		//		Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).addHandler(new LogHandler(xTeam.getInstance()));
		//		Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).severe("Error occurred while enabling xTeam v1.7.8 (Is it up to date?)\n" +
		//				"java.lang.NullPointerException\n" +
		//				"	at me.protocos.xteam.xTeamPlugin.onEnable(xTeamPlugin.java:26)\n" +
		//				"	at org.bukkit.plugin.java.JavaPlugin.setEnabled(JavaPlugin.java:217)\n" +
		//				"	at org.bukkit.plugin.java.JavaPluginLoader.enablePlugin(JavaPluginLoader.java:457)\n" +
		//				"	at org.bukkit.plugin.SimplePluginManager.enablePlugin(SimplePluginManager.java:381)\n" +
		//				"	at org.bukkit.craftbukkit.v1_6_R3.CraftServer.loadPlugin(CraftServer.java:284)\n" +
		//				"	at org.bukkit.craftbukkit.v1_6_R3.CraftServer.enablePlugins(CraftServer.java:266)\n" +
		//				"	at net.minecraft.server.v1_6_R3.MinecraftServer.l(MinecraftServer.java:315)\n" +
		//				"	at net.minecraft.server.v1_6_R3.MinecraftServer.f(MinecraftServer.java:292)\n" +
		//				"	at net.minecraft.server.v1_6_R3.MinecraftServer.a(MinecraftServer.java:252)\n" +
		//				"	at net.minecraft.server.v1_6_R3.DedicatedServer.init(DedicatedServer.java:152)\n" +
		//				"	at net.minecraft.server.v1_6_R3.MinecraftServer.run(MinecraftServer.java:393)\n" +
		//				"	at net.minecraft.server.v1_6_R3.ThreadServerApplication.run(SourceFile:583)\n" +
		//				"14:25:11 [INFO] Server permissions file permissions.yml is empty, ignoring it");
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