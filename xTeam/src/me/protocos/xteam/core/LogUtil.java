package me.protocos.xteam.core;

import java.io.*;
import java.sql.Timestamp;
import java.util.Scanner;
import me.protocos.xteam.util.LimitedQueue;
import org.bukkit.plugin.java.JavaPlugin;

public class LogUtil implements ILog
{
	private File file;
	private FileOutputStream output;
	private PrintStream printStream;

	public LogUtil(JavaPlugin plugin)
	{
		file = new File(plugin.getDataFolder().getAbsolutePath() + "/xTeam.log");
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
			LimitedQueue<String> previousEntries = new LimitedQueue<String>(50);
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
	}
	public void debug(String message)
	{
		message = "[DEBUG] " + message;
		write(message);
	}
	public void info(String message)
	{
		message = "[INFO] " + message;
		write(message);
	}
	public void error(String message)
	{
		message = "[ERROR] " + message;
		write(message);
	}
	public void fatal(String message)
	{
		message = "[FATAL] " + message;
		write(message);
	}
	public void exception(Exception e)
	{
		error(e.toString());
		for (StackTraceElement elem : e.getStackTrace())
		{
			if (elem.toString().contains("me.protocos."))
			{
				error("\t@ " + elem.toString());
			}
		}
	}
	public void custom(String message)
	{
		write(message);
	}
	public void write(String message)
	{
		Timestamp t = new Timestamp(System.currentTimeMillis());
		printStream.println(t.toString().substring(0, t.toString().indexOf('.')) + " " + message);
	}
	public void close()
	{
		printStream.close();
	}
}
