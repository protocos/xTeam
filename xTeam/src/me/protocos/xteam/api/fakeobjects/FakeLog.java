package me.protocos.xteam.api.fakeobjects;

import me.protocos.xteam.api.util.ILog;

public class FakeLog implements ILog
{

	@Override
	public void close()
	{
	}

	@Override
	public void custom(String message)
	{
	}

	@Override
	public void debug(String message)
	{
	}

	@Override
	public void error(String message)
	{
	}

	@Override
	public void exception(Exception e)
	{
		e.printStackTrace();
	}

	@Override
	public void fatal(String message)
	{
	}

	@Override
	public void info(String message)
	{
	}

	@Override
	public void write(String message)
	{
	}

}
