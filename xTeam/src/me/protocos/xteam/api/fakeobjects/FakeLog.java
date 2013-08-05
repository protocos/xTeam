package me.protocos.xteam.api.fakeobjects;

import me.protocos.xteam.api.util.ILog;

public class FakeLog implements ILog
{

	@Override
	public void close()
	{
		System.out.println("In FakeLog close methods");
	}

	@Override
	public void custom(String message)
	{
		System.out.println("Custom: " + message);
	}

	@Override
	public void debug(String message)
	{
		System.out.println("Debug: " + message);
	}

	@Override
	public void error(String message)
	{
		System.out.println("Error: " + message);
	}

	@Override
	public void exception(Exception e)
	{
		e.printStackTrace();
	}

	@Override
	public void fatal(String message)
	{
		System.out.println("Fatal: " + message);
	}

	@Override
	public void info(String message)
	{
		System.out.println("Info: " + message);
	}

	@Override
	public void write(String message)
	{
		System.out.println("In FakeLog write method");
	}

}
