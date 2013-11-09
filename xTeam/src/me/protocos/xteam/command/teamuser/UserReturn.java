package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.command.action.TeleportScheduler;
import me.protocos.xteam.core.ReturnLocation;
import me.protocos.xteam.core.exception.TeamException;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

public class UserReturn extends UserCommand
{
	public UserReturn()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		TeleportScheduler teleporter = TeleportScheduler.getInstance();
		Location returnLocation = teamPlayer.getReturnLocation();
		teleporter.teleport(teamPlayer, new ReturnLocation(returnLocation));
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		Requirements.checkPlayerHasTeam(teamPlayer);
		Requirements.checkPlayerHasReturnLocation(teamPlayer);
		Requirements.checkPlayerNotDamaged(teamPlayer);
		Requirements.checkPlayerLastAttacked(teamPlayer);
		Requirements.checkPlayerTeleportRequested(teamPlayer);
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("return") + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.player.core.return";
	}
	@Override
	public String getUsage()
	{
		return "/team return";
	}
}
