package me.protocos.xteam.data;

public interface IDataManager
{
	public abstract void open();

	public abstract boolean isOpen();

	public abstract void read();

	public abstract void write();

	public abstract void close();
}
