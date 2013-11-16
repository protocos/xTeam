package me.protocos.xteam;

import java.util.List;
import me.protocos.xteam.api.TeamPlugin;
import me.protocos.xteam.api.util.ILog;
import me.protocos.xteam.core.Log;
import org.bukkit.permissions.Permission;

public class xTeamPlugin extends TeamPlugin
{
	private ILog logger;

	public xTeamPlugin()
	{
	}

	@Override
	public String getFolder()
	{
		return this.getDataFolder().getAbsolutePath();
	}

	@Override
	public String getPluginName()
	{
		return this.getName();
	}

	@Override
	public String getVersion()
	{
		return this.getDescription().getVersion();
	}

	@Override
	public List<Permission> getPermissions()
	{
		return this.getDescription().getPermissions();
	}

	@Override
	public ILog getLog()
	{
		return logger;
	}

	@Override
	public void onLoad()
	{
		logger = new Log(this.getFolder(), this);
		xteam.load(this);
	}

	@Override
	public void onEnable()
	{
		xteam.enable(this);
	}

	@Override
	public void onDisable()
	{
		xteam.disable(this);
	}
}
