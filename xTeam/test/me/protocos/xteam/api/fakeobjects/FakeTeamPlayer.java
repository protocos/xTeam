package me.protocos.xteam.api.fakeobjects;

import me.protocos.xteam.command.*;
import me.protocos.xteam.entity.TeamPlayer;

public class FakeTeamPlayer extends TeamPlayer
{
	public enum PermissionType
	{
		SERVERADMIN, LEADER, ADMIN, USER
	}

	private PermissionType permissionType;

	public FakeTeamPlayer(PermissionType permissionType)
	{
		super(new FakePlayer());
		this.permissionType = permissionType;
	}

	@Override
	public boolean isAdmin()
	{
		return true;
	}

	@Override
	public boolean isLeader()
	{
		return true;
	}

	@Override
	public boolean hasPermission(IPermissible permissible)
	{
		if (PermissionType.SERVERADMIN.equals(permissionType) && permissible instanceof ServerAdminCommand)
			return true;
		if (PermissionType.LEADER.equals(permissionType) && permissible instanceof TeamLeaderCommand)
			return true;
		if (PermissionType.ADMIN.equals(permissionType) && permissible instanceof TeamAdminCommand)
			return true;
		if (PermissionType.USER.equals(permissionType) && permissible instanceof TeamUserCommand)
			return true;
		return false;
	}
}
