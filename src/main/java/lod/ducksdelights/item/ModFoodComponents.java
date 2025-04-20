package lod.ducksdelights.item;

import net.minecraft.component.type.FoodComponent;

public class ModFoodComponents {

    public static final FoodComponent WHITE_RICE = (new FoodComponent.Builder()).nutrition(1).saturationModifier(0.1F).build();
    public static final FoodComponent GOLDEN_RICE = (new FoodComponent.Builder()).nutrition(2).saturationModifier(1.2F).build();

    private static FoodComponent.Builder createStew(int nutrition) {
        return (new FoodComponent.Builder()).nutrition(nutrition).saturationModifier(0.6F);
    }

    public static void initialize() {
    }
}
