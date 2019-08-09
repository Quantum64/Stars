package co.q64.stars.util;

import co.q64.stars.block.BlueFormedBlock;
import co.q64.stars.block.BrownFormedBlock;
import co.q64.stars.block.CyanFormedBlock;
import co.q64.stars.block.GatewayBlock;
import co.q64.stars.block.GreenFormedBlock;
import co.q64.stars.block.GreyFormedBlock;
import co.q64.stars.block.OrangeFormedBlock;
import co.q64.stars.block.PinkFormedBlock;
import co.q64.stars.block.PurpleFormedBlock;
import co.q64.stars.block.RedFormedBlock;
import co.q64.stars.block.YellowFormedBlock;
import co.q64.stars.level.LevelType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;

@Singleton
public class Structures implements IFutureReloadListener {
    protected @Inject Identifiers identifiers;

    protected @Inject PinkFormedBlock pinkFormedBlock;
    protected @Inject RedFormedBlock redFormedBlock;
    protected @Inject BlueFormedBlock blueFormedBlock;
    protected @Inject OrangeFormedBlock orangeFormedBlock;
    protected @Inject PurpleFormedBlock purpleFormedBlock;
    protected @Inject BrownFormedBlock brownFormedBlock;
    protected @Inject GreenFormedBlock greenFormedBlock;
    protected @Inject YellowFormedBlock yellowFormedBlock;
    protected @Inject CyanFormedBlock cyanFormedBlock;
    protected @Inject GreyFormedBlock greyFormedBlock;

    protected @Inject GatewayBlock gatewayBlock;

    private Map<Integer, BlockState> ids;
    private Map<StructureType, Structure> structures = Collections.emptyMap();

    protected @Inject Structures() {}

    public Structure get(StructureType type) {
        return structures.get(type);
    }

    public CompletableFuture<Void> reload(IStage stage, IResourceManager resourceManager, IProfiler backgroundProfiler, IProfiler gameProfiler, Executor backgroundExecutor, Executor gameExecutor) {
        CompletableFuture<Map<StructureType, Structure>> future = CompletableFuture.supplyAsync(() -> {
            return this.prepare(resourceManager, backgroundProfiler);
        }, backgroundExecutor);
        stage.getClass();
        return future.thenCompose(stage::markCompleteAwaitingOthers).thenAcceptAsync((map) -> {
            this.apply(map, resourceManager, gameProfiler);
        }, gameExecutor);
    }

    protected Map<StructureType, Structure> prepare(IResourceManager resourceManager, IProfiler profiler) {
        Map<StructureType, Structure> result = new HashMap<>();
        for (StructureType type : StructureType.values()) {
            try (IResource resource = resourceManager.getResource(identifiers.get("world/" + type.getName()))) {
                CompoundNBT tag = CompressedStreamTools.readCompressed(resource.getInputStream());
                int width = tag.getShort("Width");
                int height = tag.getShort("Height");
                int length = tag.getShort("Length");
                byte[] blocks = tag.getByteArray("Blocks");
                byte[] data = tag.getByteArray("Data");
                result.put(type, new Structure(blocks, data, width, length, height));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    protected void apply(Map<StructureType, Structure> structures, IResourceManager resourceManager, IProfiler profiler) {
        this.structures = structures;
    }

    public static enum StructureType {
        HUB_WHITE("hub_white.dat");

        private @Getter String name;

        private StructureType(String name) {
            this.name = name;
        }
    }

    @AllArgsConstructor
    public class Structure {
        private byte[] blocks, data;
        private @Getter int width, length, height;

        private int index(BlockPos pos) {
            return (pos.getY() * this.length + pos.getZ()) * this.width + pos.getX();
        }

        public BlockState getBlock(BlockPos pos) {
            return ids.getOrDefault(id(blocks[index(pos)] & 0xff, data[index(pos)]), Blocks.BEDROCK.getDefaultState());
        }

        public void forEachBlock(BiConsumer<BlockPos, BlockState> action) {
            forEachBlock(BlockPos.ZERO, action);
        }

        public void forEachBlock(BlockPos offset, BiConsumer<BlockPos, BlockState> action) {
            for (int x = 0; x < width; x++) {
                for (int z = 0; z < length; z++) {
                    for (int y = 0; y < height; y++) {
                        BlockPos pos = new BlockPos(x, y, z);
                        action.accept(pos.add(offset), getBlock(pos));
                    }
                }
            }
        }

        public void place(World world, BlockPos pos) {
            forEachBlock(pos, (block, state) -> {
                world.setBlockState(block, state, 0);
            });
        }
    }

    private int id(int block, int color) {
        return (block << 4) + color;
    }

    private int id(Block block, Color color) {
        return id(block.getId(), color.getId());
    }

    private static enum Block {
        AIR(0),
        STAINED_CLAY(159),
        STAINED_GLASS(95);

        private @Getter int id;

        private Block(int id) {
            this.id = id;
        }
    }

    private static enum Color {
        WHITE(0),
        ORANGE(1),
        MAGENTA(2),
        LIGHT_BLUE(3),
        YELLOW(4),
        LIME(5),
        PINK(6),
        GRAY(7),
        LIGHT_GRAY(8),
        CYAN(9),
        PURPLE(10),
        BLUE(11),
        BROWN(12),
        GREEN(13),
        RED(14),
        BLACK(15);

        private @Getter int id;

        private Color(int id) {
            this.id = id;
        }
    }

    private void put(Map<Integer, BlockState> map, Block block, Color color, BlockState state) {
        map.put(id(block, color), state);
    }

    @Inject
    protected void init() {
        Map<Integer, BlockState> ids = new HashMap<>();
        put(ids, Block.AIR, Color.WHITE, Blocks.AIR.getDefaultState());

        put(ids, Block.STAINED_CLAY, Color.PINK, pinkFormedBlock.getDefaultState());
        put(ids, Block.STAINED_CLAY, Color.RED, redFormedBlock.getDefaultState());
        put(ids, Block.STAINED_CLAY, Color.BLUE, blueFormedBlock.getDefaultState());
        put(ids, Block.STAINED_CLAY, Color.ORANGE, orangeFormedBlock.getDefaultState());
        put(ids, Block.STAINED_CLAY, Color.PURPLE, purpleFormedBlock.getDefaultState());
        put(ids, Block.STAINED_CLAY, Color.BROWN, brownFormedBlock.getDefaultState());
        put(ids, Block.STAINED_CLAY, Color.GREEN, greenFormedBlock.getDefaultState());
        put(ids, Block.STAINED_CLAY, Color.YELLOW, yellowFormedBlock.getDefaultState());
        put(ids, Block.STAINED_CLAY, Color.CYAN, cyanFormedBlock.getDefaultState());
        put(ids, Block.STAINED_CLAY, Color.GRAY, greyFormedBlock.getDefaultState());

        put(ids, Block.STAINED_GLASS, Color.WHITE, gatewayBlock.getDefaultState().with(GatewayBlock.TYPE, LevelType.WHITE));
        put(ids, Block.STAINED_GLASS, Color.RED, gatewayBlock.getDefaultState().with(GatewayBlock.TYPE, LevelType.RED));
        put(ids, Block.STAINED_GLASS, Color.ORANGE, gatewayBlock.getDefaultState().with(GatewayBlock.TYPE, LevelType.ORANGE));
        put(ids, Block.STAINED_GLASS, Color.BLUE, gatewayBlock.getDefaultState().with(GatewayBlock.TYPE, LevelType.BLUE));
        put(ids, Block.STAINED_GLASS, Color.CYAN, gatewayBlock.getDefaultState().with(GatewayBlock.TYPE, LevelType.CYAN));
        put(ids, Block.STAINED_GLASS, Color.GREEN, gatewayBlock.getDefaultState().with(GatewayBlock.TYPE, LevelType.GREEN));
        put(ids, Block.STAINED_GLASS, Color.PINK, gatewayBlock.getDefaultState().with(GatewayBlock.TYPE, LevelType.PINK));
        put(ids, Block.STAINED_GLASS, Color.PURPLE, gatewayBlock.getDefaultState().with(GatewayBlock.TYPE, LevelType.PURPLE));
        put(ids, Block.STAINED_GLASS, Color.YELLOW, gatewayBlock.getDefaultState().with(GatewayBlock.TYPE, LevelType.YELLOW));
        put(ids, Block.STAINED_GLASS, Color.LIGHT_BLUE, gatewayBlock.getDefaultState().with(GatewayBlock.TYPE, LevelType.TEAL));

        this.ids = Collections.unmodifiableMap(ids);
    }
}
