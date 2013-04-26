package me.protocos.xteam.core.exception;

public class TeamPlayerDoesNotExistException extends TeamException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4858830530535537578L;

	public TeamPlayerDoesNotExistException()
	{
		super("Player does not exist");
	}
	public TeamPlayerDoesNotExistException(String message)
	{
		super(message);
	}
}
