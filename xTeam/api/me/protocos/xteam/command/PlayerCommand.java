package me.protocos.xteam.command;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.TeamPlayer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.entity.Player;

public abstract class PlayerCommand extends BaseCommand implements IPermissible
{
	public PlayerCommand(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	protected TeamPlayer teamPlayer;
	protected ITeam team;

	@Override
	public void preInitialize(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		Player player = CommonUtil.assignFromType(commandContainer.getSender(), Player.class);
		teamPlayer = playerFactory.getPlayer(player);
		team = teamPlayer.getTeam();
		Requirements.checkPlayerWorldDisabled(teamPlayer);
		Requirements.checkPlayerHasPermission(commandContainer.getSender(), this);
		Requirements.checkPlayerCommandIsValid(commandContainer.getCommandWithoutID(), this.getPattern());
	}
}