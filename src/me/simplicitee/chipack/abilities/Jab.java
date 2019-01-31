package me.simplicitee.chipack.abilities;

import me.simplicitee.chipack.configuration.*;
import org.bukkit.entity.*;
import me.simplicitee.chipack.abilities.combos.*;
import com.projectkorra.projectkorra.util.*;
import com.projectkorra.projectkorra.ability.*;
import org.bukkit.*;

public class Jab extends ChiAbility implements AddonAbility
{
    private int uses;
    private long cooldown;
    private int maxUses;
    
    public Jab(final Player player, final Entity entity, final JabHand hand) {
        super(player);
        this.uses = 0;
        this.cooldown = ConfigHandler.getConfig().getLong("Abilities.Jab.Cooldown");
        this.maxUses = ConfigHandler.getConfig().getInt("Abilities.Jab.MaxUses");
        this.start();
        this.activate(entity, hand);
    }
    
    public void activate(final Entity entity, final JabHand hand) {
        if (entity instanceof LivingEntity) {
            final LivingEntity lent = (LivingEntity)entity;
            ++this.uses;
            ParticleEffect.END_ROD.display(entity.getLocation().clone().add(0.0, 1.0, 0.0), 4, 0.2f, 0.2f, 0.2f, 0.02f);
            if (hand == JabHand.LEFT) {
                final double damage = WeakeningJab.isAffected((Entity)lent) ? WeakeningJab.getModifier() : 1.0;
                DamageHandler.damageEntity(entity, this.player, damage, (Ability)this);
            }
            lent.setNoDamageTicks(0);
        }
    }
    
    public long getCooldown() {
        return this.cooldown;
    }
    
    public Location getLocation() {
        return null;
    }
    
    public String getName() {
        return "Jab";
    }
    
    public boolean isHarmlessAbility() {
        return false;
    }
    
    public boolean isSneakAbility() {
        return false;
    }
    
    public void progress() {
        if (this.uses >= this.maxUses) {
            this.remove();
            this.bPlayer.addCooldown((Ability)this);
        }
    }
    
    public String getDescription() {
        return ConfigHandler.getLang().getString("Abilities.Jab.Description");
    }
    
    public String getInstructions() {
        return "Left click or Right click";
    }
    
    public String getAuthor() {
        return "Simp";
    }
    
    public String getVersion() {
        return "ChiPack";
    }
    
    public void load() {
    }
    
    public void stop() {
    }
    
    public boolean isEnabled() {
        return ConfigHandler.getConfig().getBoolean("Abilities.Jab.Enabled");
    }
    
    public enum JabHand
    {
        RIGHT("RIGHT", 0), 
        LEFT("LEFT", 1);
        
        private JabHand(final String s, final int n) {
        }
    }
}
