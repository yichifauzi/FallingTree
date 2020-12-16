package fr.raksrinana.fallingtree;

import fr.raksrinana.fallingtree.config.Config;
import me.shedaniel.clothconfig2.forge.api.ConfigBuilder;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import org.apache.commons.lang3.tuple.Pair;

@Mod(FallingTree.MOD_ID)
public class FallingTree{
	public static final String MOD_ID = "falling_tree";
	
	public FallingTree(){
		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC);
		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY, () -> (mc, parent) -> {
			ConfigBuilder builder = ConfigBuilder.create()
					.setParentScreen(parent)
					.setTitle(new StringTextComponent("FallingTree"));
			
			Config.COMMON.fillConfigScreen(builder);
			
			return builder.build();
		});
	}
}
