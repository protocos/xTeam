package me.protocos.xteam.core.exception;

public class TeamOrPlayerDoesNotExistException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6452535330840358689L;

	public TeamOrPlayerDoesNotExistException()
	{
		super("Team or player does not exist");
	}
	public TeamOrPlayerDoesNotExistException(String message)
	{
		super(message);
	}
}
