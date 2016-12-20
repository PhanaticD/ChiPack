package me.simplicitee.chipack;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.ability.CoreAbility;

import me.simplicitee.chipack.abilities.Jab;
import me.simplicitee.chipack.abilities.Jab.JabHand;

public class ChiPackListener implements Listener{

	@EventHandler
	public void onHitDamage(EntityDamageByEntityEvent event) {
		if (event.isCancelled()) return;
		if (!(event.getDamager() instanceof Player)) return;
		
		Player player = (Player)event.getDamager();
		Entity entity = event.getEntity();
		
		if (event.getCause() != DamageCause.ENTITY_ATTACK) return;
		
		//Next version hopefully
		/*if (WeakeningJab.isAffected(entity)) {
			double damage = event.getDamage();
			double newDmg = damage*WeakeningJab.getModifier();
			event.setDamage(newDmg);
		}*/
		
		if (BendingPlayer.getBendingPlayer(player).getBoundAbilityName().equals("Jab")) {
			if (BendingPlayer.getBendingPlayer(player).canBend(CoreAbility.getAbility("Jab"))) {
				new Jab(player, entity, JabHand.RIGHT);
			}
		}
	}

	@EventHandler
	public void onRightClickEntity(PlayerInteractEntityEvent event) {
		if (event.isCancelled()) return;
		
		Player player = event.getPlayer();
		Entity entity = event.getRightClicked();
		
		if (BendingPlayer.getBendingPlayer(player).getBoundAbilityName().equals("Jab")) {
			if (BendingPlayer.getBendingPlayer(player).canBend(CoreAbility.getAbility("Jab"))) {
				new Jab(player, entity, JabHand.LEFT);
			}
		}
	}
}
