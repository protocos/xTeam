package me.protocos.xteam.data;

import java.io.*;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.collections.HashList;
import me.protocos.xteam.data.translator.IDataTranslator;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.SystemUtil;

public class PlayerDataFile implements IDataManager
{
	private TeamPlugin plugin;
	private File file;
	private HashList<String, PropertyList> properties;
	private PeriodicWriter periodicWriter;

	public PlayerDataFile(TeamPlugin plugin)
	{
		this.plugin = plugin;
		this.file = SystemUtil.ensureFile(plugin.getFolder() + "players.txt");
	}

	@Override
	public void open()
	{
	}

	@Override
	public void read()
	{
		try
		{
			PropertyList propList;
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null)
			{
				propList = PropertyList.fromString(line);
				Property nameProperty = propList.remove("name");
				String name = nameProperty.getValue();
				properties.put(name, propList);
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

	@Override
	public boolean isOpen()
	{
		return true;
	}

	@Override
	public void write()
	{
		try
		{
			PropertyList propList;
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			for (String player : properties.getOrder())
			{
				propList = properties.get(player);
				writer.write(new StringBuilder().append("name:").append(player).append(" ").append(propList).append("\n").toString());
			}
			writer.close();
		}
		catch (IOException e)
		{
			xTeam.getInstance().getLog().exception(e);
		}
	}

	@Override
	public void close()
	{
	}

	@Override
	public void initializeData()
	{
		if (properties == null)
		{
			properties = new HashList<String, PropertyList>();
		}
	}

	@Override
	public void clearData()
	{
		for (String player : properties.getOrder())
		{
			properties.put(player, blankDataSet());
		}
	}

	@Override
	public <T> void setVariable(String playerName, String variableName, T variable, IDataTranslator<T> strategy)
	{
		PropertyList playerProperties = properties.get(playerName);
		playerProperties.put(variableName, strategy.decompile(variable));
	}

	@Override
	public <T> T getVariable(String playerName, String variableName, IDataTranslator<T> strategy)
	{
		if (!properties.containsKey(playerName))
		{
			properties.put(playerName, blankDataSet());
		}
		PropertyList playerProperties = properties.get(playerName);
		Property property = playerProperties.get(variableName);
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
