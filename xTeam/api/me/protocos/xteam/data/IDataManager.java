package me.protocos.xteam.data;

import me.protocos.xteam.data.translator.IDataTranslator;

public interface IDataManager
{
	public abstract void open();

	public abstract void read();

	public abstract boolean isOpen();

	public abstract void initializeData();

	public abstract void clearData();

	public abstract void write();

	public abstract void close();

	public abstract <T> void setVariable(String key, String propertyName, T variable, IDataTranslator<T> strategy);

	public abstract <T> T getVariable(String key, String propertyName, IDataTranslator<T> strategy);
}
