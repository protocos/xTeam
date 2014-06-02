package me.protocos.xteam.data;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.collections.HashList;
import me.protocos.xteam.core.ITeamManager;
import me.protocos.xteam.data.translator.IDataTranslator;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.Team;
import me.protocos.xteam.exception.DataManagerNotOpenException;
import me.protocos.xteam.model.ILog;
import me.protocos.xteam.util.SystemUtil;

public class TeamFlatFile implements IDataManager
{
	private boolean open = false;
	private TeamPlugin teamPlugin;
	private File file;
	private ITeamManager teamManager;
	private ILog log;

	public TeamFlatFile(TeamPlugin teamPlugin, ITeamManager teamManager)
	{
		this.teamPlugin = teamPlugin;
		this.teamManager = teamManager;
		this.log = teamPlugin.getLog();
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
				String line;
				BufferedReader br = new BufferedReader(new FileReader(file));
				while ((line = br.readLine()) != null)
				{
					Team team = Team.generateTeamFromProperties(teamPlugin, line);
					teamManager.createTeam(team);
				}
				br.close();
			}
			catch (Exception e)
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
				ArrayList<String> data = new ArrayList<String>();
				HashList<String, ITeam> teams = teamManager.getTeams();
				for (ITeam team : teams)
					data.add(team.toString());
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				for (String line : data)
				{
					bw.write(line + "\n");
				}
				bw.close();
			}
			catch (Exception e)
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
			this.file = SystemUtil.ensureFile(teamPlugin.getFolder() + "teams.txt");
		}
		else
		{
			throw new DataManagerNotOpenException();
		}
	}

	//	@Override
	//	public void addEntry(String teamName, PropertyList properties)
	//	{
	//		if (open)
	//		{
	//			teamProperties.put(teamName, properties);
	//		}
	//		else
	//		{
	//			throw new DataManagerNotOpenException();
	//		}
	//	}

	//	@Override
	//	public void removeEntry(String teamName)
	//	{
	//		if (open)
	//		{
	//			teamProperties.remove(teamName);
	//		}
	//		else
	//		{
	//			throw new DataManagerNotOpenException();
	//		}
	//	}

	@Override
	public <T> void setVariable(String teamName, String variableName, T variable, IDataTranslator<T> strategy)
	{
		if (open)
		{
			ITeam team = teamManager.getTeam(teamName);
			Method[] methods = Team.class.getMethods();
			for (Method method : methods)
			{
				if (method.getName().equalsIgnoreCase("set" + variableName))
				{
					try
					{
						method.invoke(team, variable);
					}
					catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
					{
						log.exception(e);
					}
				}
			}
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
			ITeam team = teamManager.getTeam(teamName);
			Method[] methods = Team.class.getMethods();
			for (Method method : methods)
			{
				if (method.getName().equalsIgnoreCase("get" + variableName))
				{
					try
					{
						method.invoke(team);
					}
					catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
					{
						log.exception(e);
					}
				}
			}
		}
		throw new DataManagerNotOpenException();
	}
}
