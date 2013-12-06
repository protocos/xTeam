package me.protocos.xteam.api.model;

public interface ITeamListener
{
	public abstract void onRename();

	public abstract void onCreate();

	public abstract void onDestroy();
}
