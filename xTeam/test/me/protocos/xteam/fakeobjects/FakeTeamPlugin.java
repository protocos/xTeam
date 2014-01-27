package me.protocos.xteam.fakeobjects;

import java.util.List;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.xTeamPlugin;
import me.protocos.xteam.command.ICommandManager;
import me.protocos.xteam.model.ILog;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.permissions.Permission;

public class FakeTeamPlugin extends TeamPlugin
{
	private final String folder = "test/";
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
