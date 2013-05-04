package me.protocos.xteam.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import org.bukkit.plugin.java.JavaPlugin;

public class Log
{
	public File file;
	public FileWriter writer;

	public Log(JavaPlugin plugin)
	{
		LimitedQueue<String> queue = new LimitedQueue<String>(10);
		file = new File(plugin.getDataFolder().getAbsolutePath() + "/xteam.log");
		if (!file.exists())
			try
			{
				file.createNewFile();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		try
		{
			Scanner readData = new Scanner(file);
			String line;
			while (readData.hasNext() && (line = readData.nextLine()) != null)
			{
				queue.offer(line);
			}
			writer = new FileWriter(file);
			writer.write(queue.toString());
		}
		catch (IOException e)
		{
			System.err.println("No log found!");
		}
	}
	public void debug(String message)
	{
		message = "DEBUG: " + message;
		write(message);
	}
	public void info(String message)
	{
		message = "INFO: " + message;
		write(message);
	}
	public void error(String message)
	{
		message = "ERROR: " + message;
		write(message);
	}
	public void fatal(String message)
	{
		message = "FATAL: " + message;
		write(message);
	}
	public void close()
	{
		try
		{
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	private void write(String message)
	{
		try
		{
			writer.write(message + "\n");
			writer.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
