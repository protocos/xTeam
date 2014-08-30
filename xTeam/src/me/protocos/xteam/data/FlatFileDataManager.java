package me.protocos.xteam.data;

import java.io.*;
import me.protocos.api.util.SystemUtil;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.core.PlayerFactory;
import me.protocos.xteam.entity.Team;
import me.protocos.xteam.model.ILog;
import me.protocos.xteam.model.PropertyList;

public class FlatFileDataManager implements IPersistenceLayer
{
	private TeamPlugin teamPlugin;
	private File teamFile;
	private File playerFile;
	private ITeamCoordinator teamCoordinator;
	private IPlayerFactory playerFactory;
	private ILog log;

	public FlatFileDataManager(TeamPlugin teamPlugin, ITeamCoordinator teamCoordinator, IPlayerFactory playerFactory)
	{
		this.teamPlugin = teamPlugin;
		this.teamFile = SystemUtil.ensureFile(teamPlugin.getFolder() + "teams.txt");
		this.playerFile = SystemUtil.ensureFile(teamPlugin.getFolder() + "players.txt");
		this.teamCoordinator = teamCoordinator;
		this.playerFactory = playerFactory;
		this.log = teamPlugin.getLog();
	}

	@Override
	public void read()
	{
		//team data
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(teamFile));
			String line;
			while ((line = reader.readLine()) != null)
			{
				try
				{
					Team team = Team.generateTeamFromProperties(teamPlugin, line);
					teamCoordinator.createTeam(team);
				}
				catch (Exception e)
				{
					//this way if one line fails to read, the entire file isn't lost
					log.exception(e);
				}
			}
			reader.close();
			reader = new BufferedReader(new FileReader(playerFile));
			while ((line = reader.readLine()) != null)
			{
				try
				{
					PropertyList propertyList = PlayerFactory.generatePlayerFromProperties(teamPlugin, line);
					playerFactory.updateValues(propertyList);
				}
				catch (Exception e)
				{
					//this way if one line fails to read, the entire file isn't lost
					log.exception(e);
				}
			}
			reader.close();
		}
		catch (Exception e)
		{
			log.exception(e);
		}
	}

	@Override
	public void write()
	{
		//team data
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(teamFile));
			for (String line : teamCoordinator.exportData())
			{
				try
				{
					writer.write(line + "\n");
				}
				catch (Exception e)
				{
					//this way if one line fails to write, the entire file isn't lost
					log.exception(e);
				}
			}
			writer.close();
			writer = new BufferedWriter(new FileWriter(playerFile));
			for (String line : playerFactory.exportData())
			{
				try
				{
					writer.write(line + "\n");
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
	public void open()
	{
	}

	@Override
	public void close()
	{
	}
}