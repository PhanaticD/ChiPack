package me.simplicitee.chipack.abilities.combos;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.ChiAbility;
import com.projectkorra.projectkorra.ability.ComboAbility;
import com.projectkorra.projectkorra.ability.util.ComboManager.AbilityInformation;
import com.projectkorra.projectkorra.util.ClickType;
import com.projectkorra.projectkorra.util.ParticleEffect;

import me.simplicitee.chipack.configuration.ConfigHandler;

public class ChiblockJab extends ChiAbility implements ComboAbility, AddonAbility{
	
	private Player attacked;
	private long duration;
	private BendingPlayer bp;

	public ChiblockJab(Player player) {
		super(player);
		
		Entity entity = GeneralMethods.getTargetedEntity(player, 4);
		
		if (entity instanceof Player) {
			attacked = (Player) entity;
		} else {
			return;
		}
		
		duration = ConfigHandler.getConfig().getLong("Combos.ChiblockJab.Duration");
		
		if (attacked != null) {
			bp = BendingPlayer.getBendingPlayer(attacked);
			
			if (bp != null) {
				bp.blockChi();
				start();
			}
		}
	}

	@Override
	public long getCooldown() {
		return ConfigHandler.getConfig().getLong("Combos.ChiblockJab.Cooldown");
	}

	@Override
	public Location getLocation() {
		return attacked.getLocation().clone().add(0, 1, 0);
	}

	@Override
	public String getName() {
		return "ChiblockJab";
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
		ParticleEffect.CRIT.display(attacked.getLocation().clone().add(0, 1, 0), 0.2f, 1.0f, 0.2f, 0.04f, 3);
		if (System.currentTimeMillis() >= getStartTime() + duration) {
			remove();
			bPlayer.addCooldown(this);
			bp.unblockChi();
		}
	}

	@Override
	public Object createNewComboInstance(Player player) {
		return new ChiblockJab(player);
	}

	@Override
	public ArrayList<AbilityInformation> getCombination() {
		ArrayList<AbilityInformation> combo = new ArrayList<>();
		combo.add(new AbilityInformation("Jab", ClickType.RIGHT_CLICK_ENTITY));
		combo.add(new AbilityInformation("Jab", ClickType.LEFT_CLICK_ENTITY));
		combo.add(new AbilityInformation("Jab", ClickType.RIGHT_CLICK_ENTITY));
		return combo;
	}
	
	@Override
	public String getDescription() {
		return ConfigHandler.getLang().getString("Combos.ChiblockJab.Description");
	}

	@Override
	public String getInstructions() {
		return "Jab (Right) > Jab (Left) > Jab (Right)";
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
		return ConfigHandler.getConfig().getBoolean("Combos.ChiblockJab.Enabled");
	}
}
