package me.protocos.xteam.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.util.Scanner;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.collections.LimitedQueue;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.ErrorReporterUtil;
import me.protocos.xteam.util.SystemUtil;

public class Log implements ILog
{
	private TeamPlugin teamPlugin;
	private ErrorReporterUtil errorReporter;
	private PrintStream printStream;
	private LimitedQueue<String> messageLog;

	public Log(TeamPlugin teamPlugin, String filePath)
	{
		this.teamPlugin = teamPlugin;
		File file = SystemUtil.ensureFile(filePath);
		try
		{
			messageLog = new LimitedQueue<String>(5000);
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(file);
			String line;
			while (sc.hasNext() && (line = sc.nextLine()) != null)
			{
				messageLog.offer(line);
			}
			FileOutputStream output = new FileOutputStream(file);
			printStream = new PrintStream(output);
			printStream.print(messageLog.toString());
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		errorReporter = new ErrorReporterUtil(teamPlugin);
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

	public void info(String message)
	{
		write(message);
		print(message);
	}

	public void error(String message)
	{
		if (BukkitUtil.serverIsLive())
		{
			message = "[ERROR] " + message;
			write(message);
			print(message);
		}
	}

	public void exception(final Exception e)
	{
		error(e.toString());
		for (StackTraceElement elem : e.getStackTrace())
		{
			if (elem.toString().contains("protocos"))
			{
				error("\t@ " + elem.toString());
			}
		}
		if (Configuration.SEND_ANONYMOUS_ERROR_REPORTS && teamPlugin.isEnabled())
		{
			class EmailReport implements Runnable
			{
				@Override
				public void run()
				{
					errorReporter.report(e);
				}
			}
			teamPlugin.getBukkitScheduler().runTaskAsynchronously(teamPlugin, new EmailReport());
		}
	}

	public void write(String message)
	{
		Timestamp t = new Timestamp(System.currentTimeMillis());
		printStream.println(t.toString().substring(0, t.toString().indexOf('.')) + " [xTeam] " + message);
	}

	public void print(String message)
	{
		String[] lines = message.split("\n");
		String header = "[xTeam] ";
		for (String line : lines)
		{
			System.out.println(header + line);
			header = "[xTeam]\t ";
		}
	}

	@Override
	public String getLastMessages()
	{
		return messageLog.toString();
	}

	@Override
	public LimitedQueue<String> getMessages()
	{
		return messageLog;
	}

	@Override
	public void clearMessages()
	{
		messageLog.clear();
	}
}
