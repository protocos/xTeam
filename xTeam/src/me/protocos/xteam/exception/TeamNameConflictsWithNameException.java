package me.protocos.xteam.exception;

public class TeamNameConflictsWithNameException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3974395143383574634L;

	public TeamNameConflictsWithNameException()
	{
		super("Team name conflicts with other team name");
	}
	public TeamNameConflictsWithNameException(String message)
	{
		super(message);
	}
}
