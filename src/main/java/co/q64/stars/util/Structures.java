package co.q64.stars.util;

import co.q64.stars.block.BlueFormedBlock;
import co.q64.stars.block.BlueFormedBlock.BlueFormedBlockHard;
import co.q64.stars.block.BrownFormedBlock;
import co.q64.stars.block.BrownFormedBlock.BrownFormedBlockHard;
import co.q64.stars.block.ChallengeEntranceBlock;
import co.q64.stars.block.ChallengeExitBlock;
import co.q64.stars.block.CyanFormedBlock;
import co.q64.stars.block.CyanFormedBlock.CyanFormedBlockHard;
import co.q64.stars.block.DarknessBlock;
import co.q64.stars.block.DecayBlock;
import co.q64.stars.block.DecayBlock.DecayBlockSolid;
import co.q64.stars.block.DecayEdgeBlock;
import co.q64.stars.block.GatewayBlock;
import co.q64.stars.block.GreenFormedBlock;
import co.q64.stars.block.GreenFormedBlock.GreenFormedBlockHard;
import co.q64.stars.block.GreyFormedBlock;
import co.q64.stars.block.OrangeFormedBlock;
import co.q64.stars.block.OrangeFormedBlock.OrangeFormedBlockHard;
import co.q64.stars.block.PinkFormedBlock;
import co.q64.stars.block.PurpleFormedBlock;
import co.q64.stars.block.PurpleFormedBlock.PurpleFormedBlockHard;
import co.q64.stars.block.RedFormedBlock;
import co.q64.stars.block.RedFormedBlock.RedFormedBlockHard;
import co.q64.stars.block.RedPrimedBlock;
import co.q64.stars.block.RedPrimedBlock.RedPrimedBlockHard;
import co.q64.stars.block.TealFormedBlock;
import co.q64.stars.block.WhiteFormedBlock;
import co.q64.stars.block.YellowFormedBlock;
import co.q64.stars.block.YellowFormedBlock.YellowFormedBlockHard;
import co.q64.stars.level.LevelType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
    protected @Inject RedPrimedBlock redPrimedBlock;
    protected @Inject WhiteFormedBlock whiteFormedBlock;
    protected @Inject TealFormedBlock tealFormedBlock;

    protected @Inject RedFormedBlockHard redFormedBlockHard;
    protected @Inject BlueFormedBlockHard blueFormedBlockHard;
    protected @Inject OrangeFormedBlockHard orangeFormedBlockHard;
    protected @Inject PurpleFormedBlockHard purpleFormedBlockHard;
    protected @Inject BrownFormedBlockHard brownFormedBlockHard;
    protected @Inject GreenFormedBlockHard greenFormedBlockHard;
    protected @Inject YellowFormedBlockHard yellowFormedBlockHard;
    protected @Inject CyanFormedBlockHard cyanFormedBlockHard;
    protected @Inject RedPrimedBlockHard redPrimedBlockHard;

    protected @Inject DecayBlock decayBlock;
    protected @Inject DecayBlockSolid decayBlockSolid;
    protected @Inject DecayEdgeBlock decayEdgeBlock;
    protected @Inject DarknessBlock darknessBlock;
    protected @Inject ChallengeEntranceBlock challengeEntranceBlock;
    protected @Inject ChallengeExitBlock challengeExitBlock;

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
        HUB_WHITE("hub_white.dat"),
        HUB_GREEN("hub_green.dat"),
        HUB_ORANGE("hub_orange.dat"),
        HUB_BLUE("hub_blue.dat"),
        HUB_PURPLE("hub_purple.dat"),
        HUB_YELLOW("hub_yellow.dat"),
        HUB_TEAL("hub_teal.dat"),
        HUB_RED("hub_red.dat"),
        HUB_PINK("hub_pink.dat"),
        HUB_CYAN("hub_cyan.dat"),

        CHALLENGE_WHITE("challenge_white.dat"),
        CHALLENGE_GREEN("challenge_green.dat"),
        CHALLENGE_ORANGE("challenge_orange.dat"),
        CHALLENGE_BLUE("challenge_blue.dat"),
        CHALLENGE_PURPLE("challenge_purple.dat"),
        CHALLENGE_YELLOW("challenge_yellow.dat"),
        CHALLENGE_TEAL("challenge_teal.dat"),
        CHALLENGE_RED("challenge_red.dat"),
        CHALLENGE_PINK("challenge_pink.dat"),
        CHALLENGE_CYAN("challenge_cyan.dat");

        private @Getter String name;

        private StructureType(String name) {
            this.name = name;
        }
    }

    @RequiredArgsConstructor
    public class Structure {
        private final byte[] blocks, data;
        private final @Getter int width, length, height;
        private BlockPos challengeStartCache = null;

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

        public void place(World world, BlockPos pos, boolean ignoreAir) {
            forEachBlock(pos, (block, state) -> {
                if (state.getBlock() == Blocks.AIR) {
                    return;
                }
                world.setBlockState(block, state, 0);
            });
        }

        public void place(World world, BlockPos pos) {
            place(world, pos, true);
        }

        private BlockPos getChallengeStart(BlockPos offset) {
            if (challengeStartCache != null) {
                return challengeStartCache.add(offset);
            }
            challengeStartCache = BlockPos.ZERO;
            forEachBlock((pos, state) -> {
                if (state.getBlock() == challengeEntranceBlock) {
                    challengeStartCache = pos;
                }
            });
            return challengeStartCache.add(offset);
        }

        public BlockPos placeChallenge(World world, BlockPos pos) {
            place(world, pos);
            return getChallengeStart(pos);
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
        STONE(1),
        DIRT(3),
        COBBLESTONE(4),
        BEDROCK(7),
        WOOL(35),
        BARRIER(166),
        LAPIS(22),
        SPONGE(19),
        STAINED_GLASS(95),
        STAINED_CLAY(159);

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

    private void put(Map<Integer, BlockState> map, Block block, BlockState state) {
        map.put(id(block, Color.WHITE), state);
    }

    @Inject
    protected void init() {
        Map<Integer, BlockState> ids = new HashMap<>();
        put(ids, Block.AIR, Blocks.AIR.getDefaultState());

        put(ids, Block.STAINED_CLAY, Color.PINK, pinkFormedBlock.getDefaultState());
        put(ids, Block.STAINED_CLAY, Color.RED, redFormedBlock.getDefaultState());
        put(ids, Block.STAINED_CLAY, Color.MAGENTA, redPrimedBlock.getDefaultState());
        put(ids, Block.STAINED_CLAY, Color.BLUE, blueFormedBlock.getDefaultState());
        put(ids, Block.STAINED_CLAY, Color.ORANGE, orangeFormedBlock.getDefaultState());
        put(ids, Block.STAINED_CLAY, Color.PURPLE, purpleFormedBlock.getDefaultState());
        put(ids, Block.STAINED_CLAY, Color.BROWN, brownFormedBlock.getDefaultState());
        put(ids, Block.STAINED_CLAY, Color.GREEN, greenFormedBlock.getDefaultState());
        put(ids, Block.STAINED_CLAY, Color.YELLOW, yellowFormedBlock.getDefaultState());
        put(ids, Block.STAINED_CLAY, Color.CYAN, cyanFormedBlock.getDefaultState());
        put(ids, Block.STAINED_CLAY, Color.GRAY, greyFormedBlock.getDefaultState());
        put(ids, Block.STAINED_CLAY, Color.LIGHT_BLUE, tealFormedBlock.getDefaultState());
        put(ids, Block.STAINED_CLAY, Color.WHITE, whiteFormedBlock.getDefaultState());

        put(ids, Block.WOOL, Color.RED, redFormedBlockHard.getDefaultState());
        put(ids, Block.WOOL, Color.MAGENTA, redPrimedBlockHard.getDefaultState());
        put(ids, Block.WOOL, Color.BLUE, blueFormedBlockHard.getDefaultState());
        put(ids, Block.WOOL, Color.ORANGE, orangeFormedBlockHard.getDefaultState());
        put(ids, Block.WOOL, Color.PURPLE, purpleFormedBlockHard.getDefaultState());
        put(ids, Block.WOOL, Color.BROWN, brownFormedBlockHard.getDefaultState());
        put(ids, Block.WOOL, Color.GREEN, greenFormedBlockHard.getDefaultState());
        put(ids, Block.WOOL, Color.YELLOW, yellowFormedBlockHard.getDefaultState());
        put(ids, Block.WOOL, Color.CYAN, cyanFormedBlockHard.getDefaultState());

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

        put(ids, Block.BARRIER, Blocks.BARRIER.getDefaultState());
        put(ids, Block.STONE, decayBlock.getDefaultState());
        put(ids, Block.DIRT, darknessBlock.getDefaultState());
        put(ids, Block.BEDROCK, decayBlockSolid.getDefaultState());
        put(ids, Block.COBBLESTONE, decayEdgeBlock.getDefaultState());
        put(ids, Block.LAPIS, challengeExitBlock.getDefaultState());
        put(ids, Block.SPONGE, challengeEntranceBlock.getDefaultState());

        this.ids = Collections.unmodifiableMap(ids);
    }
}
