package lod.ducksdelights.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.RedstoneView;
import net.minecraft.world.World;
import net.minecraft.world.block.WireOrientation;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class SculkSpeakerBlock extends Block implements Waterloggable {
    public static final MapCodec<SculkSpeakerBlock> CODEC = createCodec(SculkSpeakerBlock::new);
    public static final BooleanProperty POWERED;
    public static BooleanProperty WATERLOGGED;
    protected static final VoxelShape UP_SHAPE;
    protected static final VoxelShape LOW_SHAPE;
    protected static final VoxelShape SHAPE;

    public SculkSpeakerBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false).with(POWERED, false));
    }

    @Override
    public MapCodec<? extends SculkSpeakerBlock> getCodec() {
        return CODEC;
    }

    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    protected VoxelShape getCullingShape(BlockState state) {
        return SHAPE;
    }

    protected boolean hasSidedTransparency(BlockState state) {
        return true;
    }


    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
        builder.add(WATERLOGGED);
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
        return this.getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    protected FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {
        boolean bl = world.isReceivingRedstonePower(pos);
        if (bl != state.get(POWERED)) {
            if (bl) {
                this.emitFrequency(world, pos) ;
            }
            world.setBlockState(pos, state.with(POWERED, bl), 3);
            if (!(Boolean)state.get(WATERLOGGED)) {
                world.playSound(null, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.BLOCK_SCULK_SENSOR_CLICKING, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.2F + 0.8F);
            }
        }

    }

    private void emitFrequency(World world, BlockPos pos) {
        int a = Math.max(world.getEmittedRedstonePower(pos.north(1), Direction.SOUTH), world.getEmittedRedstonePower(pos.south(1), Direction.NORTH));
        int b = Math.max(world.getEmittedRedstonePower(pos.east(1), Direction.WEST), world.getEmittedRedstonePower(pos.west(1), Direction.EAST));
        int c = Math.max(a, b);
        switch(c){
            case 0:
                break;
            case 1:
                world.emitGameEvent(null, GameEvent.RESONATE_1, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)c / 24.0, 0.0, 0.0);
                break;
            case 2:
                world.emitGameEvent(null, GameEvent.RESONATE_2, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)c / 24.0, 0.0, 0.0);
                break;
            case 3:
                world.emitGameEvent(null, GameEvent.RESONATE_3, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)c / 24.0, 0.0, 0.0);
                break;
            case 4:
                world.emitGameEvent(null, GameEvent.RESONATE_4, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)c / 24.0, 0.0, 0.0);
                break;
            case 5:
                world.emitGameEvent(null, GameEvent.RESONATE_5, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)c / 24.0, 0.0, 0.0);
                break;
            case 6:
                world.emitGameEvent(null, GameEvent.RESONATE_6, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)c / 24.0, 0.0, 0.0);
                break;
            case 7:
                world.emitGameEvent(null, GameEvent.RESONATE_7, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)c / 24.0, 0.0, 0.0);
                break;
            case 8:
                world.emitGameEvent(null, GameEvent.RESONATE_8, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)c / 24.0, 0.0, 0.0);
                break;
            case 9:
                world.emitGameEvent(null, GameEvent.RESONATE_9, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)c / 24.0, 0.0, 0.0);
                break;
            case 10:
                world.emitGameEvent(null, GameEvent.RESONATE_10, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)c / 24.0, 0.0, 0.0);
                break;
            case 11:
                world.emitGameEvent(null, GameEvent.RESONATE_11, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)c / 24.0, 0.0, 0.0);
                break;
            case 12:
                world.emitGameEvent(null, GameEvent.RESONATE_12, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)c / 24.0, 0.0, 0.0);
                break;
            case 13:
                world.emitGameEvent(null, GameEvent.RESONATE_13, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)c / 24.0, 0.0, 0.0);
                break;
            case 14:
                world.emitGameEvent(null, GameEvent.RESONATE_14, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)c / 24.0, 0.0, 0.0);
                break;
            case 15:
                world.emitGameEvent(null, GameEvent.RESONATE_15, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)c / 24.0, 0.0, 0.0);
        }
    }

    static {
        POWERED = Properties.POWERED;
        UP_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
        LOW_SHAPE = Block.createCuboidShape(2.0, 8.0, 2.0, 14.0, 12.0, 14.0);
        SHAPE = VoxelShapes.union(UP_SHAPE, LOW_SHAPE);
    }
}
