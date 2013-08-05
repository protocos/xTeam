package me.protocos.xteam.api.collections;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class LimitedQueue<T> extends AbstractQueue<T>
{
	private Queue<T> data;
	private int maxSize;

	public LimitedQueue(int maxSize)
	{
		data = new LinkedList<T>();
		this.maxSize = maxSize;
	}

	@Override
	public Iterator<T> iterator()
	{
		return data.iterator();
	}

	@Override
	public boolean offer(T element)
	{
		if (data.size() >= maxSize)
			data.poll();
		return data.offer(element);
	}

	@Override
	public T peek()
	{
		return data.peek();
	}

	@Override
	public T poll()
	{
		return data.poll();
	}

	@Override
	public int size()
	{
		return maxSize;
	}

	public String toString()
	{
		String result = "";
		for (T element : data)
		{
			result += element + "\n";
		}
		return result;
	}
}
