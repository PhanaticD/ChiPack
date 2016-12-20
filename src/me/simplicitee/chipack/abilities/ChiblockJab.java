package me.simplicitee.chipack.abilities;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.ChiAbility;
import com.projectkorra.projectkorra.ability.ComboAbility;
import com.projectkorra.projectkorra.ability.util.ComboManager.AbilityInformation;
import com.projectkorra.projectkorra.util.ClickType;

import me.simplicitee.chipack.configuration.ConfigHandler;

public class ChiblockJab extends ChiAbility implements ComboAbility{
	
	private Player attacked;

	public ChiblockJab(Player player) {
		super(player);
		Entity entity = GeneralMethods.getTargetedEntity(player, 4);
		if (entity instanceof Player) {
			attacked = (Player) entity;
		}
		if (attacked != null) {
			start();
		}
		bPlayer.addCooldown("Jab", getCooldown());
	}

	@Override
	public long getCooldown() {
		// TODO Auto-generated method stub
		return ConfigHandler.getConfig().getLong("Combos.ChiblockJab.Cooldown");
	}

	@Override
	public Location getLocation() {
		// TODO Auto-generated method stub
		return attacked.getLocation().clone().add(0, 1, 0);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "ChiblockJab";
	}

	@Override
	public boolean isHarmlessAbility() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSneakAbility() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void progress() {
		// TODO Auto-generated method stub
		BendingPlayer bp = BendingPlayer.getBendingPlayer(attacked);
		if (bp != null) {
			bp.blockChi();
		}
	}

	@Override
	public Object createNewComboInstance(Player player) {
		// TODO Auto-generated method stub
		return new ChiblockJab(player);
	}

	@Override
	public ArrayList<AbilityInformation> getCombination() {
		ArrayList<AbilityInformation> combo = new ArrayList<>();
		combo.add(new AbilityInformation("Jab", ClickType.RIGHT_CLICK_ENTITY));
		combo.add(new AbilityInformation("Jab", ClickType.LEFT_CLICK));
		combo.add(new AbilityInformation("Jab", ClickType.RIGHT_CLICK_ENTITY));
		return combo;
	}

	@Override
	public String getInstructions() {
		// TODO Auto-generated method stub
		return "Jab (Right) > Jab (Left) > Jab (Right)";
	}

	@Override
	public boolean isHiddenAbility() {
		return true;
	}
}
