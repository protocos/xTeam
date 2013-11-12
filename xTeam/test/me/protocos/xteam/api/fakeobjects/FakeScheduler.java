package me.protocos.xteam.api.fakeobjects;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scheduler.BukkitWorker;

public class FakeScheduler implements BukkitScheduler
{

	@Override
	public <T> Future<T> callSyncMethod(Plugin arg0, Callable<T> arg1)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cancelAllTasks()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void cancelTask(int arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void cancelTasks(Plugin arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public List<BukkitWorker> getActiveWorkers()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BukkitTask> getPendingTasks()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCurrentlyRunning(int arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isQueued(int arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BukkitTask runTask(Plugin arg0, Runnable arg1) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BukkitTask runTaskAsynchronously(Plugin arg0, Runnable arg1) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BukkitTask runTaskLater(Plugin arg0, Runnable arg1, long arg2) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BukkitTask runTaskLaterAsynchronously(Plugin arg0, Runnable arg1, long arg2) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BukkitTask runTaskTimer(Plugin arg0, Runnable arg1, long arg2, long arg3) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BukkitTask runTaskTimerAsynchronously(Plugin arg0, Runnable arg1, long arg2, long arg3) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Deprecated
	public int scheduleAsyncDelayedTask(Plugin arg0, Runnable arg1)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Deprecated
	public int scheduleAsyncDelayedTask(Plugin arg0, Runnable arg1, long arg2)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Deprecated
	public int scheduleAsyncRepeatingTask(Plugin arg0, Runnable arg1, long arg2, long arg3)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int scheduleSyncDelayedTask(Plugin arg0, Runnable arg1)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int scheduleSyncDelayedTask(Plugin arg0, Runnable arg1, long arg2)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int scheduleSyncRepeatingTask(Plugin arg0, Runnable arg1, long arg2, long arg3)
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
