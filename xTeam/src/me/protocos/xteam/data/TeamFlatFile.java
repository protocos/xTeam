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
import me.protocos.xteam.model.ILog;
import me.protocos.xteam.util.SystemUtil;

public class TeamFlatFile implements IDataManager
{
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
	public void read()
	{
		this.initializeData();
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

	@Override
	public void write()
	{
		this.initializeData();
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

	@Override
	public void initializeData()
	{
		this.file = SystemUtil.ensureFile(teamPlugin.getFolder() + "teams.txt");
	}

	@Override
	public void updateEntry(String teamName, PropertyList properties)
	{
	}

	@Override
	public void removeEntry(String teamName)
	{
	}

	@Override
	public <T> void setVariable(String teamName, String variableName, T variable, IDataTranslator<T> strategy)
	{
		this.initializeData();
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

	@Override
	public <T> T getVariable(String teamName, String variableName, IDataTranslator<T> strategy)
	{
		this.initializeData();
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
		return null;
	}
}
