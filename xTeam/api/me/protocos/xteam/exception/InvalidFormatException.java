package me.protocos.xteam.exception;

public class InvalidFormatException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8796010439836508323L;

	public InvalidFormatException()
	{
		super();
	}

	public InvalidFormatException(String message)
	{
		super(message);
	}

	public InvalidFormatException(String invalidValue, String expectedFormat)
	{
		super("Invalid format: \"" + invalidValue + "\" does not match \"" + expectedFormat + "\" pair");
	}
}
