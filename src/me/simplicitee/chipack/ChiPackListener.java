package me.simplicitee.chipack;

import com.projectkorra.projectkorra.ability.*;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import me.simplicitee.chipack.abilities.combos.*;
import org.bukkit.entity.*;
import me.simplicitee.chipack.abilities.*;
import org.bukkit.event.player.*;
import com.projectkorra.projectkorra.*;

public class ChiPackListener implements Listener
{
    @EventHandler
    public void onDamage(final EntityDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }
        final Entity entity = event.getEntity();
        if (entity instanceof Player && CoreAbility.hasAbility((Player)entity, (Class)NinjaStance.class)) {
            final NinjaStance ninja = (NinjaStance)CoreAbility.getAbility((Player)entity, (Class)NinjaStance.class);
            if (ninja.stealth && ninja.stealthReady && ((Player)entity).hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                ninja.stopStealth();
            }
        }
    }
    
    @EventHandler
    public void onHitDamage(final EntityDamageByEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!(event.getDamager() instanceof Player)) {
            return;
        }
        final Player player = (Player)event.getDamager();
        final Entity entity = event.getEntity();
        if (WeakeningJab.isAffected(entity)) {
            event.setDamage(event.getDamage() * WeakeningJab.getModifier());
            if (entity instanceof LivingEntity) {
                ((LivingEntity)entity).setNoDamageTicks(5);
            }
        }
        if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            return;
        }
        if (GeneralMethods.isWeapon(player.getInventory().getItemInMainHand().getType())) {
            return;
        }
        if (CoreAbility.hasAbility(player, (Class)NinjaStance.class)) {
            final NinjaStance ninja = (NinjaStance)CoreAbility.getAbility(player, (Class)NinjaStance.class);
            if (ninja.stealth && ninja.stealthReady && player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                event.setCancelled(true);
            }
            event.setDamage(event.getDamage() * NinjaStance.getDamageModifier());
        }
        if (this.canBend(player, "Jab")) {
            if (CoreAbility.hasAbility(player, (Class)Jab.class)) {
                final Jab jab = (Jab)CoreAbility.getAbility(player, (Class)Jab.class);
                jab.activate(entity, Jab.JabHand.RIGHT);
            }
            else {
                new Jab(player, entity, Jab.JabHand.RIGHT);
            }
        }
    }
    
    @EventHandler
    public void onRightClickEntity(final PlayerInteractEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }
        final Player player = event.getPlayer();
        final Entity entity = event.getRightClicked();
        if (this.canBend(player, "Jab")) {
            if (CoreAbility.hasAbility(player, (Class)Jab.class)) {
                final Jab jab = (Jab)CoreAbility.getAbility(player, (Class)Jab.class);
                jab.activate(entity, Jab.JabHand.LEFT);
            }
            else {
                new Jab(player, entity, Jab.JabHand.LEFT);
            }
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onLeftClick(final PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }
        if (event.getAction() != Action.LEFT_CLICK_BLOCK && event.getAction() != Action.LEFT_CLICK_AIR) {
            return;
        }
        if (event.getAction() == Action.LEFT_CLICK_BLOCK && event.isCancelled()){
            return;
        }
        final Player player = event.getPlayer();
        if (this.canBend(player, "NinjaStance")) {
            new NinjaStance(player);
        }
        if ((this.canBend(player, "AcrobatStance") || this.canBend(player, "WarriorStance")) && CoreAbility.hasAbility(player, (Class)NinjaStance.class)) {
            ((NinjaStance)CoreAbility.getAbility(player, (Class)NinjaStance.class)).remove();
        }
    }
    
    @EventHandler
    public void onSneak(final PlayerToggleSneakEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!event.isSneaking()) {
            return;
        }
        final Player player = event.getPlayer();
        if (this.canBend(player, "NinjaStance") && CoreAbility.hasAbility(player, (Class)NinjaStance.class)) {
            final NinjaStance ninja = (NinjaStance)CoreAbility.getAbility(player, (Class)NinjaStance.class);
            ninja.beginStealth();
        }
    }
    
    public boolean canBend(final Player player, final String ability) {
        final BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
        final CoreAbility abil = CoreAbility.getAbility(ability);
        return bPlayer.getBoundAbilityName().equals(ability) && bPlayer.canBend(abil);
    }
}
