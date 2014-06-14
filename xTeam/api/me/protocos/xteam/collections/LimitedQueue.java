package me.protocos.xteam.collections;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class LimitedQueue<T> extends AbstractQueue<T>
{
	private Queue<T> data;
	private int maxSize;
	private T last;

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
	public boolean add(T e)
	{
		throw new AssertionError("Method not allowed.");
	}

	@Override
	public boolean offer(T element)
	{
		if (data.size() >= maxSize)
			data.poll();
		if (data.offer(element))
			last = element;
		return last == element;
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

	public T getFirst()
	{
		return data.peek();
	}

	public T getLast()
	{
		return last;
	}

	@Override
	public int size()
	{
		return maxSize;
	}

	@Override
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
