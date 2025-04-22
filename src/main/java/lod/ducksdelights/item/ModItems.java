package lod.ducksdelights.item;

import lod.ducksdelights.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.FoodComponents;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public final class ModItems {
    private ModItems() {
    }

    private static Function<Item.Settings, Item> createBlockItemWithUniqueName(Block block) {
        return (settings) -> new BlockItem(block, settings.useItemPrefixedTranslationKey());
    }

    public static final Item RAW_RICE = register("raw_rice", createBlockItemWithUniqueName(ModBlocks.RICE_CROP), new Item.Settings());
    public static final Item RAW_GOLDEN_RICE = register("raw_golden_rice", createBlockItemWithUniqueName(ModBlocks.GOLDEN_RICE_CROP), new Item.Settings());
    public static final Item WHITE_RICE = register("white_rice", Item::new, new Item.Settings().recipeRemainder(Items.BOWL).food(ModFoodComponents.WHITE_RICE, ModConsumableComponents.WHITE_RICE).useRemainder(Items.BOWL));
    public static final Item GOLDEN_RICE = register("golden_rice", Item::new, new Item.Settings().recipeRemainder(Items.BOWL).food(ModFoodComponents.GOLDEN_RICE, ModConsumableComponents.GOLDEN_RICE).useRemainder(Items.BOWL));
    public static final Item ONIGIRI = register("onigiri", Item::new, new Item.Settings().food(ModFoodComponents.ONIGIRI));
    public static final Item CHICKEN_ONIGIRI = register("chicken_onigiri", Item::new, new Item.Settings().food(ModFoodComponents.CHICKEN_ONIGIRI));
    public static final Item BEEF_ONIGIRI = register("beef_onigiri", Item::new, new Item.Settings().food(ModFoodComponents.BEEF_ONIGIRI));
    public static final Item PORK_ONIGIRI = register("pork_onigiri", Item::new, new Item.Settings().food(ModFoodComponents.PORK_ONIGIRI));
    public static final Item SALMON_ONIGIRI = register("salmon_onigiri", Item::new, new Item.Settings().food(ModFoodComponents.SALMON_ONIGIRI));
    public static final Item GUILDED_ONIGIRI = register("guilded_onigiri", Item::new, new Item.Settings().food(ModFoodComponents.GUILDED_ONIGIRI, ModConsumableComponents.GUILDED_ONIGIRI));

    //public static final Item CUSTOM_ITEM = register("custom_item", Item::new, new Item.Settings());

    public static Item register(String path, Function<Item.Settings, Item> factory, Item.Settings settings) {
        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of("ducksdelights", path));
        return Items.register(registryKey, factory, settings);
    }

    public static void initialize() {
    }
}
