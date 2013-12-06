package me.protocos.xteam.command.action;

import java.util.HashMap;
import me.protocos.xteam.api.entity.ITeamPlayer;
import me.protocos.xteam.api.model.ITeam;
import me.protocos.xteam.model.InviteRequest;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.ChatColorUtil;

public class InviteHandler
{
	private static HashMap<String, InviteRequest> invites = new HashMap<String, InviteRequest>();

	public static void addInvite(final InviteRequest request)
	{
		final ITeamPlayer inviter = request.getInviteSender();
		final ITeamPlayer invitee = request.getInviteReceiver();
		invites.put(invitee.getName(), request);
		class InviteExpire implements Runnable
		{
			@Override
			public void run()
			{
				invitee.sendMessage("Invite from " + inviter.getName() + " has " + ChatColorUtil.negativeMessage("expired"));
				invites.remove(invitee.getName());
			}
		}
		BukkitUtil.getScheduler().scheduleSyncDelayedTask(BukkitUtil.getxTeam(), new InviteExpire(), BukkitUtil.ONE_MINUTE_IN_TICKS);
	}

	public static void clear()
	{
		invites.clear();
	}

	public static String data()
	{
		return invites.toString();
	}

	public static ITeam getInviteTeam(String player)
	{
		if (invites.containsKey(player))
			return invites.get(player).getSenderTeam();
		return null;
	}

	public static long getInviteTime(String player)
	{
		if (invites.containsKey(player))
			return invites.get(player).getTimeSent();
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

	public static void removeInvite(String player)
	{
		invites.remove(player);
	}

	private InviteHandler()
	{

	}
}