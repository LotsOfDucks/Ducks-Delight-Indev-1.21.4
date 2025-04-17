package lod.ducksdelights.mixin;

import lod.ducksdelights.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ShovelItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShovelItem.class)
public class FarmlandToPaddyMixin {
    @Inject(at = @At("HEAD"), method = "useOnBlock")
    protected void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState == Blocks.FARMLAND.getDefaultState()) {
            world.setBlockState(blockPos, ModBlocks.PADDY_FARMLAND.getDefaultState());
            PlayerEntity playerEntity = context.getPlayer();
            playerEntity.swingHand(playerEntity.getActiveHand());
        }
    }
}
