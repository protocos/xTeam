package me.protocos.xteam;

import java.util.logging.Logger;
import me.protocos.xteam.api.TeamPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class xTeamPlugin extends JavaPlugin
{
	public static final Logger log = Logger.getLogger("Minecraft");

	private TeamPlugin xteam;

	public xTeamPlugin()
	{
		xteam = xTeam.getInstance();
		//		log.addHandler(new LogHandler(xteam));
	}

	@Override
	public void onLoad()
	{
		xteam.onLoad();
	}

	@Override
	public void onEnable()
	{
		xteam.onEnable();
	}

	@Override
	public void onDisable()
	{
		xteam.onDisable();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		return xteam.onCommand(sender, command, label, args);
	}
}
