package me.protocos.xteam.exception;

public class TeamDoesNotExistException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6452535330840358689L;

	public TeamDoesNotExistException()
	{
		super("ITeam does not exist");
	}
	public TeamDoesNotExistException(String message)
	{
		super(message);
	}
}
