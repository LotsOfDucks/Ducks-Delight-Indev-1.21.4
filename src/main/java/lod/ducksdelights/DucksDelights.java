package lod.ducksdelights;

import lod.ducksdelights.block.ModBlocks;
import lod.ducksdelights.entity.ModBlockEntityTypes;
import lod.ducksdelights.item.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DucksDelights implements ModInitializer {
	public static final String MOD_ID = "ducksdelights";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.initialize();
		ModBlocks.initialize();
		ModBlockEntityTypes.initialize();
	}
}