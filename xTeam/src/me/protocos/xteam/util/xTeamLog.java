package me.protocos.xteam.util;

import java.io.*;
import java.sql.Timestamp;
import java.util.Scanner;
import org.bukkit.plugin.java.JavaPlugin;

public class xTeamLog implements ILog
{
	private File file;
	private FileOutputStream output;
	private PrintStream printStream;

	public xTeamLog(JavaPlugin plugin)
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
