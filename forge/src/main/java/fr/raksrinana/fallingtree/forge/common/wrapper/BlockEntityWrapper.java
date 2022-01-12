package fr.raksrinana.fallingtree.forge.common.wrapper;

import fr.mrcraftcod.fallingtree.common.wrapper.IBlockEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class BlockEntityWrapper implements IBlockEntity{
	@NotNull
	@Getter
	private final BlockEntity raw;
}
