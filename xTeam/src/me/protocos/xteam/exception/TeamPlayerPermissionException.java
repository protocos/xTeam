package me.protocos.xteam.exception;


public class TeamPlayerPermissionException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9030393284934848456L;
	public TeamPlayerPermissionException()
	{
		super("Permission Denied");
	}
	public TeamPlayerPermissionException(String message)
	{
		super(message);
	}
}
