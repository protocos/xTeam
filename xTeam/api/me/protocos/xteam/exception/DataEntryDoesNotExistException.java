package me.protocos.xteam.exception;

public class DataEntryDoesNotExistException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3538077395159400598L;

	public DataEntryDoesNotExistException()
	{
		super();
	}

	public DataEntryDoesNotExistException(String message)
	{
		super(message);
	}
}
