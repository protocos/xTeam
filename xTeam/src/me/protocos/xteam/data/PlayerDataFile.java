package me.protocos.xteam.data;

import java.io.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.collections.HashList;
import me.protocos.xteam.data.translators.IDataTranslator;

public class PlayerDataFile implements IDataManager
{
	private File file;
	private HashList<String, PropertyList> properties;

	public PlayerDataFile(File file)
	{
		this.file = file;
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
