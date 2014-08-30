package me.protocos.api.exception;

import me.protocos.api.exception.ObjectStateException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ObjectStateExceptionTest
{
	@Before
	public void setup()
	{
	}

	@Test
	public void ShouldBeObjectStateExceptionMessageContainsDecomposedObject()
	{
		//ASSEMBLE
		try
		{
			throw new ObjectStateException(null);
		}
		catch (ObjectStateException e)
		{
			e.printStackTrace();
		}
		//ACT
		//ASSERT
		Assert.assertEquals(true, false);
	}

	@After
	public void takedown()
	{
	}
}