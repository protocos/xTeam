package me.protocos.xteam.api.command;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class PlayerCommand extends BaseCommand implements IPermissible
{
	protected TeamPlayer teamPlayer;
	protected Team team;

	@Override
	public void initData(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		Player player = CommonUtil.assignFromType(originalSender, Player.class);
		teamPlayer = xTeam.getInstance().getPlayerManager().getPlayer(player);
		team = teamPlayer.getTeam();
		Requirements.checkPlayerHasPermission(originalSender, this);
		Requirements.checkPlayerCommandIsValid(parseCommand, this.getPattern());
	}
}