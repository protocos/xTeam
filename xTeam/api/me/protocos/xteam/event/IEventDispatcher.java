package me.protocos.xteam.event;

import me.protocos.xteam.model.ITeamListener;

public interface IEventDispatcher
{
	public abstract void addTeamListener(ITeamListener listener);

	public abstract void removeTeamListener(ITeamListener listener);

	public abstract void dispatchEvent(ITeamEvent event);
}
