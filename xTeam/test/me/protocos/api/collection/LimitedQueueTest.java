package me.protocos.api.collection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LimitedQueueTest
{
	private LimitedQueue<Integer> limitedQueue;

	@Before
	public void setup()
	{
		limitedQueue = new LimitedQueue<Integer>(10);
	}

	@Test
	public void ShouldBeGetFirst()
	{
		//ASSEMBLE
		offerElevenObjects();
		//ACT
		Integer first = limitedQueue.getFirst();
		//ASSERT
		Assert.assertEquals(new Integer(2), first);
	}

	@Test
	public void ShouldBeGetLast()
	{
		//ASSEMBLE
		offerElevenObjects();
		//ACT
		Integer first = limitedQueue.getLast();
		//ASSERT
		Assert.assertEquals(new Integer(11), first);
	}

	private void offerElevenObjects()
	{
		limitedQueue.offer(1);
		limitedQueue.offer(2);
		limitedQueue.offer(3);
		limitedQueue.offer(4);
		limitedQueue.offer(5);
		limitedQueue.offer(6);
		limitedQueue.offer(7);
		limitedQueue.offer(8);
		limitedQueue.offer(9);
		limitedQueue.offer(10);
		limitedQueue.offer(11);
	}

	@After
	public void takedown()
	{
	}
}