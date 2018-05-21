package me.simplicitee.chipack;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffectType;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.CoreAbility;

import me.simplicitee.chipack.abilities.Jab;
import me.simplicitee.chipack.abilities.Jab.JabHand;
import me.simplicitee.chipack.abilities.NinjaStance;
import me.simplicitee.chipack.abilities.combos.WeakeningJab;

public class ChiPackListener implements Listener{

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if (event.isCancelled()) return;
		
		Entity entity = event.getEntity();
		
		if (entity instanceof Player) {
			if (CoreAbility.hasAbility((Player) entity, NinjaStance.class)) {
				NinjaStance ninja = CoreAbility.getAbility((Player) entity, NinjaStance.class);
				if (ninja.stealth && ninja.stealthReady && ((Player)entity).hasPotionEffect(PotionEffectType.INVISIBILITY)) {
					ninja.stopStealth();
				}
			}
		}
	}
	
	@EventHandler
	public void onHitDamage(EntityDamageByEntityEvent event) {
		if (event.isCancelled()) return;
		if (!(event.getDamager() instanceof Player)) return;
		
		Player player = (Player) event.getDamager();
		Entity entity = event.getEntity();
		
		if (WeakeningJab.isAffected(entity)) {
			event.setDamage(event.getDamage() * WeakeningJab.getModifier());
			if (entity instanceof LivingEntity) {
				((LivingEntity) entity).setNoDamageTicks(5);
			}
		}
		
		if (event.getCause() != DamageCause.ENTITY_ATTACK) return;
		if (GeneralMethods.isWeapon(player.getInventory().getItemInMainHand().getType())) return;
		
		if (CoreAbility.hasAbility(player, NinjaStance.class)) {
			NinjaStance ninja = CoreAbility.getAbility(player, NinjaStance.class);
			if (ninja.stealth && ninja.stealthReady && player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
				event.setCancelled(true);
			}
			
			event.setDamage(event.getDamage() * NinjaStance.getDamageModifier());
		}
		
		if (canBend(player, "Jab")) {
			if (CoreAbility.hasAbility(player, Jab.class)) {
				Jab jab = CoreAbility.getAbility(player, Jab.class);
				jab.activate(entity, JabHand.RIGHT);
			} else {
				new Jab(player, entity, JabHand.RIGHT);
			}
		}
	}

	@EventHandler
	public void onRightClickEntity(PlayerInteractEntityEvent event) {
		if (event.isCancelled()) return;
		
		Player player = event.getPlayer();
		Entity entity = event.getRightClicked();
		
		if (canBend(player, "Jab")) {
			if (CoreAbility.hasAbility(player, Jab.class)) {
				Jab jab = CoreAbility.getAbility(player, Jab.class);
				jab.activate(entity, JabHand.LEFT);
			} else {
				new Jab(player, entity, JabHand.LEFT);
			}
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onLeftClick(PlayerAnimationEvent event) {
		if (event.isCancelled()) return;
		
		Player player = event.getPlayer();
		
		if (canBend(player, "NinjaStance")) {
			new NinjaStance(player);
		}
		
		if (canBend(player, "AcrobatStance") || canBend(player, "WarriorStance")) {
			if (CoreAbility.hasAbility(player, NinjaStance.class)) {
				CoreAbility.getAbility(player, NinjaStance.class).remove();
			}
		}
	}
	
	@EventHandler
	public void onSneak(PlayerToggleSneakEvent event) {
		if (event.isCancelled()) return;
		if (!event.isSneaking()) return;
		
		Player player = event.getPlayer();
		
		if (canBend(player, "NinjaStance")) {
			if (CoreAbility.hasAbility(player, NinjaStance.class)) {
				NinjaStance ninja = CoreAbility.getAbility(player, NinjaStance.class);
				ninja.beginStealth();
			}
		}
	}
	
	public boolean canBend(Player player, String ability) {
		BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
		CoreAbility abil = CoreAbility.getAbility(ability);
		
		if (!bPlayer.getBoundAbilityName().equals(ability)) {
			return false;
		} else if (!bPlayer.canBend(abil)) {
			return false;
		}
		
		return true;
	}
}
