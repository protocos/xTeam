package me.protocos.xteam.message;

import me.protocos.api.collection.LimitedQueue;

public interface IMessageRecorder
{
	public abstract void clearMessages();

	public abstract String getLastMessages();

	public abstract LimitedQueue<String> getMessages();
}
