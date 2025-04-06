package lod.ducksdelights.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.enums.SculkSensorPhase;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
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
    public static final EnumProperty<SculkSensorPhase> SCULK_SENSOR_PHASE;
    private static final VoxelShape SCULK_SPEAKER_SHAPE;
    private static final VoxelShape SCULK_SPEAKER_BASE_SHAPE;
    private static final VoxelShape SCULK_SPEAKER_TOP_SHAPE;

    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SCULK_SPEAKER_SHAPE;
    }

    protected SculkSpeakerBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false).with(POWERED, false));
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
        return this.getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    protected FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public static SculkSensorPhase getPhase(BlockState state) {
        return state.get(SCULK_SENSOR_PHASE);
    }

    public static void setCooldown(World world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state.with(SCULK_SENSOR_PHASE, SculkSensorPhase.COOLDOWN));
        world.scheduleBlockTick(pos, state.getBlock(), 10);
    }

    public int getCooldownTime() {
        return 20;
    }

    public void setActive(@Nullable Entity sourceEntity, World world, BlockPos pos, BlockState state, int power, int frequency) {
        world.setBlockState(pos, state.with(SCULK_SENSOR_PHASE, SculkSensorPhase.ACTIVE));
        world.scheduleBlockTick(pos, state.getBlock(), this.getCooldownTime());
        world.emitGameEvent(sourceEntity, GameEvent.SCULK_SENSOR_TENDRILS_CLICKING, pos);
        if (!(Boolean)state.get(WATERLOGGED)) {
            world.playSound(null, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.BLOCK_SCULK_SENSOR_CLICKING, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.2F + 0.8F);
        }

    }

    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (getPhase(state) != SculkSensorPhase.ACTIVE) {
            if (getPhase(state) == SculkSensorPhase.COOLDOWN) {
                world.setBlockState(pos, state.with(SCULK_SENSOR_PHASE, SculkSensorPhase.INACTIVE), 3);
                if (!(Boolean)state.get(WATERLOGGED)) {
                    world.playSound(null, pos, SoundEvents.BLOCK_SCULK_SENSOR_CLICKING_STOP, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.2F + 0.8F);
                }
            }

        } else {
            setCooldown(world, pos, state);
        }
    }

    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {
        boolean bl = world.isReceivingRedstonePower(pos);
        if (bl != state.get(POWERED)) {
            if (bl) {
                this.emitFrequency(null, world, pos);
            }
            world.setBlockState(pos, state.with(POWERED, bl), 3);
            world.setBlockState(pos, state.with(SCULK_SENSOR_PHASE, SculkSensorPhase.ACTIVE));
            world.scheduleBlockTick(pos, state.getBlock(), this.getCooldownTime());
            if (!(Boolean)state.get(WATERLOGGED)) {
                world.playSound(null, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.BLOCK_SCULK_SENSOR_CLICKING, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.2F + 0.8F);
            }
        }

    }

    private int getEmitFrequency(RedstoneView world, BlockPos pos) {
        Direction direction = Direction.NORTH;
        Direction direction2 = direction.rotateYClockwise();
        Direction direction3 = direction.rotateYCounterclockwise();
        Direction direction4 = direction.getOpposite();
        int a = Math.max(world.getEmittedRedstonePower(pos.offset(direction), direction), world.getEmittedRedstonePower(pos.offset(direction2), direction2));
        int b = Math.max(world.getEmittedRedstonePower(pos.offset(direction3), direction3), world.getEmittedRedstonePower(pos.offset(direction4), direction4));
        return Math.max(a, b);
    }

    private void emitFrequency(@Nullable Entity entity, World world, BlockPos pos) {
        int i = this.getEmitFrequency(world, pos);
        switch(i){
            case 1:
                world.emitGameEvent(entity, GameEvent.RESONATE_1, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)i / 24.0, 0.0, 0.0);
                break;
            case 2:
                world.emitGameEvent(entity, GameEvent.RESONATE_2, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)i / 24.0, 0.0, 0.0);
                break;
            case 3:
                world.emitGameEvent(entity, GameEvent.RESONATE_3, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)i / 24.0, 0.0, 0.0);
                break;
            case 4:
                world.emitGameEvent(entity, GameEvent.RESONATE_4, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)i / 24.0, 0.0, 0.0);
                break;
            case 5:
                world.emitGameEvent(entity, GameEvent.RESONATE_5, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)i / 24.0, 0.0, 0.0);
                break;
            case 6:
                world.emitGameEvent(entity, GameEvent.RESONATE_6, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)i / 24.0, 0.0, 0.0);
                break;
            case 7:
                world.emitGameEvent(entity, GameEvent.RESONATE_7, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)i / 24.0, 0.0, 0.0);
                break;
            case 8:
                world.emitGameEvent(entity, GameEvent.RESONATE_8, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)i / 24.0, 0.0, 0.0);
                break;
            case 9:
                world.emitGameEvent(entity, GameEvent.RESONATE_9, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)i / 24.0, 0.0, 0.0);
                break;
            case 10:
                world.emitGameEvent(entity, GameEvent.RESONATE_10, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)i / 24.0, 0.0, 0.0);
                break;
            case 11:
                world.emitGameEvent(entity, GameEvent.RESONATE_11, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)i / 24.0, 0.0, 0.0);
                break;
            case 12:
                world.emitGameEvent(entity, GameEvent.RESONATE_12, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)i / 24.0, 0.0, 0.0);
                break;
            case 13:
                world.emitGameEvent(entity, GameEvent.RESONATE_13, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)i / 24.0, 0.0, 0.0);
                break;
            case 14:
                world.emitGameEvent(entity, GameEvent.RESONATE_14, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)i / 24.0, 0.0, 0.0);
                break;
            case 15:
                world.emitGameEvent(entity, GameEvent.RESONATE_15, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)i / 24.0, 0.0, 0.0);
                break;
        }
    }

    @Override
    protected MapCodec<? extends Block> getCodec() { return CODEC; }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SCULK_SENSOR_PHASE, POWERED, WATERLOGGED);
    }

    static {
        SCULK_SENSOR_PHASE = Properties.SCULK_SENSOR_PHASE;
        POWERED = Properties.POWERED;
        SCULK_SPEAKER_BASE_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
        SCULK_SPEAKER_TOP_SHAPE = Block.createCuboidShape(2.0, 8.0, 2.0, 14.0, 16.0, 14.0);
        SCULK_SPEAKER_SHAPE = VoxelShapes.union(SCULK_SPEAKER_BASE_SHAPE, SCULK_SPEAKER_TOP_SHAPE);
    }
}
