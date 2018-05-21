package me.simplicitee.chipack.abilities;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.ChiAbility;

import me.simplicitee.chipack.configuration.ConfigHandler;

public class NinjaStance extends ChiAbility implements AddonAbility{
	
	public boolean stealth, stealthReady, stealthStarted;
	public long stealthStart;
	public long stealthChargeTime;
	public long stealthReadyStart;
	public long stealthDuration;
	public int speedAmp, jumpAmp;

	public NinjaStance(Player player) {
		super(player);
		ChiAbility stance = bPlayer.getStance();
		if (stance != null) {
			stance.remove();
			if (stance instanceof NinjaStance) {
				bPlayer.setStance(null);
				return;
			}
		}
		
		stealthDuration = ConfigHandler.getConfig().getLong("Abilities.NinjaStance.Stealth.Duration");
		stealthChargeTime = ConfigHandler.getConfig().getLong("Abilities.NinjaStance.Stealth.ChargeTime");
		speedAmp = ConfigHandler.getConfig().getInt("Abilities.NinjaStance.SpeedAmplifier") + 1;
		jumpAmp = ConfigHandler.getConfig().getInt("Abilities.NinjaStance.JumpAmplifier") + 1;
		
		start();
		bPlayer.setStance(this);
		GeneralMethods.displayMovePreview(player);
		player.playSound(player.getLocation(), Sound.ENTITY_ENDERDRAGON_HURT, 0.5F, 2F);
	}

	@Override
	public long getCooldown() {
		return ConfigHandler.getConfig().getLong("Abilities.NinjaStance.Cooldown");
	}

	@Override
	public Location getLocation() {
		return player.getLocation();
	}

	@Override
	public String getName() {
		return "NinjaStance";
	}

	@Override
	public boolean isHarmlessAbility() {
		return true;
	}

	@Override
	public boolean isSneakAbility() {
		return true;
	}

	@Override
	public void progress() {
		if (!player.isOnline() || player.isDead()) {
			remove();
			return;
		}
		
		if (stealth) {
			if (System.currentTimeMillis() >= stealthStart + stealthChargeTime) {
				stealthReady = true;
			}
			
			if (!stealthStarted) {
				if (stealthReady && !player.isSneaking()) {
					stealthReadyStart = System.currentTimeMillis();
					stealthStarted = true;
				} else if (!player.isSneaking()) {
					stopStealth();
				} else if (stealthReady && player.isSneaking()) {
					Location play = player.getEyeLocation().clone().add(player.getEyeLocation().getDirection().normalize());
					GeneralMethods.displayColoredParticle(play, "#00ee00");
				} else {
					Location play = player.getEyeLocation().clone().add(player.getEyeLocation().getDirection().normalize());
					GeneralMethods.displayColoredParticle(play, "#000000");
				}
			} else {
				if (System.currentTimeMillis() >= stealthReadyStart + stealthDuration) {
					stopStealth();
				} else {
					player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 5, 2, true, false), true);
				}
			}
		}
		
		player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 5, speedAmp, true, false), true);
		player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 5, jumpAmp, true, false), true);
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
	public String getDescription() {
		return ConfigHandler.getLang().getString("Abilities.NinjaStance.Description");
	}
	
	@Override
	public String getInstructions() {
		return "Left click to begin to this stance";
	}
	
	@Override
	public boolean isEnabled() {
		return ConfigHandler.getConfig().getBoolean("Abilities.NinjaStance.Enabled");
	}

	public void beginStealth() {
		if (stealth) {
			player.sendMessage("Already cloaked!");
			return;
		}
		stealth = true;
		stealthStart = System.currentTimeMillis();
	}
	
	public void stopStealth() {
		stealth = false;
		stealthReady = false;
		stealthStarted = false;
	}
	
	public static double getDamageModifier() {
		return ConfigHandler.getConfig().getDouble("Abilities.NinjaStance.DamageModifier");
	}
}
