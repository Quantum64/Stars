package co.q64.stars.dimension.overworld.feature;

import co.q64.stars.block.FormedBlock;
import co.q64.stars.block.StarboundGatewayBlock;
import co.q64.stars.type.FormingBlockType;
import co.q64.stars.type.forming.GreyFormingBlockType;
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

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Singleton
public class GatewayFeature extends Feature<NoFeatureConfig> {
    private static final Collection<Direction> NOT_UP = Stream.of(Direction.values()).filter(d -> d != Direction.UP).collect(Collectors.toList());
    private static final int RADIUS = 10;

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
                        world.setBlockState(target, Blocks.AIR.getDefaultState(), 0);
                    }
                }
            }
        }
        BlockState edge = formingTypes.iterator().next().getFormedBlock().getDefaultState();
        int index = 0, typeIndex = rand.nextInt(formingTypes.size());
        for (FormingBlockType type : formingTypes) {
            if (type instanceof GreyFormingBlockType) {
                continue;
            }
            edge = type.getFormedBlock().getDefaultState();
            if (index >= typeIndex) {
                break;
            }
            index++;
        }
        for (int x = pos.getX() - RADIUS; x <= pos.getX() + RADIUS; x++) {
            for (int z = pos.getZ() - RADIUS; z <= pos.getZ() + RADIUS; z++) {
                for (int y = pos.getY() - RADIUS; y <= pos.getY(); y++) {
                    BlockPos target = new BlockPos(x, y, z);
                    if (target.distanceSq(pos) < RADIUS * RADIUS) {
                        for (Direction dir : NOT_UP) {
                            BlockPos place = target.offset(dir);
                            BlockState test = world.getBlockState(place);
                            if (!test.isAir(world, target.offset(dir)) && !(test.getBlock() instanceof FormedBlock)) {
                                List<BlockPos> placed = new ArrayList<>();
                                world.setBlockState(place, edge, 0);
                                placed.add(place);
                                int count = 0;
                                for (int i = 0; i < 16; i++) {
                                    BlockPos bp = placed.get(rand.nextInt(placed.size())).offset(Direction.values()[rand.nextInt(Direction.values().length)]);
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
                                break;
                            }
                        }
                    }
                }
            }
        }
        BlockPos gateway = pos.down(5);
        for (int i = 0; i < 25; i++) {
            gateway = gateway.down();
            if (!world.getBlockState(gateway).isAir(world, gateway)) {
                break;
            }
        }
        world.setBlockState(gateway, starboundGatewayBlock.getDefaultState(), 0);
        return true;
    }
}
