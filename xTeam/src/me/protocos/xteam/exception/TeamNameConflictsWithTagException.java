package me.protocos.xteam.exception;

public class TeamNameConflictsWithTagException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3974395143383574634L;

	public TeamNameConflictsWithTagException()
	{
		super("ITeam tag conflicts with name");
	}
	public TeamNameConflictsWithTagException(String message)
	{
		super(message);
	}
}
