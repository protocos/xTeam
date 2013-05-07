package me.protocos.xteam.util;

public interface ILog
{
	public abstract void debug(String message);
	public abstract void info(String message);
	public abstract void error(String message);
	public abstract void fatal(String message);
	public abstract void custom(String message);
	public abstract void write(String message);
	public abstract void close();
}
