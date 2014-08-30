package me.protocos.api.exception;

import me.protocos.api.util.ReflectionUtil;

public class ObjectStateException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7019752843168829256L;

	@SuppressWarnings("unused")
	private ObjectStateException()
	{
		super();
	}

	public ObjectStateException(Object object)
	{
		super(ReflectionUtil.decompose(object));
	}
}
