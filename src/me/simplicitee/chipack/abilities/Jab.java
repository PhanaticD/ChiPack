package me.simplicitee.chipack.abilities;

import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.ChiAbility;
import com.projectkorra.projectkorra.util.DamageHandler;

import me.simplicitee.chipack.configuration.ConfigHandler;

public class Jab extends ChiAbility implements AddonAbility{
	
	public static enum JabHand {
		RIGHT, LEFT;
	}
	
	public static ConcurrentHashMap<Player, Integer> uses = new ConcurrentHashMap<>();
	
	private Entity entity;
	private JabHand hand;
	
	private long cooldown = ConfigHandler.getConfig().getLong("Abilities.Jab.Cooldown");
	private int maxUses = ConfigHandler.getConfig().getInt("Abilities.Jab.MaxUses");

	public Jab(Player player, Entity entity, JabHand hand) {
		super(player);
		this.hand = hand;
		this.entity = entity;
		if (!uses.containsKey(player)) {
			uses.put(player, 0);
		}
		start();
	}

	@Override
	public long getCooldown() {
		// TODO Auto-generated method stub
		return cooldown;
	}

	@Override
	public Location getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Jab";
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
		uses.put(player, uses.get(player) + 1);
		if (uses.get(player) == maxUses) {
			uses.put(player, 0);
			bPlayer.addCooldown(this);
		}
		
		if (hand == JabHand.LEFT) {
			DamageHandler.damageEntity(entity, player, 1, this);
		}
		
		remove();
	}

	@Override
	public String getAuthor() {
		// TODO Auto-generated method stub
		return "Simplicitee";
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return "1.0 (SimpHub)";
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	
}
