package me.protocos.xteam.data;

import me.protocos.xteam.data.translators.IDataTranslator;

public interface IPlayerData extends IDataManager
{
	public abstract void initializePlayerData();

	public abstract void clearPlayerData();

	public abstract <T> void setVariable(String playerName, String variableName, T variable, IDataTranslator<T> strategy);

	public abstract <T> T getVariable(String playerName, String variableName, IDataTranslator<T> strategy);
}
