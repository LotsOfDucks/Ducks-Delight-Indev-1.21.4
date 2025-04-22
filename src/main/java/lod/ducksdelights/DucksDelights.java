package lod.ducksdelights;

import lod.ducksdelights.block.ModBlocks;
import lod.ducksdelights.entity.ModBlockEntityTypes;
import lod.ducksdelights.item.ModConsumableComponents;
import lod.ducksdelights.item.ModFoodComponents;
import lod.ducksdelights.item.ModItemGroups;
import lod.ducksdelights.item.ModItems;
import lod.ducksdelights.sound.ModSounds;
import net.fabricmc.api.ModInitializer;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DucksDelights implements ModInitializer {
	public static final String MOD_ID = "ducksdelights";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.initialize();
		ModItems.initialize();
		ModBlocks.initialize();
		ModBlockEntityTypes.initialize();
		ModSounds.initialize();
		ModFoodComponents.initialize();
		ModConsumableComponents.initialize();
	}
}