package co.q64.stars.block;

import co.q64.stars.level.LevelType;
import co.q64.stars.tile.TrophyTile;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.inject.Provider;
import java.util.Optional;

@AutoFactory
public class TrophyBlock extends BaseBlock {
    private static final VoxelShape SHAPE = VoxelShapes.or(
            Block.makeCuboidShape(3, 0, 3, 13, 2, 13),
            Block.makeCuboidShape(6, 2, 6, 10, 6, 10));

    private Provider<TrophyTile> tileFactory;
    private @Getter TrophyVariant variant;

    protected TrophyBlock(TrophyVariant variant, @Provided Provider<TrophyTile> tileFactory) {
        super("trophy_" + variant.name().toLowerCase(), Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(5f, 6f).lightValue(variant == TrophyVariant.BASE ? 0 : 15));
        this.tileFactory = tileFactory;
        this.variant = variant;
    }

    public boolean hasTileEntity(BlockState state) {
        return variant != TrophyVariant.BASE;
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        TrophyTile result = tileFactory.get();
        result.setup(this);
        return result;
    }

    @Override
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        if (!world.isRemote() && hand == Hand.MAIN_HAND) {
            Optional.ofNullable((TrophyTile) world.getTileEntity(pos)).ifPresent(tile -> {
                tile.setSunbeams(!tile.isSunbeams());
                tile.markDirty();
                world.notifyBlockUpdate(pos, state, state, 3);
            });
        }
        return super.onBlockActivated(state, world, pos, player, hand, result);
    }

    public static enum TrophyVariant {
        BASE(null),
        BLUE(LevelType.BLUE),
        CYAN(LevelType.CYAN),
        GREEN(LevelType.GREEN),
        ORANGE(LevelType.ORANGE),
        PINK(LevelType.PINK),
        PURPLE(LevelType.PURPLE),
        TEAL(LevelType.TEAL),
        WHITE(LevelType.WHITE),
        YELLOW(LevelType.YELLOW),
        RED(LevelType.RED);

        private @Getter Optional<LevelType> type;

        private TrophyVariant(LevelType type) {
            this.type = Optional.ofNullable(type);
        }
    }
}
