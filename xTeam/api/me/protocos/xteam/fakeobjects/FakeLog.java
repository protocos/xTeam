package me.protocos.xteam.fakeobjects;

import me.protocos.api.collection.LimitedQueue;
import me.protocos.xteam.model.ILog;

public class FakeLog implements ILog
{
	private LimitedQueue<String> messageLog;

	public FakeLog()
	{
		super();
		messageLog = new LimitedQueue<String>(50);
	}

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
		messageLog.offer(message);
	}

	@Override
	public void exception(Exception e)
	{
		messageLog.offer(e.getMessage());
	}

	@Override
	public void info(String message)
	{
	}

	@Override
	public void write(String message)
	{
	}

	@Override
	public String getLastMessages()
	{
		return messageLog.toString();
	}

	@Override
	public LimitedQueue<String> getMessages()
	{
		return messageLog;
	}

	@Override
	public void clearMessages()
	{
		messageLog.clear();
	}
}
