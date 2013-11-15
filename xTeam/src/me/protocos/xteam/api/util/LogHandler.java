package me.protocos.xteam.api.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.util.Scanner;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import me.protocos.xteam.api.TeamPlugin;
import me.protocos.xteam.api.collections.LimitedQueue;
import me.protocos.xteam.util.SystemUtil;

public class LogHandler extends Handler
{
	private final String packageString;
	//	private ErrorReportUtil errorReporter;
	private PrintStream printStream;

	public LogHandler(TeamPlugin teamPlugin)
	{
		String pluginPackageID = teamPlugin.getClass().getPackage().toString();
		this.packageString = pluginPackageID.substring(pluginPackageID.indexOf(' ') + 1, pluginPackageID.lastIndexOf('.') + 1);
		String pluginName = teamPlugin.getName();
		SystemUtil.ensureFolder(pluginName);
		File file = SystemUtil.ensureFile(pluginName + "/" + pluginName + ".log");
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
		//		errorReporter = new ErrorReportUtil(teamPlugin);
	}

	@Override
	public void close() throws SecurityException
	{
		printStream.close();
	}

	@Override
	public void flush()
	{
		printStream.flush();
	}

	@Override
	public void publish(LogRecord record)
	{
		String message = record.getMessage();
		if (message.contains(packageString))
		{
			Timestamp t = new Timestamp(System.currentTimeMillis());
			final String finalMessage = t.toString().substring(0, t.toString().indexOf('.')) + " " + message;
			printStream.println(finalMessage);
			//			if (record.getLevel().equals(Level.SEVERE))
			//			{
			//				handleSevere(record);
			//			}
		}
	}

	//	private void handleSevere(final LogRecord record)
	//	{
	//		if (Configuration.SEND_ANONYMOUS_ERROR_REPORTS)
	//		{
	//			class EmailReport implements Runnable
	//			{
	//				@Override
	//				public void run()
	//				{
	//					errorReporter.sendErrorReport(record);
	//				}
	//			}
	//			BukkitUtil.getScheduler().scheduleSyncDelayedTask(BukkitUtil.getxTeam(), new EmailReport(), CommonUtil.LONG_ZERO);
	//		}
	//	}
}
