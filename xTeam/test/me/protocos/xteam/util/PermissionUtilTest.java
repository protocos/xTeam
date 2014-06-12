package me.protocos.xteam.util;

import me.protocos.xteam.command.IPermissible;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.fakeobjects.FakeTeamPlayer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PermissionUtilTest
{
	@Before
	public void setup()
	{
	}

	@Test
	public void ShouldBeUserPermission()
	{
		//ASSEMBLE
		Configuration.NO_PERMISSIONS = true;
		FakeTeamPlayer teamPlayer = new FakeTeamPlayer(false);
		//ACT
		//ASSERT
		Assert.assertTrue(teamPlayer.hasPermission(new Permissible("xteam.core.user.permission")));
	}

	@Test
	public void ShouldBeTeamAdminPermission()
	{
		//ASSEMBLE
		Configuration.NO_PERMISSIONS = true;
		FakeTeamPlayer teamPlayer = new FakeTeamPlayer(false);
		//ACT
		//ASSERT
		Assert.assertTrue(teamPlayer.hasPermission(new Permissible("xteam.core.admin.permission")));
	}

	@Test
	public void ShouldBeTeamLeaderPermission()
	{
		//ASSEMBLE
		Configuration.NO_PERMISSIONS = true;
		FakeTeamPlayer teamPlayer = new FakeTeamPlayer(false);
		//ACT
		//ASSERT
		Assert.assertTrue(teamPlayer.hasPermission(new Permissible("xteam.core.leader.permission")));
	}

	@Test
	public void ShouldBeHelpPermission()
	{
		//ASSEMBLE
		FakeTeamPlayer teamPlayer = new FakeTeamPlayer(false);
		//ACT
		//ASSERT
		Assert.assertTrue(teamPlayer.hasPermission(new Permissible("help")));
	}

	@Test
	public void ShouldBeInfoPermission()
	{
		//ASSEMBLE
		Configuration.NO_PERMISSIONS = true;
		FakeTeamPlayer teamPlayer = new FakeTeamPlayer(false);
		//ACT
		//ASSERT
		Assert.assertTrue(teamPlayer.hasPermission(new Permissible("xteam.core.ANYTHING.info")));
	}

	@After
	public void takedown()
	{
		Configuration.NO_PERMISSIONS = false;
	}
}

class Permissible implements IPermissible
{
	private String permission;

	public Permissible(String permission)
	{
		this.permission = permission;
	}

	@Override
	public String getPermissionNode()
	{
		return permission;
	}
}