package co.q64.stars.util;

import co.q64.stars.block.DoorBlock;
import co.q64.stars.capability.GardenerCapability;
import co.q64.stars.dimension.hub.HubDimension;
import co.q64.stars.net.PacketManager;
import co.q64.stars.qualifier.SoundQualifiers.Door;
import co.q64.stars.qualifier.SoundQualifiers.Seed;
import co.q64.stars.type.FleetingStage;
import co.q64.stars.type.FormingBlockType;
import co.q64.stars.type.forming.GreenFormingBlockType;
import co.q64.stars.type.forming.PinkFormingBlockType;
import co.q64.stars.type.forming.YellowFormingBlockType;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Singleton
public class PlayerManager {
    protected @Inject PacketManager packetManager;
    protected @Inject SeedManager seedManager;
    protected @Inject YellowFormingBlockType yellowFormingBlockType;
    protected @Inject GreenFormingBlockType greenFormingBlockType;
    protected @Inject PinkFormingBlockType pinkFormingBlockType;
    protected @Inject Provider<Capability<GardenerCapability>> gardenerCapability;
    protected @Inject Sounds sounds;
    protected @Inject @Door SoundEvent doorSound;
    protected @Inject @Seed Set<SoundEvent> seedSounds;
    protected @Inject Capabilities capabilities;

    private Queue<ServerPlayerEntity> toSync = new ConcurrentLinkedQueue<>();
    private Set<FormingBlockType> formingBlockTypes;
    private Set<FormingBlockType> hubFormingBlocks;

    protected @Inject PlayerManager() {}

    public int getSeeds(ServerPlayerEntity player) {
        return player.getCapability(gardenerCapability.get()).map(GardenerCapability::getSeeds).orElse(0);
    }

    public void setSeeds(ServerPlayerEntity player, final int lock) {
        capabilities.gardener(player, c -> {
            int seeds = lock;
            c.setSeeds(seeds);
        });
        syncCapability(player);
    }

    public void updateSeeds(ServerPlayerEntity player) {
        capabilities.gardener(player, c -> {
            int seeds = getSeeds(player);
            boolean hub = player.getServerWorld().getDimension() instanceof HubDimension || c.isEnteringHub();
            if (c.getFleetingStage() == FleetingStage.LIGHT || hub) {
                Set<FormingBlockType> types = hub ? hubFormingBlocks : formingBlockTypes;
                while (c.getNextSeeds().size() < c.getSeedVisibility() && seeds > 0) {
                    FormingBlockType offering = pinkFormingBlockType;
                    if (c.getSeedsSincePink() < 5 + ThreadLocalRandom.current().nextInt(2)) {
                        for (int i = 0; i < 50; i++) {
                            offering = types.stream().skip(ThreadLocalRandom.current().nextInt(types.size())).findFirst().orElseThrow(() -> new RuntimeException("It is impossible for this exception to be thrown."));
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

    public boolean shouldApplyJump(ServerPlayerEntity player) {
        return player.getCapability(gardenerCapability.get()).map(GardenerCapability::getLastJumped).orElse(0L) < System.currentTimeMillis() - 1000;
    }

    public void grow(ServerPlayerEntity player) {
        capabilities.gardener(player, c -> {
            if (c.getFleetingStage() != FleetingStage.DARK && !c.getNextSeeds().isEmpty()) {
                if (seedManager.tryGrow(player.getServerWorld(), player.getPosition().offset(Direction.DOWN), c.getNextSeeds().peek())) {
                    c.getNextSeeds().poll();
                    updateSeeds(player);
                }
            } else if (c.getFleetingStage() == FleetingStage.DARK) {
                c.setLastJumped(System.currentTimeMillis());
                if (c.getKeys() > 0) {
                    BlockPos target = player.getPosition().offset(Direction.DOWN);
                    Block block = player.getServerWorld().getBlockState(target).getBlock();
                    if (block instanceof DoorBlock) {
                        player.getServerWorld().setBlockState(target, Blocks.AIR.getDefaultState());
                        player.teleport(player.getServerWorld(), target.getX() + 0.5, target.getY() + 0.5, target.getZ() + 0.5, player.rotationYaw, player.rotationPitch);
                        player.setMotion(0, 0, 0);
                        sounds.playSound(player.getServerWorld(), target, doorSound, 1f);
                        capabilities.gardener(player, gardener -> {
                            gardener.setOpenDoor(true); // todo challenge door
                        });
                    }
                }
            }
        });
    }

    public void useSeed(ServerPlayerEntity player, Runnable action) {
        if (getSeeds(player) > 0) {
            setSeeds(player, getSeeds(player) - 1);
            action.run();
        }
    }

    public void addSeed(ServerPlayerEntity player) {
        setSeeds(player, getSeeds(player) + 1);
        updateSeeds(player);
    }

    public void pickupSeed(ServerPlayerEntity player) {
        addSeed(player);
        sounds.playSound(player.getServerWorld(), player.getPosition(), seedSounds, 1f);
    }

    public void syncCapability(ServerPlayerEntity player) {
        if (toSync.contains(player)) {
            return;
        }
        toSync.add(player);
    }

    public void tick() {
        while (true) {
            ServerPlayerEntity player = toSync.poll();
            if (player == null) {
                break;
            }
            capabilities.gardener(player, c -> packetManager.getChannel().send(PacketDistributor.PLAYER.with(() -> player), packetManager.getUpdateOverlayPacketFactory().create(c)));
        }
    }

    @Inject
    protected void setup(Set<FormingBlockType> types) {
        this.formingBlockTypes = types.stream().filter(FormingBlockType::canGrow).collect(Collectors.toSet());
        this.hubFormingBlocks = formingBlockTypes.stream().filter(type -> !(type instanceof PinkFormingBlockType)).collect(Collectors.toSet());
    }
}
