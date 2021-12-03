package fr.raksrinana.fallingtree.forge.tree.breaking;

import fr.raksrinana.fallingtree.forge.config.Config;
import fr.raksrinana.fallingtree.forge.config.MaxSizeAction;
import lombok.Getter;
import net.minecraft.world.item.ItemStack;

public class ToolDamageHandler{
	private final ItemStack tool;
	private final double damageMultiplicand;
	private final int maxDurabilityTaken;
	@Getter
	private final int maxBreakCount;
	
	public ToolDamageHandler(ItemStack tool, double damageMultiplicand, boolean preserve, int breakableCount) throws BreakTreeTooBigException{
		this.tool = tool;
		this.damageMultiplicand = damageMultiplicand;
		
		if(breakableCount > Config.COMMON.getTrees().getMaxSize() && Config.COMMON.getTrees().getMaxSizeAction() == MaxSizeAction.ABORT){
			throw new BreakTreeTooBigException();
		}
		
		int tempMaxBreakCount;
		if(tool.isDamageableItem()){
			var breakCount = damageMultiplicand == 0 ? Config.COMMON.getTrees().getMaxSize() : (int) Math.floor(getToolDurability() / damageMultiplicand);
			if(preserve && breakCount <= breakableCount){
				breakCount--;
			}
			
			tempMaxBreakCount = breakCount;
		}
		else{
			tempMaxBreakCount = Config.COMMON.getTrees().getMaxSize();
		}
		
		maxBreakCount = Math.min(Config.COMMON.getTrees().getMaxSize(), tempMaxBreakCount);
		maxDurabilityTaken = getDamage(maxBreakCount);
	}
	
	private int getDamage(long count){
		if(Double.compare(damageMultiplicand, 0) <= 0){
			return 1;
		}
		var rawDamage = count * damageMultiplicand;
		
		return (int) switch(Config.COMMON.getTools().getDamageRounding()){
			case ROUND_DOWN -> Math.floor(rawDamage);
			case ROUND_UP -> Math.ceil(rawDamage);
			case ROUNDING -> Math.round(rawDamage);
			case PROBABILISTIC -> getProbabilisticDamage(rawDamage);
		};
	}
	
	private int getProbabilisticDamage(double rawDamage){
		var damage = Math.floor(rawDamage);
		var finalDamage = (int) damage;
		var probability = rawDamage - damage;
		if(Math.random() < probability){
			finalDamage++;
		}
		return finalDamage;
	}
	
	public int getActualDamage(int brokenCount){
		if(tool.isDamageableItem()){
			return brokenCount == maxBreakCount ? maxDurabilityTaken : Math.min(maxDurabilityTaken, getDamage(brokenCount));
		}
		return 0;
	}
	
	private int getToolDurability(){
		if(tool.isDamageableItem()){
			return tool.getMaxDamage() - tool.getDamageValue();
		}
		return Integer.MAX_VALUE;
	}
}
