package me.protocos.xteam.data;

import java.io.*;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.collections.HashList;
import me.protocos.xteam.data.translator.IDataTranslator;
import me.protocos.xteam.model.ILog;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.SystemUtil;
import org.bukkit.scheduler.BukkitScheduler;

public class PlayerFlatFile implements IDataManager
{
	private TeamPlugin plugin;
	private BukkitScheduler bukkitScheduler;
	private File file;
	private HashList<String, PropertyList> playerProperties;
	private PeriodicPlayerWriter periodicWriter;
	private ILog log;

	public PlayerFlatFile(TeamPlugin plugin)
	{
		this.plugin = plugin;
		this.bukkitScheduler = plugin.getBukkitScheduler();
		this.log = plugin.getLog();
	}

	@Override
	public void read()
	{
		this.initializeData();
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
					String playerName = nameProperty.getValue();
					playerProperties.put(playerName, propList);
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
			periodicWriter = new PeriodicPlayerWriter(log, this);
			long interval = 10 * BukkitUtil.ONE_MINUTE_IN_TICKS;
			bukkitScheduler.scheduleSyncRepeatingTask(plugin, periodicWriter, interval, interval);
		}
	}

	@Override
	public void write()
	{
		this.initializeData();
		try
		{
			PropertyList propList;
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			for (String player : playerProperties.getOrder())
			{

				try
				{
					propList = playerProperties.get(player);
					writer.write(new StringBuilder().append("name:").append(player).append(" ").append(propList).append("\n").toString());
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

	@Override
	public void initializeData()
	{
		this.file = SystemUtil.ensureFile(plugin.getFolder() + "players.txt");
		if (playerProperties == null)
		{
			playerProperties = new HashList<String, PropertyList>();
		}
	}

	@Override
	public void updateEntry(String name, PropertyList properties)
	{
		this.initializeData();
		playerProperties.put(name, properties);
	}

	@Override
	public void removeEntry(String name)
	{
		this.initializeData();
		playerProperties.remove(name);
	}

	@Override
	public <T> void setVariable(String playerName, String variableName, T variable, IDataTranslator<T> strategy)
	{
		this.initializeData();
		if (!playerProperties.containsKey(playerName))
		{
			playerProperties.put(playerName, blankDataSet());
		}
		PropertyList properties = playerProperties.get(playerName);
		properties.put(variableName, strategy.decompile(variable));
	}

	@Override
	public <T> T getVariable(String playerName, String variableName, IDataTranslator<T> strategy)
	{
		this.initializeData();
		if (!playerProperties.containsKey(playerName))
		{
			playerProperties.put(playerName, blankDataSet());
		}
		PropertyList properties = playerProperties.get(playerName);
		Property property = properties.get(variableName);
		if (property == null)
			return null;
		return strategy.compile(property.getValue());
	}

	private PropertyList blankDataSet()
	{
		PropertyList props = new PropertyList();
		props.put("lastAttacked", "0");
		props.put("lastTeleported", "0");
		props.put("returnLocation", "");
		return props;
	}
}

class PeriodicPlayerWriter implements Runnable
{
	private IDataManager writer;
	private ILog log;

	public PeriodicPlayerWriter(ILog log, IDataManager writer)
	{
		this.log = log;
		this.writer = writer;
	}

	@Override
	public void run()
	{
		log.info("Saving player data...");
		writer.write();
		log.info("Done.");
	}

}
