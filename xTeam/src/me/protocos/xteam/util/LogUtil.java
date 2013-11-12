package me.protocos.xteam.util;

import java.io.*;
import java.sql.Timestamp;
import java.util.Scanner;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.TeamPlugin;
import me.protocos.xteam.api.collections.LimitedQueue;
import me.protocos.xteam.api.util.ILog;
import me.protocos.xteam.core.Data;

public class LogUtil implements ILog
{
	private String pluginPackageID;
	private File file;
	private FileOutputStream output;
	private PrintStream printStream;
	private ErrorReportUtil errorReporter;

	public LogUtil(TeamPlugin plugin, String filePath)
	{
		String packageString = plugin.getClass().getPackage().toString();
		this.pluginPackageID = packageString.substring(packageString.indexOf(' ') + 1, packageString.lastIndexOf('.') + 1);
		file = new File(filePath + "/" + plugin.getPluginName() + ".log");
		if (!file.exists())
		{
			try
			{
				file.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		try
		{
			LimitedQueue<String> previousEntries = new LimitedQueue<String>(500);
			Scanner sc = new Scanner(file);
			String line;
			while (sc.hasNext() && (line = sc.nextLine()) != null)
			{
				previousEntries.offer(line);
			}
			output = new FileOutputStream(file);
			printStream = new PrintStream(output);
			printStream.print(previousEntries.toString());
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		errorReporter = new ErrorReportUtil(plugin);
	}

	public void close()
	{
		printStream.close();
	}

	public void custom(String message)
	{
		write(message);
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
	}

	public void exception(final Exception e)
	{
		error(e.toString());
		for (StackTraceElement elem : e.getStackTrace())
		{
			if (elem.toString().contains(pluginPackageID))
			{
				error("\t@ " + elem.toString().replaceAll(pluginPackageID, ""));
			}
		}
		if (Data.SEND_ANONYMOUS_ERROR_REPORTS)
		{
			class EmailReport implements Runnable
			{
				@Override
				public void run()
				{
					errorReporter.sendErrorReport(e);
				}
			}
			BukkitUtil.getScheduler().scheduleSyncDelayedTask(xTeam.getInstance(), new EmailReport(), CommonUtil.LONG_ZERO);
		}
	}

	public void fatal(String message)
	{
		message = "[FATAL] " + message;
		write(message);
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
}
