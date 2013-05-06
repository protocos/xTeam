package me.protocos.xteam.core;

import java.util.HashMap;
import me.protocos.xteam.xTeam;

public class InviteHandler
{
	public static HashMap<String, String> invites = new HashMap<String, String>();

	private InviteHandler()
	{

	}
	public static void addInvite(String player, Team team)
	{
		String invite = team.getName() + ":" + System.currentTimeMillis();
		invites.put(player.toLowerCase().toLowerCase(), invite);
		xTeam.logger.debug("Added Invite: " + data());
	}
	public static void removeInvite(String player)
	{
		invites.remove(player.toLowerCase());
		xTeam.logger.debug("Removed Invite: " + data());
	}
	public static Team getInviteTeam(String player)
	{
		xTeam.logger.debug("Get Invite Team: " + data());
		if (invites.containsKey(player.toLowerCase()))
			return xTeam.tm.getTeam(invites.get(player.toLowerCase()).split(":")[0]);
		return null;
	}
	public static long getInviteTime(String player)
	{
		xTeam.logger.debug("Get Invite Time: " + data());
		if (invites.containsKey(player.toLowerCase()))
			return Long.parseLong(invites.get(player.toLowerCase()).split(":")[1]);
		return 0;
	}
	public static boolean hasInvite(String player)
	{
		long currentTime = System.currentTimeMillis();
		long timeStamp = getInviteTime(player.toLowerCase());
		if (currentTime - timeStamp > 30 * 1000)
			invites.remove(player.toLowerCase());
		xTeam.logger.debug("Has Invite: " + data());
		return invites.containsKey(player.toLowerCase());
	}
	public static void clear()
	{
		invites.clear();
	}
	public static String data()
	{
		return invites.toString();
	}
}
