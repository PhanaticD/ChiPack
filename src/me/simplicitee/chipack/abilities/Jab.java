package me.simplicitee.chipack.abilities;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.ChiAbility;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.ParticleEffect;

import me.simplicitee.chipack.abilities.combos.WeakeningJab;
import me.simplicitee.chipack.configuration.ConfigHandler;

public class Jab extends ChiAbility implements AddonAbility{
	
	public static enum JabHand {
		RIGHT, LEFT;
	}
	
	private int uses = 0;
	private long cooldown;
	private int maxUses;

	public Jab(Player player, Entity entity, JabHand hand) {
		super(player);
		
		cooldown = ConfigHandler.getConfig().getLong("Abilities.Jab.Cooldown");
		maxUses = ConfigHandler.getConfig().getInt("Abilities.Jab.MaxUses");
		
		start();
		activate(entity, hand);
	}
	
	public void activate(Entity entity, JabHand hand) {
		if (entity instanceof LivingEntity) {
			LivingEntity lent = (LivingEntity) entity;
			uses++;
			
			ParticleEffect.END_ROD.display(entity.getLocation().clone().add(0, 1, 0), 0.2f, 0.2f, 0.2f, 0.02f, 4);
			if (hand == JabHand.LEFT) {
				double damage = WeakeningJab.isAffected(lent) ? WeakeningJab.getModifier() : 1;
				
				DamageHandler.damageEntity(entity, player, damage, this);
			}
			
			lent.setNoDamageTicks(0);
		}
	}

	@Override
	public long getCooldown() {
		return cooldown;
	}

	@Override
	public Location getLocation() {
		return null;
	}

	@Override
	public String getName() {
		return "Jab";
	}

	@Override
	public boolean isHarmlessAbility() {
		return false;
	}

	@Override
	public boolean isSneakAbility() {
		return false;
	}

	@Override
	public void progress() {
		if (uses >= maxUses) {
			remove();
			bPlayer.addCooldown(this);
		}
	}
	
	@Override
	public String getDescription() {
		return ConfigHandler.getLang().getString("Abilities.Jab.Description");
	}
	
	@Override
	public String getInstructions() {
		return "Left click or Right click";
	}

	@Override
	public String getAuthor() {
		return "Simp";
	}

	@Override
	public String getVersion() {
		return "ChiPack";
	}

	@Override
	public void load() {}

	@Override
	public void stop() {}
	
	@Override
	public boolean isEnabled() {
		return ConfigHandler.getConfig().getBoolean("Abilities.Jab.Enabled");
	}
}
