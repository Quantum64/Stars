package co.q64.stars.util;

import co.q64.stars.block.DarknessEdgeBlock;
import co.q64.stars.block.OrangeFormedBlock;
import co.q64.stars.block.SpecialDecayBlock;
import co.q64.stars.block.SpecialDecayEdgeBlock;
import co.q64.stars.capability.GardenerCapability;
import co.q64.stars.dimension.Dimensions;
import co.q64.stars.net.PacketManager;
import co.q64.stars.net.packets.PlayClientEffectPacket.ClientEffectType;
import co.q64.stars.tile.SpecialDecayEdgeTile.SpecialDecayType;
import co.q64.stars.type.FleetingStage;
import co.q64.stars.type.FormingBlockType;
import co.q64.stars.type.forming.GreenFormingBlockType;
import co.q64.stars.type.forming.PinkFormingBlockType;
import co.q64.stars.type.forming.YellowFormingBlockType;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Singleton
public class EntryManager {
    private static final int SPREAD_DISTANCE = 2000;

    protected @Inject Dimensions dimensions;
    protected @Inject OrangeFormedBlock orangeFormedBlock;
    protected @Inject SpecialDecayEdgeBlock specialDecayEdgeBlock;
    protected @Inject DarknessEdgeBlock darknessEdgeBlock;
    protected @Inject PacketManager packetManager;
    protected @Inject SeedManager seedManager;
    protected @Inject PinkFormingBlockType pinkFormingBlockType;
    protected @Inject YellowFormingBlockType yellowFormingBlockType;
    protected @Inject GreenFormingBlockType greenFormingBlockType;
    protected @Inject Provider<Capability<GardenerCapability>> gardenerCapability;

    private Set<FormingBlockType> formingBlockTypes;
    private int index = 0;

    @Inject
    protected EntryManager(Set<FormingBlockType> formingBlockTypes) {
        this.formingBlockTypes = formingBlockTypes.stream().filter(FormingBlockType::canGrow).collect(Collectors.toSet());
    }

    public void enterFleeting(ServerPlayerEntity player) {
        enterFleeting(player, false);
    }

    public void enterFleeting(ServerPlayerEntity player, boolean showEffect) {
        if (showEffect) {
            packetManager.getChannel().send(PacketDistributor.PLAYER.with(() -> player), packetManager.getPlayClientEffectPacketFactory().create(ClientEffectType.ENTRY));
        }
        BlockPos spawnpoint = getNext();
        ServerWorld world = DimensionManager.getWorld(player.getServer(), dimensions.getFleetingDimensionType(), false, true);
        setupSpawnpoint(world, spawnpoint);
        player.teleport(world, spawnpoint.getX() + 0.5, spawnpoint.getY(), spawnpoint.getZ() + 0.5, player.rotationYaw, player.rotationPitch);
        setStage(player, FleetingStage.LIGHT);
        setSeeds(player, 13); // TODO
        setKeys(player, 0);
        updateSeeds(player);
    }

    public void clearStage(ServerPlayerEntity player) {
        setStage(player, FleetingStage.NONE);
    }

    public FleetingStage getStage(ServerPlayerEntity player) {
        return player.getCapability(gardenerCapability.get()).map(GardenerCapability::getFleetingStage).orElse(FleetingStage.NONE);
    }

    public void setStage(ServerPlayerEntity player, FleetingStage stage) {
        player.getCapability(gardenerCapability.get()).ifPresent(c -> {
            c.setFleetingStage(stage);
            c.setTotalSeeds(0);
            c.getNextSeeds().clear();
        });
        syncCapability(player);
    }

    public int getSeeds(ServerPlayerEntity player) {
        return player.getCapability(gardenerCapability.get()).map(GardenerCapability::getSeeds).orElse(0);
    }

    public void setSeeds(ServerPlayerEntity player, final int lock) {
        player.getCapability(gardenerCapability.get()).ifPresent(c -> {
            int seeds = lock;
            c.setSeeds(seeds);
        });
        syncCapability(player);
    }

    public void updateSeeds(ServerPlayerEntity player) {
        player.getCapability(gardenerCapability.get()).ifPresent(c -> {
            int seeds = getSeeds(player);
            if (c.getFleetingStage() == FleetingStage.LIGHT) { // TODO hub
                while (c.getNextSeeds().size() < c.getSeedVisibility() && seeds > 0) {
                    FormingBlockType offering = pinkFormingBlockType;
                    if (c.getSeedsSincePink() < 5 + ThreadLocalRandom.current().nextInt(2)) {
                        for (int i = 0; i < 50; i++) {
                            offering = formingBlockTypes.stream().skip(ThreadLocalRandom.current().nextInt(formingBlockTypes.size())).findFirst().orElseThrow(() -> new RuntimeException("It is impossible for this exception to be thrown."));
                            if (offering != c.getLastSeed()) {
                                break;
                            }
                        }
                    }
                    if (c.getFleetingStage() == FleetingStage.LIGHT) {
                        if (c.getTotalSeeds() == 0) {
                            offering = pinkFormingBlockType;
                        } else if (c.getTotalSeeds() == 1) {
                            offering = yellowFormingBlockType;
                        } else if (c.getTotalSeeds() == 2) {
                            offering = greenFormingBlockType;
                            c.setSeedsSincePink(0);
                        }
                    }
                    c.setSeedsSincePink(offering == pinkFormingBlockType ? 0 : c.getSeedsSincePink() + 1);
                    c.setLastSeed(offering);
                    c.getNextSeeds().offer(offering);
                    c.setTotalSeeds(c.getTotalSeeds() + 1);
                    seeds--;
                }
            }
            setSeeds(player, seeds);
        });
    }

    public int getKeys(ServerPlayerEntity player) {
        return player.getCapability(gardenerCapability.get()).map(GardenerCapability::getKeys).orElse(0);
    }

    public void setKeys(ServerPlayerEntity player, int seeds) {
        player.getCapability(gardenerCapability.get()).ifPresent(c -> {
            c.setKeys(seeds);
        });
        syncCapability(player);
    }

    public void addSeed(ServerPlayerEntity player) {
        setSeeds(player, getSeeds(player) + 1);
    }

    public void addKey(ServerPlayerEntity player) {
        setKeys(player, getKeys(player) + 1);
    }

    public void useSeed(ServerPlayerEntity player, Runnable action) {
        if (getSeeds(player) > 0) {
            setSeeds(player, getSeeds(player) - 1);
            action.run();
        }
    }

    public void updateJumpStatus(ServerPlayerEntity player, boolean jumping) {
        if (getStage(player) == FleetingStage.DARK) {
            if (jumping) {
                useSeed(player, () -> {
                    player.addPotionEffect(new EffectInstance(Effects.LEVITATION, 200, 1, true, false));
                });
            } else {
                player.removePotionEffect(Effects.LEVITATION);
            }
        }
    }

    public void createDarkness(ServerPlayerEntity player) {
        setStage(player, FleetingStage.DARK);
        World world = player.getServerWorld();
        BlockPos playerPosition = player.getPosition();
        packetManager.getChannel().send(PacketDistributor.PLAYER.with(() -> player), packetManager.getPlayClientEffectPacketFactory().create(ClientEffectType.DARKNESS));
        world.setBlockState(playerPosition, darknessEdgeBlock.getDefaultState());
        player.teleport((ServerWorld) world, playerPosition.getX() + 0.5, playerPosition.getY() + 0.1, playerPosition.getZ() + 0.5, player.rotationYaw, player.rotationPitch);
    }

    public void grow(ServerPlayerEntity player) {
        player.getCapability(gardenerCapability.get()).ifPresent(c -> {
            if (c.getFleetingStage() == FleetingStage.LIGHT && !c.getNextSeeds().isEmpty()) {
                if (seedManager.tryGrow(player.getServerWorld(), player.getPosition().offset(Direction.DOWN), c.getNextSeeds().peek())) {
                    c.getNextSeeds().poll();
                    updateSeeds(player);
                }
            }
        });
    }

    private void syncCapability(ServerPlayerEntity player) {
        player.getCapability(gardenerCapability.get()).ifPresent(c -> packetManager.getChannel().send(PacketDistributor.PLAYER.with(() -> player), packetManager.getUpdateOverlayPacketFactory().create(c)));
    }

    // TODO set the initial decay according to level
    private void setupSpawnpoint(World world, BlockPos pos) {
        for (int y = pos.getY() - 8; y < pos.getY(); y++) {
            for (int x = pos.getX() - 2; x <= pos.getX() + 2; x++) {
                for (int z = pos.getZ() - 2; z <= pos.getZ() + 2; z++) {
                    world.setBlockState(new BlockPos(x, y, z), orangeFormedBlock.getDefaultState());
                }
            }
        }
        BlockPos door = new BlockPos(pos.getX(), pos.getY() - 8, pos.getZ());
        world.setBlockState(door, specialDecayEdgeBlock.getDefaultState().with(SpecialDecayBlock.TYPE, SpecialDecayType.DOOR));

        // MAJOR TODO
        for (int i = 0; i < 20; i++) {
            int[] position = new int[3];
            for (int p = 0; p < position.length; p++) {
                int rnd = ThreadLocalRandom.current().nextInt(30) + 10;
                if (p != 1) {
                    rnd = ThreadLocalRandom.current().nextBoolean() ? rnd : rnd * -1;
                }
                position[p] = rnd;
            }
            BlockPos key = pos.add(position[0], position[1], position[2]);
            world.setBlockState(key, specialDecayEdgeBlock.getDefaultState().with(SpecialDecayBlock.TYPE, SpecialDecayType.KEY));
        }
    }

    // TODO FIX
    private BlockPos getNext() {
        double sidelen = Math.floor(Math.sqrt(index));
        double layer = Math.floor((sidelen + 1) / 2.0);
        double offset = offset = index - sidelen * sidelen;
        int segment = (int) (Math.floor(offset / (sidelen + 1)) + 0.5);
        double offset2 = offset - 4 * segment + 1 - layer;
        double x = 0, y = 0;
        switch (segment) {
            case 0:
                x = layer;
                y = offset2;
            case 1:
                x = -offset2;
                y = layer;
            case 2:
                x = -layer;
                y = -offset2;
            case 3:
                x = offset2;
                y = -layer;
        }
        x *= SPREAD_DISTANCE;
        y *= SPREAD_DISTANCE;
        index++;
        return new BlockPos(x, 64, y);
    }
}
