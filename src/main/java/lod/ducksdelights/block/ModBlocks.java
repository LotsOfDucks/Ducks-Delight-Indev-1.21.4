package lod.ducksdelights.block;

import lod.ducksdelights.DucksDelights;
import lod.ducksdelights.block.custom.SculkSpeakerBlock;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.SculkSensorPhase;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block SCULK_SPEAKER = registerBlock("sculk_speaker",
            new Block(AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.SCULK)
                    .mapColor(MapColor.CYAN)
                    .requiresTool()
                    .luminance((state) -> 1)
                    .emissiveLighting((state, world, pos) -> SculkSpeakerBlock.getPhase(state) == SculkSensorPhase.ACTIVE)
            ));



    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(DucksDelights.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(DucksDelights.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks() {
        DucksDelights.LOGGER.info("Registering" + DucksDelights.MOD_ID + "Blocks");
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(entries -> {
            entries.add(ModBlocks.SCULK_SPEAKER);
        });
    }
}
