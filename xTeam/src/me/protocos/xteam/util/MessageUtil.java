package me.protocos.xteam.util;

import java.util.List;
import me.protocos.xteam.api.core.ITeamEntity;
import me.protocos.xteam.core.TeamPlayer;

public class MessageUtil
{
	public static void sendMessageToTeam(ITeamEntity entity, String message)
	{
		List<TeamPlayer> onlinePlayers = entity.getOnlineTeammates();
		for (TeamPlayer teammate : onlinePlayers)
		{
			teammate.sendMessage(message);
		}
	}
}
