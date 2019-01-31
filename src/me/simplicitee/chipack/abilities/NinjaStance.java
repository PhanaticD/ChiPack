package me.simplicitee.chipack.abilities;

import com.projectkorra.projectkorra.ability.*;
import org.bukkit.entity.*;
import me.simplicitee.chipack.configuration.*;
import com.projectkorra.projectkorra.*;
import org.bukkit.*;
import org.bukkit.potion.*;

public class NinjaStance extends ChiAbility implements AddonAbility
{
    public boolean stealth;
    public boolean stealthReady;
    public boolean stealthStarted;
    public long stealthStart;
    public long stealthChargeTime;
    public long stealthReadyStart;
    public long stealthDuration;
    public int speedAmp;
    public int jumpAmp;
    public long duration;
    
    public NinjaStance(final Player player) {
        super(player);
        final ChiAbility stance = this.bPlayer.getStance();
        if (stance != null) {
            stance.remove();
            if (stance instanceof NinjaStance) {
                this.bPlayer.setStance((ChiAbility)null);
                return;
            }
        }
        this.duration = ConfigHandler.getConfig().getLong("Abilities.NinjaStance.Duration");
        this.stealthDuration = ConfigHandler.getConfig().getLong("Abilities.NinjaStance.Stealth.Duration");
        this.stealthChargeTime = ConfigHandler.getConfig().getLong("Abilities.NinjaStance.Stealth.ChargeTime");
        this.speedAmp = ConfigHandler.getConfig().getInt("Abilities.NinjaStance.SpeedAmplifier") - 1;
        this.jumpAmp = ConfigHandler.getConfig().getInt("Abilities.NinjaStance.JumpAmplifier") - 1;
        this.start();
        this.bPlayer.setStance((ChiAbility)this);
        GeneralMethods.displayMovePreview(player);
        player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_HURT, 0.5f, 2.0f);
    }
    
    public long getCooldown() {
        return ConfigHandler.getConfig().getLong("Abilities.NinjaStance.Cooldown");
    }
    
    public Location getLocation() {
        return this.player.getLocation();
    }
    
    public String getName() {
        return "NinjaStance";
    }
    
    public boolean isHarmlessAbility() {
        return true;
    }
    
    public boolean isSneakAbility() {
        return true;
    }
    
    public void progress() {
        if (!this.player.isOnline() || this.player.isDead()) {
            this.remove();
            return;
        } else if (this.duration != 0 && System.currentTimeMillis() > this.getStartTime() + this.duration) {
            remove();
            return;
        }
        if (this.stealth) {
            if (System.currentTimeMillis() >= this.stealthStart + this.stealthChargeTime) {
                this.stealthReady = true;
            }
            if (!this.stealthStarted) {
                if (this.stealthReady && !this.player.isSneaking()) {
                    this.stealthReadyStart = System.currentTimeMillis();
                    this.stealthStarted = true;
                }
                else if (!this.player.isSneaking()) {
                    this.stopStealth();
                }
                else if (this.stealthReady && this.player.isSneaking()) {
                    final Location play = this.player.getEyeLocation().clone().add(this.player.getEyeLocation().getDirection().normalize());
                    GeneralMethods.displayColoredParticle("#00ee00", play);
                }
                else {
                    final Location play = this.player.getEyeLocation().clone().add(this.player.getEyeLocation().getDirection().normalize());
                    GeneralMethods.displayColoredParticle("#000000", play);
                }
            }
            else if (System.currentTimeMillis() >= this.stealthReadyStart + this.stealthDuration) {
                this.stopStealth();
            }
            else {
                this.player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 5, 2, true, false), true);
            }
        }
        // Jump Buff.
        if (!this.player.hasPotionEffect(PotionEffectType.JUMP) || this.player.getPotionEffect(PotionEffectType.JUMP).getAmplifier() < this.jumpAmp || (this.player.getPotionEffect(PotionEffectType.JUMP).getAmplifier() <= this.jumpAmp && this.player.getPotionEffect(PotionEffectType.JUMP).getDuration() == 1)) {
            this.player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 10, this.jumpAmp, true, false), true);
        }
        // Speed Buff.
        if (!this.player.hasPotionEffect(PotionEffectType.SPEED) || this.player.getPotionEffect(PotionEffectType.SPEED).getAmplifier() < this.speedAmp || (this.player.getPotionEffect(PotionEffectType.SPEED).getAmplifier() <= this.speedAmp && this.player.getPotionEffect(PotionEffectType.SPEED).getDuration() == 1)) {
            this.player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10, this.speedAmp, true, false), true);
        }
    }

    @Override
    public void remove() {
        super.remove();
        this.bPlayer.addCooldown(this);
        this.bPlayer.setStance(null);
        GeneralMethods.displayMovePreview(this.player);
        this.player.playSound(this.player.getLocation(), Sound.ENTITY_ENDER_DRAGON_SHOOT, 0.5F, 2F);
        this.player.removePotionEffect(PotionEffectType.SPEED);
        this.player.removePotionEffect(PotionEffectType.JUMP);
    }
    
    public String getAuthor() {
        return "Simp";
    }
    
    public String getVersion() {
        return "ChiPack";
    }

    public long getDuration() {
        return this.duration;
    }

    public void setDuration(final long duration) {
        this.duration = duration;
    }
    
    public void load() {
    }
    
    public void stop() {
    }
    
    public String getDescription() {
        return ConfigHandler.getLang().getString("Abilities.NinjaStance.Description");
    }
    
    public String getInstructions() {
        return "Left click to begin to this stance > Hold sneak to begin stealth mode";
    }
    
    public boolean isEnabled() {
        return ConfigHandler.getConfig().getBoolean("Abilities.NinjaStance.Enabled");
    }
    
    public void beginStealth() {
        if (this.stealth) {
            this.player.sendMessage("Already cloaked!");
            return;
        }
        this.stealth = true;
        this.stealthStart = System.currentTimeMillis();
    }
    
    public void stopStealth() {
        this.stealth = false;
        this.stealthReady = false;
        this.stealthStarted = false;
    }
    
    public static double getDamageModifier() {
        return ConfigHandler.getConfig().getDouble("Abilities.NinjaStance.DamageModifier");
    }
}
