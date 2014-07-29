package me.protocos.xteam.data.translator;

import java.util.List;
import me.protocos.api.util.CommonUtil;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.exception.InvalidFormatException;
import me.protocos.xteam.model.ILog;
import me.protocos.xteam.util.BukkitUtil;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationDataTranslator implements IDataTranslator<Location>
{
	private BukkitUtil bukkitUtil;
	private ILog log;

	public LocationDataTranslator(TeamPlugin teamPlugin)
	{
		this.bukkitUtil = teamPlugin.getBukkitUtil();
		this.log = teamPlugin.getLog();
	}

	@Override
	public String decompile(Location location)
	{
		if (location == null)
			return "";
		return location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch();
	}

	@Override
	public Location compile(String formattedString)
	{
		if ("".equals(formattedString))
			return null;
		List<String> components = CommonUtil.split(formattedString, ",");
		if (components.size() != 6)
			throw new InvalidFormatException(formattedString, "world,x,y,z,yaw,pitch");
		try
		{
			World world = bukkitUtil.getWorld(components.get(0));
			Double x = Double.valueOf(components.get(1));
			Double y = Double.valueOf(components.get(2));
			Double z = Double.valueOf(components.get(3));
			Float yaw = Float.valueOf(components.get(4));
			Float pitch = Float.valueOf(components.get(5));
			return new Location(world, x, y, z, yaw, pitch);
		}
		catch (Exception e)
		{
			log.exception(e);
		}
		return null;
	}
}
