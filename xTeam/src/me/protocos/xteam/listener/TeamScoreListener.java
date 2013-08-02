package me.protocos.xteam.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
public class TeamScoreListener implements Listener
{
	@EventHandler
	public void onEntityDamage(@SuppressWarnings("unused") EntityDamageEvent event)
	{
	}
	@EventHandler
	public void onEntityDeath(@SuppressWarnings("unused") EntityDeathEvent event)
	{
		//		Player victim;
		//		Player killer;
		//		if (mockEvent.getEntity() instanceof Player)
		//		{
		//			victim = (Player) mockEvent.getEntity();
		//			if (victim.getKiller()!=null)
		//			{
		//				killer = victim.getKiller();
		//				Team attTeam = getTeam(killer);
		//				Team defTeam = getTeam(victim);
		//				if(!attTeam.getName().equalsIgnoreCase(defTeam.getName()))
		//				{
		//					attTeam.addToScore(10);
		//					defTeam.subFromScore(10);
		//				}
		//			}
		//			else
		//			{
		//				p("Unknown damage cause: " + mockEvent.getEntity().getLastDamageCause());
		//				return;
		//			}
		//		}
	}
}
