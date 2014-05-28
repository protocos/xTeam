package me.protocos.xteam.data;

import java.io.*;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.collections.HashList;
import me.protocos.xteam.data.translator.IDataTranslator;
import me.protocos.xteam.exception.DataEntryDoesNotExistException;
import me.protocos.xteam.exception.DataManagerNotOpenException;
import me.protocos.xteam.model.ILog;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.SystemUtil;
import org.bukkit.scheduler.BukkitScheduler;

public class TeamFlatFile implements IDataManager
{
	private boolean open = false;
	private TeamPlugin teamPlugin;
	private BukkitScheduler bukkitScheduler;
	private File file;
	private HashList<String, PropertyList> teamProperties;
	private PeriodicTeamWriter periodicWriter;
	private ILog log;

	public TeamFlatFile(TeamPlugin plugin)
	{
		this.teamPlugin = plugin;
		this.bukkitScheduler = plugin.getBukkitScheduler();
		this.log = plugin.getLog();
		this.file = SystemUtil.ensureFile(plugin.getFolder() + "teams.txt");
	}

	@Override
	public void open()
	{
		open = true;
		this.initializeData();
	}

	@Override
	public void read()
	{
		if (open)
		{
			try
			{
				PropertyList propList;
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String line;
				while ((line = reader.readLine()) != null)
				{
					try
					{
						propList = PropertyList.fromString(line);
						Property nameProperty = propList.remove("name");
						String teamName = nameProperty.getValue();
						teamProperties.put(teamName, propList);
					}
					catch (Exception e)
					{
						//this way if one line fails to write, the entire file isn't lost
						log.exception(e);
					}
				}
				reader.close();
			}
			catch (Exception e)
			{
				log.exception(e);
			}
			if (periodicWriter == null)
			{
				periodicWriter = new PeriodicTeamWriter(log, this);
				long interval = 10 * BukkitUtil.ONE_MINUTE_IN_TICKS;
				bukkitScheduler.scheduleSyncRepeatingTask(teamPlugin, periodicWriter, interval, interval);
			}
		}
		else
		{
			throw new DataManagerNotOpenException();
		}
	}

	@Override
	public boolean isOpen()
	{
		return open;
	}

	@Override
	public void write()
	{
		if (open)
		{
			try
			{
				PropertyList propList;
				BufferedWriter writer = new BufferedWriter(new FileWriter(file));
				for (String team : teamProperties.getOrder())
				{

					try
					{
						propList = teamProperties.get(team);
						writer.write(new StringBuilder().append("name:").append(team).append(" ").append(propList).append("\n").toString());
					}
					catch (Exception e)
					{
						//this way if one line fails to write, the entire file isn't lost
						log.exception(e);
					}
				}
				writer.close();
			}
			catch (IOException e)
			{
				log.exception(e);
			}
		}
		else
		{
			throw new DataManagerNotOpenException();
		}
	}

	@Override
	public void close()
	{
		open = false;
	}

	@Override
	public void initializeData()
	{
		if (open)
		{
			if (teamProperties == null)
			{
				teamProperties = new HashList<String, PropertyList>();
			}
		}
		else
		{
			throw new DataManagerNotOpenException();
		}
	}

	@Override
	public void addEntry(String teamName, PropertyList properties)
	{
		if (open)
		{
			teamProperties.put(teamName, properties);
		}
		else
		{
			throw new DataManagerNotOpenException();
		}
	}

	@Override
	public void removeEntry(String teamName)
	{
		if (open)
		{
			teamProperties.remove(teamName);
		}
		else
		{
			throw new DataManagerNotOpenException();
		}
	}

	@Override
	public <T> void setVariable(String teamName, String variableName, T variable, IDataTranslator<T> strategy)
	{
		if (open)
		{
			if (!teamProperties.containsKey(teamName))
			{
				throw new DataEntryDoesNotExistException();
			}
			PropertyList properties = teamProperties.get(teamName);
			properties.put(variableName, strategy.decompile(variable));
		}
		else
		{
			throw new DataManagerNotOpenException();
		}
	}

	@Override
	public <T> T getVariable(String teamName, String variableName, IDataTranslator<T> strategy)
	{
		if (open)
		{
			if (!teamProperties.containsKey(teamName))
			{
				throw new DataEntryDoesNotExistException();
			}
			PropertyList properties = teamProperties.get(teamName);
			Property property = properties.get(variableName);
			if (property == null)
				return null;
			return strategy.compile(property.getValue());
		}
		throw new DataManagerNotOpenException();
	}
}

class PeriodicTeamWriter implements Runnable
{
	private IDataManager writer;
	private ILog log;

	public PeriodicTeamWriter(ILog log, IDataManager writer)
	{
		this.log = log;
		this.writer = writer;
	}

	@Override
	public void run()
	{
		log.info("Saving team data...");
		writer.write();
		log.info("Done.");
	}

}
