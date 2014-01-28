package me.protocos.xteam.exception;

public class DataManagerNotOpenException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3538077395159400598L;

	public DataManagerNotOpenException()
	{
		super();
	}

	public DataManagerNotOpenException(String message)
	{
		super(message);
	}
}
