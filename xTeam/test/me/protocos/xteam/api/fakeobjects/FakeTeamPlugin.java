package me.protocos.xteam.api.fakeobjects;

import java.util.List;
import me.protocos.xteam.api.TeamPlugin;
import me.protocos.xteam.api.util.ILog;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.permissions.Permission;

public class FakeTeamPlugin extends TeamPlugin
{
	private final String name = "xTeam";
	private final String folder = "test";
	private ILog logger;

	public FakeTeamPlugin()
	{
		logger = new FakeLog();
	}

	@Override
	public String getFolder()
	{
		return folder;
	}

	@Override
	public String getPluginName()
	{
		return name;
	}

	@Override
	public String getVersion()
	{
		return "CURRENT";
	}

	@Override
	public List<Permission> getPermissions()
	{
		return CommonUtil.emptyList();
	}

	@Override
	public ILog getLog()
	{
		return logger;
	}

	@Override
	public void onLoad()
	{
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
