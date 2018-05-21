package me.simplicitee.chipack.abilities.combos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.ChiAbility;
import com.projectkorra.projectkorra.ability.ComboAbility;
import com.projectkorra.projectkorra.ability.util.ComboManager.AbilityInformation;
import com.projectkorra.projectkorra.util.ClickType;
import com.projectkorra.projectkorra.util.ParticleEffect;

import me.simplicitee.chipack.configuration.ConfigHandler;

public class WeakeningJab extends ChiAbility implements ComboAbility, AddonAbility{

	private static Set<Integer> entities = new HashSet<>();
	
	public LivingEntity entity = null;
	public long duration;
	
	public WeakeningJab(Player player) {
		super(player);
		
		Entity e = GeneralMethods.getTargetedEntity(player, 4);
		if (e instanceof LivingEntity) {
			entity = (LivingEntity) e;
		} else {
			return;
		}
		
		duration = ConfigHandler.getConfig().getLong("Combos.WeakeningJab.Duration");
		
		if (entity != null && !entities.contains(entity.getEntityId())) {
			entities.add(entity.getEntityId());
			start();
		}
	}

	@Override
	public long getCooldown() {
		return ConfigHandler.getConfig().getLong("Combos.WeakeningJab.Cooldown");
	}

	@Override
	public Location getLocation() {
		return entity.getLocation().clone().add(0, 1, 0);
	}

	@Override
	public String getName() {
		return "WeakeningJab";
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
		ParticleEffect.DAMAGE_INDICATOR.display(entity.getLocation(), 0.2f, 1.0f, 0.2f, 0.0004f, 3);
		if (System.currentTimeMillis() >= getStartTime() + duration) {
			entities.remove(entity.getEntityId());
			remove();
			bPlayer.addCooldown(this);
		}
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
	public Object createNewComboInstance(Player player) {
		return new WeakeningJab(player);
	}

	@Override
	public ArrayList<AbilityInformation> getCombination() {
		ArrayList<AbilityInformation> combo = new ArrayList<>();
		combo.add(new AbilityInformation("Jab", ClickType.LEFT_CLICK_ENTITY));
		combo.add(new AbilityInformation("Jab", ClickType.LEFT_CLICK_ENTITY));
		combo.add(new AbilityInformation("Jab", ClickType.RIGHT_CLICK_ENTITY));
		return combo;
	}
	
	@Override
	public String getDescription() {
		return ConfigHandler.getLang().getString("Combos.WeakeningJab.Description");
	}
	
	@Override
	public String getInstructions() {
		return "Jab (Left) > Jab (Left) > Jab (Right)";
	}

	@Override
	public boolean isEnabled() {
		return ConfigHandler.getConfig().getBoolean("Combos.WeakeningJab.Enabled");
	}
	
	public static boolean isAffected(Entity e) {
		return entities.contains(e.getEntityId());
	}
	
	public static double getModifier() {
		return ConfigHandler.getConfig().getDouble("Combos.WeakeningJab.Modifier");
	}
}
