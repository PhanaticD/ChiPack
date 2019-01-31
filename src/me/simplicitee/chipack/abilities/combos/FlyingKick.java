package me.simplicitee.chipack.abilities.combos;

import me.simplicitee.chipack.configuration.*;
import org.bukkit.util.*;
import org.bukkit.*;
import com.projectkorra.projectkorra.ability.*;
import com.projectkorra.projectkorra.*;
import org.bukkit.entity.*;
import com.projectkorra.projectkorra.ability.util.*;
import com.projectkorra.projectkorra.util.*;

import java.util.ArrayList;

public class FlyingKick extends ChiAbility implements ComboAbility, AddonAbility
{
    private double launch;
    private double damage;
    
    public FlyingKick(final Player player) {
        super(player);
        if (hasAbility(player, (Class)FlyingKick.class)) {
            return;
        }
        if (player.getLocation().getBlock().isLiquid()) {
            return;
        }
        if (!player.isOnGround()) {
            return;
        }
        this.launch = ConfigHandler.getConfig().getDouble("Combos.FlyingKick.LaunchPower");
        this.damage = ConfigHandler.getConfig().getDouble("Combos.FlyingKick.Damage");
        final Vector v = player.getLocation().getDirection().add(new Vector(0.0, 0.25485, 0.0)).normalize().multiply(this.launch);
        player.setVelocity(v);
        this.start();
    }
    
    public long getCooldown() {
        return ConfigHandler.getConfig().getLong("Combos.FlyingKick.Cooldown");
    }
    
    public Location getLocation() {
        return this.player.getLocation();
    }
    
    public String getName() {
        return "FlyingKick";
    }
    
    public boolean isHarmlessAbility() {
        return false;
    }
    
    public boolean isSneakAbility() {
        return false;
    }
    
    public void progress() {
        if (this.player == null) {
            this.remove();
            return;
        }
        if (!this.player.isOnline() || this.player.isDead()) {
            this.remove();
            return;
        }
        if (this.player.getLocation().subtract(0.0, 0.1, 0.0).getBlock().getType() != Material.AIR) {
            this.remove();
            this.bPlayer.addCooldown((Ability)this);
            return;
        }
        ParticleEffect.CRIT_MAGIC.display(this.player.getLocation(), 3, 0.2f, 0.2f, 0.2f, 0.02f);
        for (final Entity entity : GeneralMethods.getEntitiesAroundPoint(this.player.getLocation(), 2.0)) {
            if (entity instanceof LivingEntity && entity.getEntityId() != this.player.getEntityId()) {
                DamageHandler.damageEntity(entity, this.player, this.damage, (Ability)this);
            }
        }
    }
    
    public Object createNewComboInstance(final Player player) {
        return new FlyingKick(player);
    }
    
    public ArrayList<ComboManager.AbilityInformation> getCombination() {
        final ArrayList<ComboManager.AbilityInformation> combo = new ArrayList<ComboManager.AbilityInformation>();
        combo.add(new ComboManager.AbilityInformation("SwiftKick", ClickType.SHIFT_DOWN));
        combo.add(new ComboManager.AbilityInformation("SwiftKick", ClickType.LEFT_CLICK));
        return combo;
    }
    
    public String getDescription() {
        return ConfigHandler.getLang().getString("Combos.FlyingKick.Description");
    }
    
    public String getInstructions() {
        return "SwiftKick (Hold sneak) > SwiftKick (Left Click)";
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
        return ConfigHandler.getConfig().getBoolean("Combos.FlyingKick.Enabled");
    }
}
