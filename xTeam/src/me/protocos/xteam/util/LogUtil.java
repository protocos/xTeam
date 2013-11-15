package me.protocos.xteam.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.util.Scanner;
import me.protocos.xteam.api.TeamPlugin;
import me.protocos.xteam.api.collections.LimitedQueue;
import me.protocos.xteam.api.util.ILog;
import me.protocos.xteam.core.Configuration;

public class LogUtil implements ILog
{
	private final String packageString;
	private ErrorReportUtil errorReporter;
	private PrintStream printStream;

	public LogUtil(String folderPath, TeamPlugin teamPlugin)
	{
		String pluginPackageID = teamPlugin.getClass().getPackage().toString();
		this.packageString = pluginPackageID.substring(pluginPackageID.indexOf(' ') + 1, pluginPackageID.lastIndexOf('.') + 1);
		String pluginName = teamPlugin.getPluginName();
		SystemUtil.ensureFolder(pluginName);
		File file = SystemUtil.ensureFile(folderPath + "/" + pluginName + ".log");
		try
		{
			LimitedQueue<String> previousEntries = new LimitedQueue<String>(500);
			Scanner sc = new Scanner(file);
			String line;
			while (sc.hasNext() && (line = sc.nextLine()) != null)
			{
				previousEntries.offer(line);
			}
			FileOutputStream output = new FileOutputStream(file);
			printStream = new PrintStream(output);
			printStream.print(previousEntries.toString());
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		errorReporter = new ErrorReportUtil(teamPlugin);
	}

	public void close()
	{
		printStream.close();
	}

	public void debug(String message)
	{
		message = "[DEBUG] " + message;
		write(message);
	}

	public void error(String message)
	{
		message = "[ERROR] " + message;
		write(message);
		print(message);
	}

	public void exception(final Exception e)
	{
		error(e.toString());
		for (StackTraceElement elem : e.getStackTrace())
		{
			if (elem.toString().contains(packageString))
			{
				error("\t@ " + elem.toString().replaceAll(packageString, ""));
			}
		}
		if (Configuration.SEND_ANONYMOUS_ERROR_REPORTS)
		{
			class EmailReport implements Runnable
			{
				@Override
				public void run()
				{
					errorReporter.sendErrorReport(e);
				}
			}
			BukkitUtil.getScheduler().scheduleSyncDelayedTask(BukkitUtil.getxTeam(), new EmailReport(), CommonUtil.LONG_ZERO);
		}
	}

	public void info(String message)
	{
		message = "[INFO] " + message;
		write(message);
	}

	public void write(String message)
	{
		Timestamp t = new Timestamp(System.currentTimeMillis());
		printStream.println(t.toString().substring(0, t.toString().indexOf('.')) + " " + message);
	}

	public void print(String message)
	{
		System.out.println(message);
	}
}
