package me.protocos.xteam.message;

import me.protocos.xteam.collections.LimitedQueue;

public interface IMessageRecorder
{
	public abstract void clearMessages();

	public abstract String getLastMessages();

	public abstract LimitedQueue<String> getMessages();
}
