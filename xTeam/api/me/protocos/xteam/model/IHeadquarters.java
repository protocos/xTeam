package me.protocos.xteam.model;

import me.protocos.xteam.entity.ITeamEntity;

public interface IHeadquarters extends ILocatable
{
	public abstract String getInfoFor(ITeamEntity entity);

	public abstract boolean isValid();
}
