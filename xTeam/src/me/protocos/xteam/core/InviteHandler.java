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
		invites.put(player, invite);
	}
	public static void removeInvite(String player)
	{
		invites.remove(player);
	}
	public static Team getInviteTeam(String player)
	{
		if (invites.containsKey(player))
			return xTeam.tm.getTeam(invites.get(player).split(":")[0]);
		return null;
	}
	public static long getInviteTime(String player)
	{
		if (invites.containsKey(player))
			return Long.parseLong(invites.get(player).split(":")[1]);
		return 0;
	}
	public static boolean hasInvite(String player)
	{
		long currentTime = System.currentTimeMillis();
		long timeStamp = getInviteTime(player);
		if (currentTime - timeStamp > 30 * 1000)
			invites.remove(player);
		return invites.containsKey(player);
	}
	public static void print()
	{
		System.out.println(invites);
	}
	public static void clear()
	{
		invites.clear();
	}
}
