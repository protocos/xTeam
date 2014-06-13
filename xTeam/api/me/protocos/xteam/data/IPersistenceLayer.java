package me.protocos.xteam.data;

public interface IPersistenceLayer
{
	public abstract void open();

	public abstract void read();

	public abstract void write();

	public abstract void close();
}
