package me.protocos.xteam.api.fakeobjects;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.logging.Logger;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.*;
import org.bukkit.Warning.WarningState;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.help.HelpMap;
import org.bukkit.inventory.*;
import org.bukkit.map.MapView;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.util.CachedServerIcon;
import com.avaje.ebean.config.ServerConfig;

public class FakeServer implements Server
{
	private World world;
	private Player[] onlinePlayers;
	private OfflinePlayer[] offlinePlayers;
	private BukkitScheduler fakeScheduler;
	private PluginManager fakePluginManager;

	public FakeServer()
	{
		world = new FakeWorld();
		onlinePlayers = new Player[0];
		offlinePlayers = new OfflinePlayer[0];
		fakeScheduler = new FakeScheduler();
		fakePluginManager = new FakePluginManager();
	}

	public void setWorld(World world)
	{
		this.world = world;
	}

	public void setOnlinePlayers(Player[] onlinePlayers)
	{
		this.onlinePlayers = onlinePlayers;
	}

	public void setOfflinePlayers(OfflinePlayer[] offlinePlayers)
	{
		this.offlinePlayers = offlinePlayers;
	}

	@Override
	public boolean addRecipe(Recipe arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void banIP(String arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public int broadcast(String arg0, String arg1)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int broadcastMessage(String arg0)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clearRecipes()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void configureDbConfig(ServerConfig arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public Inventory createInventory(InventoryHolder arg0, int arg1)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Inventory createInventory(InventoryHolder arg0, int arg1, String arg2)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Inventory createInventory(InventoryHolder arg0, InventoryType arg1)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MapView createMap(World arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public World createWorld(WorldCreator arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean dispatchCommand(CommandSender arg0, String arg1) throws CommandException
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getAllowEnd()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getAllowFlight()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getAllowNether()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getAmbientSpawnLimit()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAnimalSpawnLimit()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<OfflinePlayer> getBannedPlayers()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getBukkitVersion()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String[]> getCommandAliases()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getConnectionThrottle()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ConsoleCommandSender getConsoleSender()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameMode getDefaultGameMode()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getGenerateStructures()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public HelpMap getHelpMap()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getIp()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getIPBans()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemFactory getItemFactory()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getListeningPluginChannels()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Logger getLogger()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MapView getMap(short arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMaxPlayers()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Messenger getMessenger()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMonsterSpawnLimit()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getMotd()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OfflinePlayer getOfflinePlayer(String name)
	{
		for (OfflinePlayer p : offlinePlayers)
		{
			if (p.getName().equals(name))
			{
				return p;
			}
		}
		return null;
	}

	@Override
	public OfflinePlayer[] getOfflinePlayers()
	{
		return offlinePlayers;
	}

	@Override
	public boolean getOnlineMode()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Player[] getOnlinePlayers()
	{
		return onlinePlayers;
	}

	@Override
	public Set<OfflinePlayer> getOperators()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player getPlayer(String name)
	{
		for (Player p : onlinePlayers)
		{
			if (p.getName().equals(name))
			{
				return p;
			}
		}
		return null;
	}

	@Override
	public Player getPlayerExact(String arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PluginCommand getPluginCommand(String arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PluginManager getPluginManager()
	{
		return fakePluginManager;
	}

	@Override
	public int getPort()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Recipe> getRecipesFor(ItemStack arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BukkitScheduler getScheduler()
	{
		return fakeScheduler;
	}

	@Override
	public ScoreboardManager getScoreboardManager()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServerId()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServerName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServicesManager getServicesManager()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getShutdownMessage()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSpawnRadius()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTicksPerAnimalSpawns()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTicksPerMonsterSpawns()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getUpdateFolder()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getUpdateFolderFile()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getVersion()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getViewDistance()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public WarningState getWarningState()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getWaterAnimalSpawnLimit()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<OfflinePlayer> getWhitelistedPlayers()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public World getWorld(String arg0)
	{
		return world;
	}

	@Override
	public World getWorld(UUID arg0)
	{
		return world;
	}

	@Override
	public File getWorldContainer()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<World> getWorlds()
	{
		List<World> worlds = CommonUtil.emptyList();
		worlds.add(world);
		return worlds;
	}

	@Override
	public String getWorldType()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasWhitelist()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isHardcore()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPrimaryThread()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Player> matchPlayer(String arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<Recipe> recipeIterator()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void reload()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void reloadWhitelist()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void resetRecipes()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void savePlayers()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void sendPluginMessage(Plugin arg0, String arg1, byte[] arg2)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setDefaultGameMode(GameMode arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setSpawnRadius(int arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setWhitelist(boolean arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void shutdown()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void unbanIP(String arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean unloadWorld(String arg0, boolean arg1)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean unloadWorld(World arg0, boolean arg1)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean useExactLoginLocation()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CachedServerIcon getServerIcon()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CachedServerIcon loadServerIcon(File arg0) throws IllegalArgumentException, Exception
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CachedServerIcon loadServerIcon(BufferedImage arg0) throws IllegalArgumentException, Exception
	{
		// TODO Auto-generated method stub
		return null;
	}

}