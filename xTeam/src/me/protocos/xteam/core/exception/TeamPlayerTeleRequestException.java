package me.protocos.xteam.core.exception;

public class TeamPlayerTeleRequestException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -192275546414479683L;
	public TeamPlayerTeleRequestException()
	{
		super("Player already put in request to teleport");
	}
	public TeamPlayerTeleRequestException(String message)
	{
		super(message);
	}
}
