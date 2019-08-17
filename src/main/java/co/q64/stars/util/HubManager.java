package co.q64.stars.util;

import co.q64.stars.dimension.hub.HubDimension.HubDimensionTemplate;
import co.q64.stars.level.LevelType;
import co.q64.stars.net.PacketManager;
import co.q64.stars.net.packets.ClientFadePacket.FadeMode;
import co.q64.stars.type.FleetingStage;
import co.q64.stars.util.Structures.StructureType;
import net.minecraft.block.Block;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class HubManager {
    protected @Inject PacketManager packetManager;
    protected @Inject SpawnpointManager spawnpointManager;
    protected @Inject PlayerManager playerManager;
    protected @Inject Scheduler scheduler;
    protected @Inject Capabilities capabilities;
    protected @Inject Structures structures;
    protected @Inject HubDimensionTemplate hubDimensionTemplate;
    protected @Inject Logger logger;

    protected @Inject HubManager() {}

    public void fall(ServerPlayerEntity player) {
        capabilities.gardener(player, gardener -> {
            if (gardener.isOpenDoor() || gardener.isOpenChallengeDoor()) {
                // TODO sound
            } else {

            }
            enter(player);
        });
    }

    public void lost(ServerPlayerEntity player) {
        // TODO sound
        enterNoFade(player, 0);
    }

    public void enter(ServerPlayerEntity player) {
        capabilities.gardener(player, gardener -> {
            if (gardener.isOpenDoor() || gardener.isOpenChallengeDoor()) {

            } else {
                // TODO sound
            }
            packetManager.getChannel().send(PacketDistributor.PLAYER.with(() -> player), packetManager.getClientFadePacketFactory().create(FadeMode.FADE_TO_WHITE, 3000));
            player.addPotionEffect(new EffectInstance(Effects.LEVITATION, 60, 1, true, false));
            enterNoFade(player, 60);
        });
    }

    private void enterNoFade(ServerPlayerEntity player, int delay) {
        ServerWorld spawnWorld = getWorld(player.getServer());
        capabilities.hub(spawnWorld, hub -> {
            capabilities.gardener(player, gardener -> {
                gardener.setLevelType(LevelType.WHITE); // No side effects
                int index = gardener.getHubIndex();
                boolean setup = false;

                int cIndex = index, cHub = hub.getNextIndex();
                long start = System.currentTimeMillis();

                gardener.setEnteringHub(true);
                if (index == -1) {
                    index = hub.getNextIndex();
                    setup = true;
                    hub.setNextIndex(hub.getNextIndex() + 1);
                    gardener.setHubIndex(index);
                    gardener.setHubSpawn(BlockPos.ZERO);
                }
                BlockPos spawnpoint = spawnpointManager.getSpawnpoint(index);
                if (setup) {
                    setupSpawnpoint(spawnWorld, spawnpoint);
                    logger.info("Setup hub for player '" + player.getName().getFormattedText() + "' in " + (System.currentTimeMillis() - start) + " ms " +
                            "(Previous player index: " + cIndex + ", Previous hub index: " + cHub + ", New player index: " + gardener.getHubIndex() +
                            ", New hub index: " + hub.getNextIndex() + ")");
                }
                if (gardener.getHubSpawn().equals(BlockPos.ZERO)) {
                    gardener.setHubSpawn(spawnpoint);
                }
                scheduler.run(() -> {
                    ServerWorld world = getWorld(player.getServer());
                    BlockPos hubSpawn = gardener.getHubSpawn();
                    player.setMotion(0, 0, 0);
                    player.teleport(world, hubSpawn.getX() + 0.5, hubSpawn.getY() + 5, hubSpawn.getZ() + 0.5, player.rotationYaw, player.rotationPitch);
                    capabilities.gardener(player, c -> {
                        c.setFleetingStage(FleetingStage.NONE);
                    });
                    playerManager.syncCapability(player);
                    gardener.getNextSeeds().clear();
                    if (!(gardener.isOpenDoor() || gardener.isOpenChallengeDoor())) {
                        gardener.setSeeds(0);
                    }
                    playerManager.updateSeeds(player);
                    player.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING, 60, 3, true, false));
                    gardener.setEnteringHub(false);
                    gardener.setOpenDoor(false);
                    gardener.setOpenChallengeDoor(false);
                    playerManager.syncCapability(player);
                    packetManager.getChannel().send(PacketDistributor.PLAYER.with(() -> player), packetManager.getClientFadePacketFactory().create(FadeMode.FADE_FROM_WHITE, 3000));
                }, delay);
            });
        });
    }

    private void setupSpawnpoint(World world, BlockPos spawn) {
        structures.get(StructureType.HUB_WHITE).place(world, spawn.add(-2, -2, -2), true);
        structures.get(StructureType.HUB_GREEN).place(world, spawn.add(18, 2, -12), true);
        structures.get(StructureType.HUB_ORANGE).place(world, spawn.add(48, 2, 15), true);
        structures.get(StructureType.HUB_PURPLE).place(world, spawn.add(45, -16, -20), true);
        structures.get(StructureType.HUB_BLUE).place(world, spawn.add(-42, 0, -40), true);
        structures.get(StructureType.HUB_YELLOW).place(world, spawn.add(-42, 30, -70), true);
        structures.get(StructureType.HUB_TEAL).place(world, spawn.add(-65, 10, -85), true);
        structures.get(StructureType.HUB_PINK).place(world, spawn.add(-22, 0, 30), true);
        structures.get(StructureType.HUB_RED).place(world, spawn.add(80, -11, 0), true);
    }

    private void createCube(World world, Block block, BlockPos start, BlockPos end) {
        for (int x = start.getX(); x <= end.getX(); x++) {
            for (int y = start.getY(); y <= end.getY(); y++) {
                for (int z = start.getZ(); z <= end.getZ(); z++) {
                    world.setBlockState(new BlockPos(x, y, z), block.getDefaultState(), 0);
                }
            }
        }
    }

    public ServerWorld getWorld(MinecraftServer server) {
        return DimensionManager.getWorld(server, hubDimensionTemplate.getType(), false, true);
    }
}
