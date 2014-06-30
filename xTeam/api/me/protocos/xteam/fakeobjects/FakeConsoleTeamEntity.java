package me.protocos.xteam.fakeobjects;

import java.util.List;
import me.protocos.xteam.entity.*;

public class FakeConsoleTeamEntity implements ITeamEntity
{
	@Override
	public void sendMessage(String message)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public ITeam getTeam()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasTeam()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOnSameTeam(ITeamEntity entity)
	{
		return true;
	}

	@Override
	public boolean isOnline()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isVulnerable()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ITeamPlayer> getTeammates()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TeamPlayer> getOnlineTeammates()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OfflineTeamPlayer> getOfflineTeammates()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInfoFor(ITeamEntity entity)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
