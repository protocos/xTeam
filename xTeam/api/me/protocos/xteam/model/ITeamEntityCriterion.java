package me.protocos.xteam.model;

import me.protocos.xteam.entity.ITeamEntity;

public interface ITeamEntityCriterion
{
	public abstract boolean passes(ITeamEntity entity1, ITeamEntity teamEntity2);
}
