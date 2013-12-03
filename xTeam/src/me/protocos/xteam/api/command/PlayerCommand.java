package me.protocos.xteam.api.command;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.model.ITeam;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.entity.TeamPlayer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.entity.Player;

public abstract class PlayerCommand extends BaseCommand implements IPermissible
{
	protected TeamPlayer teamPlayer;
	protected ITeam team;

	@Override
	public void preInitialize(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		Player player = CommonUtil.assignFromType(commandContainer.getSender(), Player.class);
		teamPlayer = xTeam.getInstance().getPlayerManager().getPlayer(player);
		team = teamPlayer.getTeam();
		Requirements.checkPlayerHasPermission(commandContainer.getSender(), this);
		Requirements.checkPlayerCommandIsValid(commandContainer.getCommandWithoutID(), this.getPattern());
	}
}