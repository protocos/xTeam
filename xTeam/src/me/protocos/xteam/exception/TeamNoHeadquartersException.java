package me.protocos.xteam.exception;

public class TeamNoHeadquartersException extends TeamException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2005748079450062596L;

	public TeamNoHeadquartersException()
	{
		super("ITeam has no headquarters");
	}
	public TeamNoHeadquartersException(String message)
	{
		super(message);
	}
}
