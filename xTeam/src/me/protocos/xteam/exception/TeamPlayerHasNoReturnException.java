package me.protocos.xteam.exception;

public class TeamPlayerHasNoReturnException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3943854581565167674L;
	public TeamPlayerHasNoReturnException()
	{
		super("Player does not have return location");
	}
	public TeamPlayerHasNoReturnException(String message)
	{
		super(message);
	}
}
