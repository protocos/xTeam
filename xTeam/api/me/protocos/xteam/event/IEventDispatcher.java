package me.protocos.xteam.event;


public interface IEventDispatcher
{
	public abstract void addTeamListener(IEventHandler handler);

	public abstract void removeTeamListener(IEventHandler handler);

	public abstract void dispatchEvent(ITeamEvent event);
}
