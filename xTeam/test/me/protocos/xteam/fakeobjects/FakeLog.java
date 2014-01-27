package me.protocos.xteam.fakeobjects;

import me.protocos.xteam.model.ILog;

public class FakeLog implements ILog
{

	@Override
	public void close()
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
	public void info(String message)
	{
	}

	@Override
	public void write(String message)
	{
	}

}
