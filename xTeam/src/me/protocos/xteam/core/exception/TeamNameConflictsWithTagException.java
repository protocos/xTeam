package me.protocos.xteam.core.exception;

public class TeamNameConflictsWithTagException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3974395143383574634L;

	public TeamNameConflictsWithTagException()
	{
		super("Team tag conflicts with name");
	}
	public TeamNameConflictsWithTagException(String message)
	{
		super(message);
	}
}
