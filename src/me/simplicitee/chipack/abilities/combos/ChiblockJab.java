package me.simplicitee.chipack.abilities.combos;

import com.projectkorra.projectkorra.*;
import me.simplicitee.chipack.configuration.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import com.projectkorra.projectkorra.ability.*;
import java.util.*;
import com.projectkorra.projectkorra.ability.util.*;
import com.projectkorra.projectkorra.util.*;

public class ChiblockJab extends ChiAbility implements ComboAbility, AddonAbility
{
    private Player attacked;
    private long duration;
    private BendingPlayer bp;
    
    public ChiblockJab(final Player player) {
        super(player);
        final Entity entity = GeneralMethods.getTargetedEntity(player, 4.0);
        if (entity instanceof Player) {
            this.attacked = (Player)entity;
            this.duration = ConfigHandler.getConfig().getLong("Combos.ChiblockJab.Duration");
            if (this.attacked != null) {
                this.bp = BendingPlayer.getBendingPlayer(this.attacked);
                if (this.bp != null) {
                    this.bp.blockChi();
                    this.start();
                }
            }
        }
    }
    
    public long getCooldown() {
        return ConfigHandler.getConfig().getLong("Combos.ChiblockJab.Cooldown");
    }
    
    public Location getLocation() {
        return this.attacked.getLocation().clone().add(0.0, 1.0, 0.0);
    }
    
    public String getName() {
        return "ChiblockJab";
    }
    
    public boolean isHarmlessAbility() {
        return false;
    }
    
    public boolean isSneakAbility() {
        return false;
    }
    
    public void progress() {
        ParticleEffect.CRIT.display(this.attacked.getLocation().clone().add(0.0, 1.0, 0.0), 3, 0.2f, 1.0f, 0.2f, 0.04f);
        if (System.currentTimeMillis() >= this.getStartTime() + this.duration) {
            this.remove();
            this.bPlayer.addCooldown((Ability)this);
            this.bp.unblockChi();
        }
    }
    
    public Object createNewComboInstance(final Player player) {
        return new ChiblockJab(player);
    }
    
    public ArrayList<ComboManager.AbilityInformation> getCombination() {
        final ArrayList<ComboManager.AbilityInformation> combo = new ArrayList<ComboManager.AbilityInformation>();
        combo.add(new ComboManager.AbilityInformation("Jab", ClickType.RIGHT_CLICK_ENTITY));
        combo.add(new ComboManager.AbilityInformation("Jab", ClickType.LEFT_CLICK_ENTITY));
        combo.add(new ComboManager.AbilityInformation("Jab", ClickType.RIGHT_CLICK_ENTITY));
        return combo;
    }
    
    public String getDescription() {
        return ConfigHandler.getLang().getString("Combos.ChiblockJab.Description");
    }
    
    public String getInstructions() {
        return "Jab (Right) > Jab (Left) > Jab (Right)";
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
        return ConfigHandler.getConfig().getBoolean("Combos.ChiblockJab.Enabled");
    }
}
