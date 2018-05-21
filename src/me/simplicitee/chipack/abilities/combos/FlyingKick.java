package me.simplicitee.chipack.abilities.combos;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.ChiAbility;
import com.projectkorra.projectkorra.ability.ComboAbility;
import com.projectkorra.projectkorra.ability.util.ComboManager.AbilityInformation;
import com.projectkorra.projectkorra.util.ClickType;
import com.projectkorra.projectkorra.util.DamageHandler;

import me.simplicitee.chipack.configuration.ConfigHandler;

public class FlyingKick extends ChiAbility implements ComboAbility, AddonAbility{
	
	private double launch;
	private double damage;

	public FlyingKick(Player player) {
		super(player);
		
		if (hasAbility(player, FlyingKick.class)) {
			return;
		}
		
		if (player.getLocation().getBlock().isLiquid()) {
			return;
		}
		
		if (!player.isOnGround()) {
			return;
		}
		
		launch = ConfigHandler.getConfig().getDouble("Combos.FlyingKick.LaunchPower");
		damage = ConfigHandler.getConfig().getDouble("Combos.FlyingKick.Damage");
		Vector v = player.getLocation().getDirection().add(new Vector(0, 0.25485, 0)).normalize().multiply(launch);
		player.setVelocity(v);
		start();
	}

	@Override
	public long getCooldown() {
		return ConfigHandler.getConfig().getLong("Combos.FlyingKick.Cooldown");
	}

	@Override
	public Location getLocation() {
		return player.getLocation();
	}

	@Override
	public String getName() {
		return "FlyingKick";
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
		if (player == null) {
			remove();
			return;
		}
		
		if (!player.isOnline() || player.isDead()) {
			remove();
			return;
		}
		
		if (player.getLocation().subtract(0, 0.1, 0).getBlock().getType() != Material.AIR) {
			remove();
			bPlayer.addCooldown(this);
			return;
		}
		
		for (Entity entity : GeneralMethods.getEntitiesAroundPoint(player.getLocation(), 2)) {
			if (entity instanceof LivingEntity && entity.getEntityId() != player.getEntityId()) {
				DamageHandler.damageEntity(entity, player, damage, this);
			}
		}
	}

	@Override
	public Object createNewComboInstance(Player player) {
		return new FlyingKick(player);
	}

	@Override
	public ArrayList<AbilityInformation> getCombination() {
		ArrayList<AbilityInformation> combo = new ArrayList<>();
		combo.add(new AbilityInformation("SwiftKick", ClickType.SHIFT_DOWN));
		combo.add(new AbilityInformation("SwiftKick", ClickType.LEFT_CLICK));
		return combo;
	}
	
	@Override
	public String getDescription() {
		return ConfigHandler.getLang().getString("Combos.FlyingKick.Description");
	}

	@Override
	public String getInstructions() {
		return "SwiftKick (Hold sneak) > SwiftKick (Left Click)";
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
		return ConfigHandler.getConfig().getBoolean("Combos.FlyingKick.Enabled");
	}
}
