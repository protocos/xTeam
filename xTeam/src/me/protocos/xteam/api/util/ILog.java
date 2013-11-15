package me.protocos.xteam.api.util;

public interface ILog
{
	public abstract void close();

	public abstract void debug(String message);

	public abstract void error(String message);

	public abstract void exception(Exception e);

	public abstract void info(String message);

	public abstract void write(String message);
}
