package me.protocos.xteam.message;

import me.protocos.xteam.collections.LimitedQueue;

public interface IMessageRecorder
{
	public abstract String getLastMessage();

	public abstract String getAllMessages();

	public abstract LimitedQueue<String> getMessages();
}
