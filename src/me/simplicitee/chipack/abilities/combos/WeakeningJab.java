package me.simplicitee.chipack.abilities.combos;

import com.projectkorra.projectkorra.*;
import me.simplicitee.chipack.configuration.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import com.projectkorra.projectkorra.ability.*;
import java.util.*;
import com.projectkorra.projectkorra.ability.util.*;
import com.projectkorra.projectkorra.util.*;

public class WeakeningJab extends ChiAbility implements ComboAbility, AddonAbility
{
    private static Set<Integer> entities;
    public LivingEntity entity;
    public long duration;
    
    static {
        WeakeningJab.entities = new HashSet<Integer>();
    }
    
    public WeakeningJab(final Player player) {
        super(player);
        this.entity = null;
        final Entity e = GeneralMethods.getTargetedEntity(player, 4.0);
        if (e instanceof LivingEntity) {
            this.entity = (LivingEntity)e;
            this.duration = ConfigHandler.getConfig().getLong("Combos.WeakeningJab.Duration");
            if (this.entity != null && !WeakeningJab.entities.contains(this.entity.getEntityId())) {
                WeakeningJab.entities.add(this.entity.getEntityId());
                this.start();
            }
        }
    }
    
    public long getCooldown() {
        return ConfigHandler.getConfig().getLong("Combos.WeakeningJab.Cooldown");
    }
    
    public Location getLocation() {
        return this.entity.getLocation().clone().add(0.0, 1.0, 0.0);
    }
    
    public String getName() {
        return "WeakeningJab";
    }
    
    public boolean isHarmlessAbility() {
        return false;
    }
    
    public boolean isSneakAbility() {
        return false;
    }
    
    public void progress() {
        ParticleEffect.DAMAGE_INDICATOR.display(this.entity.getLocation(), 3, 0.2f, 1.0f, 0.2f, 4.0E-4f);
        if (System.currentTimeMillis() >= this.getStartTime() + this.duration) {
            WeakeningJab.entities.remove(this.entity.getEntityId());
            this.remove();
            this.bPlayer.addCooldown((Ability)this);
        }
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
    
    public Object createNewComboInstance(final Player player) {
        return new WeakeningJab(player);
    }
    
    public ArrayList<ComboManager.AbilityInformation> getCombination() {
        final ArrayList<ComboManager.AbilityInformation> combo = new ArrayList<ComboManager.AbilityInformation>();
        combo.add(new ComboManager.AbilityInformation("Jab", ClickType.LEFT_CLICK_ENTITY));
        combo.add(new ComboManager.AbilityInformation("Jab", ClickType.LEFT_CLICK_ENTITY));
        combo.add(new ComboManager.AbilityInformation("Jab", ClickType.RIGHT_CLICK_ENTITY));
        return combo;
    }
    
    public String getDescription() {
        return ConfigHandler.getLang().getString("Combos.WeakeningJab.Description");
    }
    
    public String getInstructions() {
        return "Jab (Left) > Jab (Left) > Jab (Right)";
    }
    
    public boolean isEnabled() {
        return ConfigHandler.getConfig().getBoolean("Combos.WeakeningJab.Enabled");
    }
    
    public static boolean isAffected(final Entity e) {
        return WeakeningJab.entities.contains(e.getEntityId());
    }
    
    public static double getModifier() {
        return ConfigHandler.getConfig().getDouble("Combos.WeakeningJab.Modifier");
    }
}
