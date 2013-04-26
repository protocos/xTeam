package me.protocos.xteam.core.exception;

public class TeamLocationNameAlreadyUsedException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6464456097297544013L;

	public TeamLocationNameAlreadyUsedException()
	{
		super("Location name has already been used");
	}
	public TeamLocationNameAlreadyUsedException(String message)
	{
		super(message);
	}
}
