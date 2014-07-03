package me.protocos.xteam.command;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.entity.Player;

public abstract class PlayerCommand extends BaseCommand implements IPermissible
{
	protected enum Classification
	{
		SERVER_ADMIN(4),
		TEAM_LEADER(3),
		TEAM_ADMIN(2),
		TEAM_USER(1);

		private int rank;

		private Classification(int rank)
		{
			this.rank = rank;
		}

		public int getRank()
		{
			return rank;
		}
	}

	public PlayerCommand(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	public void preInitialize(CommandContainer commandContainer) throws TeamException
	{
		Player player = CommonUtil.assignFromType(commandContainer.getSender(), Player.class);
		Requirements.checkPlayerWorldDisabled(player);
		Requirements.checkPlayerHasPermission(commandContainer.getSender(), this);
		Requirements.checkCommandIsValid(commandContainer.getCommandWithoutID(), this.getPattern());
	}

	public abstract Classification getClassification();
}