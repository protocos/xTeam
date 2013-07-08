package me.protocos.xteam.core;

import java.util.List;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public interface ITeamPlayer extends ITeamEntity
{
	public abstract String getLastPlayed();
	public abstract String getName();
	public abstract OfflinePlayer getOfflinePlayer();
	public abstract List<String> getOfflineTeammates();
	public abstract Player getOnlinePlayer();
	public abstract List<String> getOnlineTeammates();
	public abstract List<String> getTeammates();
	public abstract boolean hasPermission(String permission);
	public abstract boolean hasPlayedBefore();
	public abstract boolean isAdmin();
	public abstract boolean isLeader();
	public abstract boolean isOp();
	public abstract boolean sendMessage(String message);
	public abstract void sendMessageToTeam(String message);
}
