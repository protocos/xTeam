package me.protocos.xteam.data;

import me.protocos.xteam.data.translators.IDataTranslator;

public interface IDataManager
{
	public abstract void open();

	public abstract boolean isOpen();

	public abstract void initializeData();

	public abstract void clearData();

	public abstract void close();

	public abstract <T> void setVariable(String playerName, String variableName, T variable, IDataTranslator<T> strategy);

	public abstract <T> T getVariable(String playerName, String variableName, IDataTranslator<T> strategy);
}
