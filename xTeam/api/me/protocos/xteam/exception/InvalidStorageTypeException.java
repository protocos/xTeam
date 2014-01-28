package me.protocos.xteam.exception;

public class InvalidStorageTypeException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8796010439836508323L;

	public InvalidStorageTypeException()
	{
		super();
	}

	public InvalidStorageTypeException(String message)
	{
		super(message);
	}
}
