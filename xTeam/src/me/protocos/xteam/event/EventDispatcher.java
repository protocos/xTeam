package me.protocos.xteam.event;

import java.lang.reflect.Method;
import java.util.List;
import me.protocos.xteam.util.CommonUtil;

public class EventDispatcher implements IEventDispatcher
{
	private static List<IEventHandler> listeners;

	public EventDispatcher()
	{
		listeners = CommonUtil.emptyList();
	}

	@Override
	public void addTeamListener(IEventHandler listener)
	{
		listeners.add(listener);
	}

	@Override
	public void removeTeamListener(IEventHandler listener)
	{
		listeners.remove(listener);
	}

	@Override
	public void dispatchEvent(ITeamEvent event)
	{
		for (IEventHandler listener : listeners)
		{
			dispatchEventTo(event, listener);
		}
	}

	private void dispatchEventTo(ITeamEvent event, IEventHandler listener)
	{
		List<Method> methods = getAllMethodsWithEventAnnotation(listener);
		for (Method method : methods)
		{
			try
			{
				method.setAccessible(true);
				Class<?>[] parameters = method.getParameterTypes();

				if (parameters.length == 1 && parameters[0].equals(event.getClass()))
				{
					method.invoke(listener, event);
				}
			}
			catch (Exception e)
			{
				System.err.println("Could not invoke event handler!");
				e.printStackTrace(System.err);
			}
		}
	}

	private List<Method> getAllMethodsWithEventAnnotation(IEventHandler listener)
	{
		Method[] methods = listener.getClass().getDeclaredMethods();
		List<Method> finalMethods = CommonUtil.emptyList();
		for (Method method : methods)
		{
			if (method.getAnnotation(TeamEvent.class) != null)
			{
				finalMethods.add(method);
			}
		}
		return finalMethods;
	}
}
