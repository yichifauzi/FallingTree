package fr.raksrinana.fallingtree.utils;

import net.minecraft.util.math.BlockPos;
import java.util.Objects;
import static java.lang.System.currentTimeMillis;

public class CacheSpeed{
	private final BlockPos pos;
	private final float speed;
	private final long millis;
	
	public CacheSpeed(BlockPos pos, float speed){
		this.pos = pos;
		this.speed = speed;
		this.millis = currentTimeMillis();
	}
	
	public boolean isValid(BlockPos blockPos){
		return millis + 1000 >= currentTimeMillis() && Objects.equals(pos, blockPos);
	}
	
	public float getSpeed(){
		return speed;
	}
}
