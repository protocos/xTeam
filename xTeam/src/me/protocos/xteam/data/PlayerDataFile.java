package me.protocos.xteam.data;

import java.io.*;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.collections.HashList;
import me.protocos.xteam.data.translator.IDataTranslator;
import me.protocos.xteam.exception.DataManagerNotOpenException;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.SystemUtil;

public class PlayerDataFile implements IDataManager
{
	private boolean open = false;
	private TeamPlugin plugin;
	private File file;
	private HashList<String, PropertyList> playerProperties;
	private PeriodicWriter periodicWriter;

	public PlayerDataFile(TeamPlugin plugin)
	{
		this.plugin = plugin;
		this.file = SystemUtil.ensureFile(plugin.getFolder() + "players.txt");
	}

	@Override
	public void open()
	{
		open = true;
		this.initializeData();
		this.clearData();
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
						String playerName = nameProperty.getValue();
						playerProperties.put(playerName, propList);
					}
					catch (Exception e)
					{
						//this way if one line fails to write, the entire file isn't lost
						xTeam.getInstance().getLog().exception(e);
					}
				}
				reader.close();
			}
			catch (Exception e)
			{
				xTeam.getInstance().getLog().exception(e);
			}
			if (periodicWriter == null)
			{
				periodicWriter = new PeriodicWriter(this);
				long interval = 10 * BukkitUtil.ONE_MINUTE_IN_TICKS;
				BukkitUtil.getScheduler().scheduleSyncRepeatingTask(plugin, periodicWriter, interval, interval);
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
						xTeam.getInstance().getLog().exception(e);
					}
				}
				writer.close();
			}
			catch (IOException e)
			{
				xTeam.getInstance().getLog().exception(e);
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
			if (playerProperties == null)
			{
				playerProperties = new HashList<String, PropertyList>();
			}
		}
		else
		{
			throw new DataManagerNotOpenException();
		}
	}

	@Override
	public void clearData()
	{
		if (open)
		{
			for (String player : playerProperties.getOrder())
			{
				playerProperties.put(player, blankDataSet());
			}
		}
		else
		{
			throw new DataManagerNotOpenException();
		}
	}

	@Override
	public <T> void setVariable(String playerName, String variableName, T variable, IDataTranslator<T> strategy)
	{
		if (open)
		{
			if (!playerProperties.containsKey(playerName))
			{
				playerProperties.put(playerName, blankDataSet());
			}
			PropertyList properties = playerProperties.get(playerName);
			properties.put(variableName, strategy.decompile(variable));
		}
		else
		{
			throw new DataManagerNotOpenException();
		}
	}

	@Override
	public <T> T getVariable(String playerName, String variableName, IDataTranslator<T> strategy)
	{
		if (open)
		{
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
		throw new DataManagerNotOpenException();
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

class PeriodicWriter implements Runnable
{
	private IDataManager writer;

	public PeriodicWriter(IDataManager writer)
	{
		this.writer = writer;
	}

	@Override
	public void run()
	{
		xTeam.getInstance().getLog().info("Saving player data...");
		writer.write();
		xTeam.getInstance().getLog().info("Done.");
	}

}
