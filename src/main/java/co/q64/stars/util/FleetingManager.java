package co.q64.stars.util;

import co.q64.stars.block.DarknessEdgeBlock;
import co.q64.stars.block.DecayBlock;
import co.q64.stars.block.GatewayBlock;
import co.q64.stars.block.OrangeFormedBlock;
import co.q64.stars.block.OrangeFormedBlock.OrangeFormedBlockHard;
import co.q64.stars.block.SpecialDecayBlock;
import co.q64.stars.block.SpecialDecayEdgeBlock;
import co.q64.stars.capability.GardenerCapability;
import co.q64.stars.dimension.fleeting.FleetingDimension.FleetingDimensionTemplate;
import co.q64.stars.dimension.fleeting.FleetingSolidDimension.FleetingSolidDimensionTemplate;
import co.q64.stars.entity.PickupEntity;
import co.q64.stars.level.Level;
import co.q64.stars.level.LevelManager;
import co.q64.stars.level.LevelType;
import co.q64.stars.net.PacketManager;
import co.q64.stars.net.packets.ClientFadePacket.FadeMode;
import co.q64.stars.qualifier.SoundQualifiers.Bubble;
import co.q64.stars.qualifier.SoundQualifiers.Key;
import co.q64.stars.qualifier.SoundQualifiers.Pop;
import co.q64.stars.qualifier.SoundQualifiers.Thunder;
import co.q64.stars.tile.DecayEdgeTile;
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
import net.minecraft.util.SoundEvent;
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
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
public class FleetingManager {
    protected @Inject OrangeFormedBlock orangeFormedBlock;
    protected @Inject OrangeFormedBlockHard orangeFormedBlockHard;
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
    protected @Inject Sounds sounds;
    protected @Inject @Key SoundEvent keySound;
    protected @Inject @Bubble SoundEvent bubbleSound;
    protected @Inject @Pop SoundEvent popSound;
    protected @Inject @Thunder Set<SoundEvent> thunderSounds;
    protected @Inject FleetingDimensionTemplate fleetingDimensionTemplate;
    protected @Inject FleetingSolidDimensionTemplate fleetingSolidDimensionTemplate;

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
        capabilities.gardener(player, gardener -> {
            if (gardener.isEnteringFleeting()) {
                return;
            }
            gardener.setEnteringFleeting(true);
            if (showEffect) {
                packetManager.getChannel().send(PacketDistributor.PLAYER.with(() -> player), packetManager.getClientFadePacketFactory().create(FadeMode.FADE_TO_WHITE, 3000));
                player.addPotionEffect(new EffectInstance(Effects.LEVITATION, 60, 1, true, false));
            }
            BlockPos levelStart = spawnpointManager.getSpawnpoint(index);
            BlockPos teleportPoint = levelStart;
            index++;
            ServerWorld spawnWorld = getSpawnWorld(player);

            Level level = levelManager.getLevel(gardener.getLevelType());
            if (gardener.isOpenChallengeDoor()) {
                teleportPoint = level.createChallenge(spawnWorld, levelStart);
            } else {
                setupSpawnpoint(player, spawnWorld, levelStart, level);
                teleportPoint = levelStart.add(0, 10, 0);
            }
            final BlockPos lock = teleportPoint;
            Runnable task = () -> {
                gardener.setEnteringFleeting(false);
                ServerWorld world = getSpawnWorld(player);
                player.setMotion(0, 0, 0);
                player.teleport(world, lock.getX() + 0.5, lock.getY(), lock.getZ() + 0.5, player.rotationYaw, player.rotationPitch);
                setStage(player, FleetingStage.LIGHT);
                gardener.setTotalSeeds(0);
                gardener.getNextSeeds().clear();
                gardener.setOpenDoor(gardener.isOpenChallengeDoor());
                gardener.setOpenChallengeDoor(false);
                gardener.setEnteringHub(false);
                playerManager.setSeeds(player, gardener.isOpenDoor() ? gardener.getSeeds() : gardener.getSeeds() > 13 ? gardener.getSeeds() : 13);
                setKeys(player, 0);
                playerManager.updateSeeds(player);
                playerManager.syncCapability(player);
                if (showEffect) {
                    player.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING, 60, 3, true, false));
                    packetManager.getChannel().send(PacketDistributor.PLAYER.with(() -> player), packetManager.getClientFadePacketFactory().create(FadeMode.FADE_FROM_WHITE, 3000));
                }
            };
            scheduler.run(task, showEffect ? 60 : 0);
        });
    }

    public void tryEnter(ServerPlayerEntity player) {
        capabilities.gardener(player, gardener -> {
            if (gardener.getFleetingStage() == FleetingStage.NONE) {
                ServerWorld world = player.getServerWorld();
                BlockPos target = player.getPosition().offset(Direction.DOWN);
                BlockState state = world.getBlockState(target);
                if (state.getBlock() instanceof GatewayBlock) {
                    gardener.setLevelType(state.get(GatewayBlock.TYPE));
                    gardener.setHubSpawn(target.offset(Direction.UP));
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
                gardener.setCompleteChallenge(true);
                hubManager.enter(player);
            } else {
                playerManager.pickupSeed(player);
            }
        });
    }

    public void addKey(ServerPlayerEntity player) {
        setKeys(player, getKeys(player) + 1);
    }

    public void touchKey(ServerPlayerEntity player) {
        addKey(player);
        sounds.playSound(player.getServerWorld(), player.getPosition(), keySound, 1f);
    }

    public void updateJumpStatus(ServerPlayerEntity player, boolean jumping) {
        if (getStage(player) == FleetingStage.DARK) {
            if (jumping) {
                levitationQueue.put(player.getUniqueID(), 18);
            } else {
                levitationQueue.remove(player.getUniqueID());
                if (player.getActivePotionEffect(Effects.LEVITATION) != null) {
                    player.removePotionEffect(Effects.LEVITATION);
                    sounds.playSound(player.getServerWorld(), player.getPosition(), popSound, 1f);
                }
            }
        }
    }

    public void createDarkness(ServerPlayerEntity player) {
        capabilities.gardener(player, gardener -> {
            if (gardener.isCompleteChallenge()) {
                return;
            }
            if (gardener.isOpenDoor()) {
                playerManager.setSeeds(player, 0);
                hubManager.enter(player);
                return;
            }
            setStage(player, FleetingStage.DARK);
            ServerWorld world = player.getServerWorld();
            sounds.playSound(world, player.getPosition(), thunderSounds, 1f);
            world.getEntities()
                    .filter(entity -> entity.getPosition().distanceSq(player.getPosition()) < 1000 * 1000)
                    .filter(entity -> entity instanceof PickupEntity)
                    .map(entity -> (PickupEntity) entity)
                    .filter(p -> p.getVariant() == PickupEntity.VARIANT_STAR || p.getVariant() == PickupEntity.VARIANT_CHALLENGE)
                    .forEach(Entity::remove);
            BlockPos playerPosition = player.getPosition();
            BlockPos darknessLocation = playerPosition;
            if (world.getBlockState(playerPosition).getBlock() instanceof SpecialDecayEdgeBlock || world.getBlockState(playerPosition).getBlock() instanceof SpecialDecayBlock) {
                darknessLocation = darknessLocation.offset(Direction.NORTH); // Special case fix for when the player decides to dig directly down after entering
            }
            world.setBlockState(darknessLocation, darknessEdgeBlock.getDefaultState());
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
                sounds.playSound(player.getServerWorld(), player.getPosition(), bubbleSound, 1f);
            });
        }
    }

    private ServerWorld getSpawnWorld(ServerPlayerEntity player) {
        return DimensionManager.getWorld(player.getServer(), player.getCapability(gardenerCapability.get())
                .map(gardener -> gardener.getLevelType() == LevelType.TEAL ? fleetingSolidDimensionTemplate.getType() : fleetingDimensionTemplate.getType())
                .orElse(fleetingDimensionTemplate.getType()), false, true);
    }

    private void setupSpawnpoint(ServerPlayerEntity player, World world, BlockPos pos, Level level) {
        capabilities.gardener(player, gardener -> {
            for (int y = pos.getY() - 8; y < pos.getY(); y++) {
                for (int x = pos.getX() - 2; x <= pos.getX() + 2; x++) {
                    for (int z = pos.getZ() - 2; z <= pos.getZ() + 2; z++) {
                        world.setBlockState(new BlockPos(x, y, z), gardener.getLevelType() == LevelType.PURPLE ? orangeFormedBlockHard.getDefaultState() : orangeFormedBlock.getDefaultState());
                    }
                }
            }
            BlockPos door = new BlockPos(pos.getX(), pos.getY() - 8, pos.getZ());
            decayManager.createSpecialDecay(world, door, SpecialDecayType.DOOR);
            Optional.ofNullable((DecayEdgeTile) world.getTileEntity(door)).ifPresent(tile -> {
                if (gardener.getLevelType() == LevelType.WHITE) {
                    tile.setMultiplier(1.25);
                } else if (gardener.getLevelType() == LevelType.ORANGE) {
                    tile.setMultiplier(0.5);
                }
            });
            /*
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
             */
        });
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
