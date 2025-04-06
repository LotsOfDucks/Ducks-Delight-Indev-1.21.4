package lod.ducksdelights.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.block.WireOrientation;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class SculkSpeakerBlock extends Block {
    public static final MapCodec<SculkSpeakerBlock> CODEC = createCodec(SculkSpeakerBlock::new);
    public static final BooleanProperty POWERED;
    public static final IntProperty TUNE;
    protected static final VoxelShape UP_SHAPE;
    protected static final VoxelShape LOW_SHAPE;
    protected static final VoxelShape SHAPE;

    public MapCodec<SculkSpeakerBlock> getCodec() {
        return CODEC;
    }

    public SculkSpeakerBlock(Settings settings) {
        super(settings);
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

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState();
    }

    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {
        boolean bl = world.isReceivingRedstonePower(pos);
        if (bl != state.get(POWERED)) {
            if (bl) {
                this.playResonance(null, state, world, pos);
            }

            world.setBlockState(pos, state.with(POWERED, bl));
        }
    }

    private void playResonance(@Nullable Entity entity, BlockState state, World world, BlockPos pos) {
        world.addSyncedBlockEvent(pos, this, 0, 0);
        int c = state.get(TUNE);
        switch(c){
            case 1:
                world.emitGameEvent(entity, GameEvent.RESONATE_1, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)c / 24.0, 0.0, 0.0);
                break;
            case 2:
                world.emitGameEvent(entity, GameEvent.RESONATE_2, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)c / 24.0, 0.0, 0.0);
                break;
            case 3:
                world.emitGameEvent(entity, GameEvent.RESONATE_3, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)c / 24.0, 0.0, 0.0);
                break;
            case 4:
                world.emitGameEvent(entity, GameEvent.RESONATE_4, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)c / 24.0, 0.0, 0.0);
                break;
            case 5:
                world.emitGameEvent(entity, GameEvent.RESONATE_5, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)c / 24.0, 0.0, 0.0);
                break;
            case 6:
                world.emitGameEvent(entity, GameEvent.RESONATE_6, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)c / 24.0, 0.0, 0.0);
                break;
            case 7:
                world.emitGameEvent(entity, GameEvent.RESONATE_7, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)c / 24.0, 0.0, 0.0);
                break;
            case 8:
                world.emitGameEvent(entity, GameEvent.RESONATE_8, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)c / 24.0, 0.0, 0.0);
                break;
            case 9:
                world.emitGameEvent(entity, GameEvent.RESONATE_9, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)c / 24.0, 0.0, 0.0);
                break;
            case 10:
                world.emitGameEvent(entity, GameEvent.RESONATE_10, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)c / 24.0, 0.0, 0.0);
                break;
            case 11:
                world.emitGameEvent(entity, GameEvent.RESONATE_11, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)c / 24.0, 0.0, 0.0);
                break;
            case 12:
                world.emitGameEvent(entity, GameEvent.RESONATE_12, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)c / 24.0, 0.0, 0.0);
                break;
            case 13:
                world.emitGameEvent(entity, GameEvent.RESONATE_13, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)c / 24.0, 0.0, 0.0);
                break;
            case 14:
                world.emitGameEvent(entity, GameEvent.RESONATE_14, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)c / 24.0, 0.0, 0.0);
                break;
            case 15:
                world.emitGameEvent(entity, GameEvent.RESONATE_15, pos);
                world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)c / 24.0, 0.0, 0.0);
        }
    }

    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            state = state.cycle(TUNE);
            world.setBlockState(pos, state);
            this.playResonance(player, state, world, pos);
            player.incrementStat(Stats.TUNE_NOTEBLOCK);
        }

        return ActionResult.SUCCESS;
    }

    protected void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        if (!world.isClient) {
            this.playResonance(player, state, world, pos);
            player.incrementStat(Stats.PLAY_NOTEBLOCK);
        }
    }

    public static float getNotePitch(int note) {
        return (float)Math.pow(2.0, (double)(note - 12) / 12.0);
    }

    protected boolean onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data) {
        NoteBlockInstrument noteBlockInstrument = NoteBlockInstrument.BASEDRUM;
        float f;
        if (noteBlockInstrument.canBePitched()) {
            int i = state.get(TUNE);
            f = getNotePitch(i);
            world.addParticle(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)i / 24.0, 0.0, 0.0);
        } else {
            f = 1.0F;
        }
        RegistryEntry registryEntry = noteBlockInstrument.getSound();
        world.playSound(null, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, registryEntry, SoundCategory.RECORDS, 3.0F, f, world.random.nextLong());
        return true;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{POWERED, TUNE});
    }

    static {
        POWERED = Properties.POWERED;
        TUNE = IntProperty.of("tune", 1, 15);
        UP_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
        LOW_SHAPE = Block.createCuboidShape(2.0, 8.0, 2.0, 14.0, 12.0, 14.0);
        SHAPE = VoxelShapes.union(UP_SHAPE, LOW_SHAPE);
    }
}
