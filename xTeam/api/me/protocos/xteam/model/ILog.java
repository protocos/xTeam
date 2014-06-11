package me.protocos.xteam.model;

import me.protocos.xteam.message.IMessageRecorder;

public interface ILog extends IMessageRecorder
{
	public abstract void close();

	public abstract void debug(String message);

	public abstract void error(String message);

	public abstract void exception(Exception e);

	public abstract void info(String message);

	public abstract void write(String message);
}
