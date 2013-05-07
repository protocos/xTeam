package me.protocos.xteam.util;

public class SystemUtil
{
	public static boolean isMac()
	{
		String os = System.getProperty("os.name").toLowerCase();
		return (os.indexOf("mac") >= 0);
	}
	public static boolean isWindows()
	{
		String os = System.getProperty("os.name").toLowerCase();
		return (os.indexOf("win") >= 0);
	}
}
