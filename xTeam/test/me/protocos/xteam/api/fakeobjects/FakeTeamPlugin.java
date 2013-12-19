package me.protocos.xteam.api.fakeobjects;

import java.util.List;
import me.protocos.xteam.xTeamPlugin;
import me.protocos.xteam.api.TeamPlugin;
import me.protocos.xteam.api.command.ICommandManager;
import me.protocos.xteam.api.model.ILog;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.permissions.Permission;

public class FakeTeamPlugin extends TeamPlugin
{
	private final String folder = "test";
	private ILog fakeLog;

	public FakeTeamPlugin()
	{
		fakeLog = new FakeLog();
	}

	@Override
	public String getFolder()
	{
		return folder;
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
		return fakeLog;
	}

	@Override
	public void onEnable()
	{
		xteam.load(this);
	}

	@Override
	public void onDisable()
	{
	}

	@Override
	public void registerCommands(ICommandManager manager)
	{
		(new xTeamPlugin()).registerCommands(manager);
	}
}
