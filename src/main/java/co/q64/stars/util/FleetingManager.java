package co.q64.stars.util;

import co.q64.stars.block.DarknessEdgeBlock;
import co.q64.stars.block.DecayBlock;
import co.q64.stars.block.GatewayBlock;
import co.q64.stars.block.OrangeFormedBlock;
import co.q64.stars.capability.GardenerCapability;
import co.q64.stars.dimension.Dimensions;
import co.q64.stars.entity.PickupEntity;
import co.q64.stars.level.Level;
import co.q64.stars.level.LevelManager;
import co.q64.stars.net.PacketManager;
import co.q64.stars.net.packets.ClientFadePacket.FadeMode;
import co.q64.stars.type.FleetingStage;
import co.q64.stars.type.FormingBlockType;
import co.q64.stars.util.DecayManager.SpecialDecayType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
public class FleetingManager {
    protected @Inject Dimensions dimensions;
    protected @Inject OrangeFormedBlock orangeFormedBlock;
    protected @Inject DecayBlock decayBlock;
    protected @Inject DarknessEdgeBlock darknessEdgeBlock;
    protected @Inject PacketManager packetManager;
    protected @Inject Provider<Capability<GardenerCapability>> gardenerCapability;
    protected @Inject DecayManager decayManager;
    protected @Inject SpawnpointManager spawnpointManager;
    protected @Inject PlayerManager playerManager;
    protected @Inject Scheduler scheduler;
    protected @Inject Capabilities capabilities;
    protected @Inject LevelManager levelManager;
    protected @Inject EntityType<PickupEntity> pickupEntityType;
    protected @Inject HubManager hubManager;

    private Set<FormingBlockType> formingBlockTypes;
    private Map<UUID, Integer> levitationQueue = new HashMap<>();
    private int index = 0;

    @Inject
    protected FleetingManager(Set<FormingBlockType> formingBlockTypes) {
        this.formingBlockTypes = formingBlockTypes.stream().filter(FormingBlockType::canGrow).collect(Collectors.toSet());
    }

    public void enter(ServerPlayerEntity player) {
        enter(player, false);
    }

    public void enter(ServerPlayerEntity player, boolean showEffect) {
        if (showEffect) {
            packetManager.getChannel().send(PacketDistributor.PLAYER.with(() -> player), packetManager.getClientFadePacketFactory().create(FadeMode.FADE_TO_WHITE, 3000));
            player.addPotionEffect(new EffectInstance(Effects.LEVITATION, 60, 1, true, false));
        }
        BlockPos spawnpoint = spawnpointManager.getSpawnpoint(index);
        index++;
        ServerWorld spawnWorld = DimensionManager.getWorld(player.getServer(), dimensions.getFleetingDimensionType(), false, true);
        capabilities.gardener(player, c -> {
            Level level = levelManager.getLevel(c.getLevelType());
            if (c.isOpenChallengeDoor()) {
                level.createChallenge(spawnWorld, spawnpoint);
            } else {
                setupSpawnpoint(spawnWorld, spawnpoint, level);
            }
        });
        Runnable task = () -> {
            ServerWorld world = DimensionManager.getWorld(player.getServer(), dimensions.getFleetingDimensionType(), false, true);
            capabilities.gardener(player, c -> {
                player.setMotion(0, 0, 0);
                player.teleport(world, spawnpoint.getX() + 0.5, showEffect ? spawnpoint.getY() + (c.isOpenChallengeDoor() ? 1 : 10) : spawnpoint.getY(), spawnpoint.getZ() + 0.5, player.rotationYaw, player.rotationPitch);
                setStage(player, FleetingStage.LIGHT);
                c.setTotalSeeds(0);
                c.getNextSeeds().clear();
                c.setOpenDoor(c.isOpenChallengeDoor());
                c.setOpenChallengeDoor(false);
                c.setEnteringHub(false);
                playerManager.setSeeds(player, c.isOpenDoor() ? c.getSeeds() : 13);
            });
            setKeys(player, 0);
            playerManager.updateSeeds(player);
            playerManager.syncCapability(player);
            if (showEffect) {
                player.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING, 60, 3, true, false));
                packetManager.getChannel().send(PacketDistributor.PLAYER.with(() -> player), packetManager.getClientFadePacketFactory().create(FadeMode.FADE_FROM_WHITE, 3000));
            }
        };
        scheduler.run(task, showEffect ? 60 : 0);
    }

    public void tryEnter(ServerPlayerEntity player) {
        capabilities.gardener(player, gardener -> {
            if (gardener.getFleetingStage() == FleetingStage.NONE) {
                ServerWorld world = player.getServerWorld();
                BlockPos target = player.getPosition().offset(Direction.DOWN);
                BlockState state = world.getBlockState(target);
                if (state.getBlock() instanceof GatewayBlock) {
                    gardener.setLevelType(state.get(GatewayBlock.TYPE));
                    enter(player, true);
                }
            }
        });
    }

    public void clearStage(ServerPlayerEntity player) {
        setStage(player, FleetingStage.NONE);
    }

    public FleetingStage getStage(ServerPlayerEntity player) {
        return player.getCapability(gardenerCapability.get()).map(GardenerCapability::getFleetingStage).orElse(FleetingStage.NONE);
    }

    public void setStage(ServerPlayerEntity player, FleetingStage stage) {
        capabilities.gardener(player, c -> {
            c.setFleetingStage(stage);
        });
        playerManager.syncCapability(player);
    }

    public int getKeys(ServerPlayerEntity player) {
        return player.getCapability(gardenerCapability.get()).map(GardenerCapability::getKeys).orElse(0);
    }

    public void setKeys(ServerPlayerEntity player, int seeds) {
        capabilities.gardener(player, c -> {
            c.setKeys(seeds);
        });
        playerManager.syncCapability(player);
    }

    public void touchHeart(ServerPlayerEntity player) {
        capabilities.gardener(player, gardener -> {
            if (gardener.isOpenDoor()) {
                hubManager.enter(player);
            } else {
                playerManager.pickupSeed(player);
            }
        });
    }

    public void addKey(ServerPlayerEntity player) {
        setKeys(player, getKeys(player) + 1);
    }

    public void updateJumpStatus(ServerPlayerEntity player, boolean jumping) {
        if (getStage(player) == FleetingStage.DARK) {
            if (jumping) {
                levitationQueue.put(player.getUniqueID(), 18);
            } else {
                levitationQueue.remove(player.getUniqueID());
                player.removePotionEffect(Effects.LEVITATION);
            }
        }
    }

    public void createDarkness(ServerPlayerEntity player) {
        capabilities.gardener(player, gardener -> {
            if (gardener.isOpenDoor()) {
                playerManager.setSeeds(player, 0);
                hubManager.enter(player);
                return;
            }
            setStage(player, FleetingStage.DARK);
            ServerWorld world = player.getServerWorld();
            world.getEntities()
                    .filter(entity -> entity.getPosition().distanceSq(player.getPosition()) < 1000 * 1000)
                    .filter(entity -> entity instanceof PickupEntity)
                    .map(entity -> (PickupEntity) entity)
                    .filter(p -> p.getVariant() == PickupEntity.VARIANT_STAR || p.getVariant() == PickupEntity.VARIANT_CHALLENGE)
                    .forEach(Entity::remove);
            BlockPos playerPosition = player.getPosition();
            world.setBlockState(playerPosition, darknessEdgeBlock.getDefaultState());
            player.teleport((ServerWorld) world, playerPosition.getX() + 0.5, playerPosition.getY() + 0.1, playerPosition.getZ() + 0.5, player.rotationYaw, player.rotationPitch);
        });
    }

    public void tickPlayer(ServerPlayerEntity player) {
        UUID uuid = player.getUniqueID();
        if (levitationQueue.containsKey(uuid)) {
            int ticks = levitationQueue.get(uuid) - 1;
            if (ticks > 0) {
                levitationQueue.put(uuid, ticks);
                return;
            }
            levitationQueue.remove(player.getUniqueID());
            playerManager.useSeed(player, () -> {
                player.addPotionEffect(new EffectInstance(Effects.LEVITATION, 200, 1, true, false));
            });
        }
    }

    // TODO set the initial decay according to level
    private void setupSpawnpoint(World world, BlockPos pos, Level level) {
        for (int y = pos.getY() - 8; y < pos.getY(); y++) {
            for (int x = pos.getX() - 2; x <= pos.getX() + 2; x++) {
                for (int z = pos.getZ() - 2; z <= pos.getZ() + 2; z++) {
                    world.setBlockState(new BlockPos(x, y, z), orangeFormedBlock.getDefaultState());
                }
            }
        }
        BlockPos door = new BlockPos(pos.getX(), pos.getY() - 8, pos.getZ());
        decayManager.createSpecialDecay(world, door, SpecialDecayType.DOOR);
        for (BlockPos challenge : level.getChallengeStars(pos)) {
            for (int y = challenge.getY() - 1; y <= challenge.getY() + 1; y++) {
                for (int x = challenge.getX() - 1; x <= challenge.getX() + 1; x++) {
                    for (int z = challenge.getZ() - 1; z <= challenge.getZ() + 1; z++) {
                        world.setBlockState(new BlockPos(x, y, z), decayBlock.getDefaultState());
                    }
                }
            }
            decayManager.createSpecialDecay(world, challenge, SpecialDecayType.CHALLENGE_DOOR);
        }
    }

    private void createKey(World world, BlockPos key) {
        for (int x = -3; x < 3; x++) {
            for (int y = -3; y < 3; y++) {
                for (int z = -3; z < 3; z++) {
                    world.setBlockState(key.add(x, y, z), decayBlock.getDefaultState());
                }
            }
        }
        decayManager.createSpecialDecay(world, key, SpecialDecayType.KEY);
    }
}
