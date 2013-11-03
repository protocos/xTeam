package me.protocos.xteam.util;

import java.util.List;
import me.protocos.xteam.api.core.ITeamEntity;
import me.protocos.xteam.core.TeamPlayer;

public class MessageUtil
{
	public static boolean sendMessageToTeam(ITeamEntity entity, String message)
	{
		boolean sentToAll = true;
		List<TeamPlayer> onlinePlayers = entity.getOnlineTeammates();
		for (TeamPlayer teammate : onlinePlayers)
		{
			sentToAll = teammate.sendMessage(message);
		}
		return sentToAll;
	}
}
