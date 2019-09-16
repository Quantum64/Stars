package co.q64.stars.dimension.overworld.feature;

import co.q64.stars.block.FormedBlock;
import co.q64.stars.block.StarboundGatewayBlock;
import co.q64.stars.type.FormingBlockType;
import co.q64.stars.type.FormingBlockTypes;
import co.q64.stars.util.Identifiers;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Singleton
public class GatewayFeature extends Feature<NoFeatureConfig> {
    private static final int RADIUS = 20;

    protected @Inject Set<FormingBlockType> formingTypes;
    protected @Inject StarboundGatewayBlock starboundGatewayBlock;

    @Inject
    protected GatewayFeature(Identifiers identifiers) {
        super(NoFeatureConfig::deserialize);
        setRegistryName(identifiers.get("gateway"));
    }

    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        while (pos.getY() > RADIUS && world.getBlockState(pos).isAir(world, pos)) {
            pos = pos.down();
        }
        for (int x = pos.getX() - RADIUS; x <= pos.getX() + RADIUS; x++) {
            for (int z = pos.getZ() - RADIUS; z <= pos.getZ() + RADIUS; z++) {
                for (int y = pos.getY() - RADIUS; y <= pos.getY(); y++) {
                    BlockPos target = new BlockPos(x, y, z);
                    if (target.distanceSq(pos) < RADIUS * RADIUS) {
                        world.setBlockState(pos, Blocks.AIR.getDefaultState(), 0);
                    }
                }
            }
        }
        BlockState edge = null;
        int index = 0, typeIndex = rand.nextInt(formingTypes.size());
        for (FormingBlockType type : formingTypes) {
            edge = type.getFormedBlock().getDefaultState();
            if (index == typeIndex) {
                break;
            }
            index++;
        }
        for (int x = pos.getX() - RADIUS; x <= pos.getX() + RADIUS; x++) {
            for (int z = pos.getZ() - RADIUS; z <= pos.getZ() + RADIUS; z++) {
                for (int y = pos.getY() - RADIUS; y <= pos.getY(); y++) {
                    BlockPos target = new BlockPos(x, y, z);
                    if (target.distanceSq(pos) < RADIUS * RADIUS) {
                        BlockState test = world.getBlockState(target.down());
                        if (!test.isAir(world, target.down()) && !(test.getBlock() instanceof FormedBlock)) {
                            List<BlockPos> placed = new ArrayList<>();
                            world.setBlockState(pos, edge, 0);
                            placed.add(pos);
                            int count = 0;
                            for (int i = 0; i < 16; i++) {
                                BlockPos bp = placed.get(rand.nextInt(placed.size()));
                                for (Direction direction : Direction.values()) {
                                    if (world.getBlockState(bp.offset(direction)).getBlock() == edge.getBlock()) {
                                        count++;
                                        if (count > 1) {
                                            break;
                                        }
                                    }
                                }
                                if (count == 1) {
                                    world.setBlockState(bp, edge, 0);
                                    placed.add(bp);
                                }
                            }
                        }
                    }
                }
            }
        }
        world.setBlockState(pos, starboundGatewayBlock.getDefaultState(), 0);
        return false;
    }
}
