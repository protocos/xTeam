package me.protocos.xteam.testing;

import java.net.InetSocketAddress;
import java.util.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.*;
import org.bukkit.inventory.InventoryView.Property;
import org.bukkit.map.MapView;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

public class FakePlayer implements Player
{
	private String name;
	private boolean isOp;
	private boolean isOnline;
	private double health;
	private Location location;
	private int noDamageTicks;
	private Server server;

	public FakePlayer()
	{
		this("online");
	}

	public FakePlayer(String name)
	{
		this(name, false, false, -1, new FakeLocation());
	}

	public FakePlayer(String name, boolean isOp, boolean isOnline, double health, Location location)
	{
		this(name, isOp, isOnline, health, location, null);
	}

	public FakePlayer(String name, boolean isOp, boolean isOnline, double health, Location location, Server server)
	{
		this.name = name;
		this.isOp = isOp;
		this.isOnline = isOnline;
		this.health = health;
		this.location = location;
		this.server = server;
	}

	@Override
	@Deprecated
	public void _INVALID_damage(int arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	@Deprecated
	public void _INVALID_damage(int arg0, Entity arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override
	@Deprecated
	public int _INVALID_getHealth()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Deprecated
	public int _INVALID_getLastDamage()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Deprecated
	public int _INVALID_getMaxHealth()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Deprecated
	public void _INVALID_setHealth(int arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	@Deprecated
	public void _INVALID_setLastDamage(int arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	@Deprecated
	public void _INVALID_setMaxHealth(int arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void abandonConversation(Conversation arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void abandonConversation(Conversation arg0, ConversationAbandonedEvent arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void acceptConversationInput(String arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0, int arg1)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2, int arg3)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addPotionEffect(PotionEffect arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addPotionEffect(PotionEffect arg0, boolean arg1)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addPotionEffects(Collection<PotionEffect> arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void awardAchievement(Achievement arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean beginConversation(Conversation arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canSee(Player arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void chat(String arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void closeInventory()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void damage(double arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void damage(double arg0, Entity arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean eject()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<PotionEffect> getActivePotionEffects()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InetSocketAddress getAddress()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getAllowFlight()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Location getBedSpawnLocation()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getCanPickupItems()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Location getCompassTarget()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCustomName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDisplayName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<PermissionAttachmentInfo> getEffectivePermissions()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Inventory getEnderChest()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getEntityId()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public EntityEquipment getEquipment()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getExhaustion()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getExp()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getExpToLevel()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getEyeHeight()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getEyeHeight(boolean arg0)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Location getEyeLocation()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getFallDistance()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFireTicks()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getFirstPlayed()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getFlySpeed()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFoodLevel()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public GameMode getGameMode()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getHealth()
	{
		return health;
	}

	@Override
	public PlayerInventory getInventory()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack getItemInHand()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack getItemOnCursor()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player getKiller()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getLastDamage()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public EntityDamageEvent getLastDamageCause()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getLastPlayed()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Block> getLastTwoTargetBlocks(HashSet<Byte> arg0, int arg1)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLevel()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Block> getLineOfSight(HashSet<Byte> arg0, int arg1)
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
	public Location getLocation()
	{
		return location;
	}

	@Override
	public Location getLocation(Location arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMaxFireTicks()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getMaxHealth()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaximumAir()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaximumNoDamageTicks()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<MetadataValue> getMetadata(String arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public List<Entity> getNearbyEntities(double arg0, double arg1, double arg2)
	{
		return new ArrayList<Entity>();
	}

	@Override
	public int getNoDamageTicks()
	{
		return noDamageTicks;
	}

	@Override
	public InventoryView getOpenInventory()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entity getPassenger()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player getPlayer()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPlayerListName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getPlayerTime()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getPlayerTimeOffset()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public WeatherType getPlayerWeather()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRemainingAir()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getRemoveWhenFarAway()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public float getSaturation()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Scoreboard getScoreboard()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Server getServer()
	{
		return server;
	}

	@Override
	public int getSleepTicks()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Block getTargetBlock(HashSet<Byte> arg0, int arg1)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTicksLived()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalExperience()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public EntityType getType()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UUID getUniqueId()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entity getVehicle()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector getVelocity()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getWalkSpeed()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public World getWorld()
	{
		return location.getWorld();
	}

	@Override
	public void giveExp(int arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void giveExpLevels(int arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean hasLineOfSight(Entity arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasMetadata(String arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasPermission(Permission arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasPermission(String arg0)
	{
		return true;
	}

	@Override
	public boolean hasPlayedBefore()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasPotionEffect(PotionEffectType arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void hidePlayer(Player arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void incrementStatistic(Statistic arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void incrementStatistic(Statistic arg0, int arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void incrementStatistic(Statistic arg0, Material arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void incrementStatistic(Statistic arg0, Material arg1, int arg2)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isBanned()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isBlocking()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isConversing()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCustomNameVisible()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDead()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFlying()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInsideVehicle()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@Deprecated
	public boolean isOnGround()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOnline()
	{
		return isOnline;
	}

	@Override
	public boolean isOp()
	{
		return isOp;
	}

	@Override
	public boolean isPermissionSet(Permission arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPermissionSet(String arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPlayerTimeRelative()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isScaledHealth()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSleeping()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSleepingIgnored()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSneaking()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSprinting()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isValid()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isWhitelisted()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void kickPlayer(String arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public <T extends Projectile> T launchProjectile(Class<? extends T> arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean leaveVehicle()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void loadData()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public InventoryView openEnchanting(Location arg0, boolean arg1)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InventoryView openInventory(Inventory arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void openInventory(InventoryView arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public InventoryView openWorkbench(Location arg0, boolean arg1)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean performCommand(String arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void playEffect(EntityEffect arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void playEffect(Location arg0, Effect arg1, int arg2)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public <T> void playEffect(Location arg0, Effect arg1, T arg2)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void playNote(Location arg0, byte arg1, byte arg2)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void playNote(Location arg0, Instrument arg1, Note arg2)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void playSound(Location arg0, Sound arg1, float arg2, float arg3)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void recalculatePermissions()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void remove()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void removeAttachment(PermissionAttachment arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void removeMetadata(String arg0, Plugin arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void removePotionEffect(PotionEffectType arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void resetMaxHealth()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void resetPlayerTime()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void resetPlayerWeather()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void saveData()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void sendBlockChange(Location arg0, int arg1, byte arg2)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void sendBlockChange(Location arg0, Material arg1, byte arg2)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean sendChunkChange(Location arg0, int arg1, int arg2, int arg3, byte[] arg4)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void sendMap(MapView arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void sendMessage(String arg0)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void sendMessage(String[] arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void sendPluginMessage(Plugin arg0, String arg1, byte[] arg2)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void sendRawMessage(String arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public Map<String, Object> serialize()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAllowFlight(boolean arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setBanned(boolean arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setBedSpawnLocation(Location arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setBedSpawnLocation(Location arg0, boolean arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setCanPickupItems(boolean arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setCompassTarget(Location arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setCustomName(String arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setCustomNameVisible(boolean arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setDisplayName(String arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setExhaustion(float arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setExp(float arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setFallDistance(float arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setFireTicks(int arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setFlying(boolean arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setFlySpeed(float arg0) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setFoodLevel(int arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setGameMode(GameMode arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setHealth(double health)
	{
		this.health = health;
	}

	@Override
	public void setItemInHand(ItemStack arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setItemOnCursor(ItemStack arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setLastDamage(double arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setLastDamageCause(EntityDamageEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setLevel(int arg0)
	{
		// TODO Auto-generated method stub

	}

	public void setLocation(Location location)
	{
		this.location = location;
	}

	@Override
	public void setMaxHealth(double arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setMaximumAir(int arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setMaximumNoDamageTicks(int arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setMetadata(String arg0, MetadataValue arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setNoDamageTicks(int noDamageTicks)
	{
		this.noDamageTicks = noDamageTicks;
	}

	public void setOnline(boolean isOnline)
	{
		this.isOnline = isOnline;
	}

	@Override
	public void setOp(boolean isOp)
	{
		this.isOp = isOp;
	}

	@Override
	public boolean setPassenger(Entity arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setPlayerListName(String arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setPlayerTime(long arg0, boolean arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setPlayerWeather(WeatherType arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setRemainingAir(int arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setRemoveWhenFarAway(boolean arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setSaturation(float arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setScaleHealth(boolean arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setScoreboard(Scoreboard arg0) throws IllegalArgumentException, IllegalStateException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setSleepingIgnored(boolean arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setSneaking(boolean arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setSprinting(boolean arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setTexturePack(String arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setTicksLived(int arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setTotalExperience(int arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setVelocity(Vector arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setWalkSpeed(float arg0) throws IllegalArgumentException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setWhitelisted(boolean arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean setWindowProperty(Property arg0, int arg1)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@Deprecated
	public Arrow shootArrow()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void showPlayer(Player arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean teleport(Entity entity)
	{
		setLocation(entity.getLocation());
		return true;
	}
	@Override
	public boolean teleport(Entity entity, TeleportCause arg1)
	{
		setLocation(entity.getLocation());
		return true;
	}

	@Override
	public boolean teleport(Location loc)
	{
		setLocation(loc);
		return true;
	}

	@Override
	public boolean teleport(Location loc, TeleportCause arg1)
	{
		setLocation(loc);
		return false;
	}

	@Override
	@Deprecated
	public Egg throwEgg()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Deprecated
	public Snowball throwSnowball()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Deprecated
	public void updateInventory()
	{
		// TODO Auto-generated method stub

	}
}