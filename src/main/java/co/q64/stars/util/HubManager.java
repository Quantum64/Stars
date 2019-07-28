package co.q64.stars.util;

import co.q64.stars.block.GreyFormedBlock;
import co.q64.stars.capability.GardenerCapability;
import co.q64.stars.capability.HubCapability;
import co.q64.stars.dimension.Dimensions;
import co.q64.stars.net.PacketManager;
import co.q64.stars.net.packets.ClientFadePacket.FadeMode;
import co.q64.stars.type.FleetingStage;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class HubManager {
    protected @Inject Dimensions dimensions;
    protected @Inject PacketManager packetManager;
    protected @Inject SpawnpointManager spawnpointManager;
    protected @Inject FleetingManager fleetingManager;
    protected @Inject PlayerManager playerManager;
    protected @Inject GreyFormedBlock greyFormedBlock;
    protected @Inject Provider<Capability<HubCapability>> hubCapability;
    protected @Inject Provider<Capability<GardenerCapability>> gardenerCapability;

    protected @Inject HubManager() {}

    public void enter(ServerPlayerEntity player, boolean showEffect) {
        ServerWorld world = getWorld(player.getServer());
        world.getCapability(hubCapability.get()).ifPresent(hub -> {
            player.getCapability(gardenerCapability.get()).ifPresent(gardener -> {
                int index = gardener.getHubIndex();
                boolean setup = false;
                if (index == -1) {
                    index = hub.getNextIndex();
                    setup = true;
                    hub.setNextIndex(hub.getNextIndex() + 1);
                    gardener.setHubIndex(index);
                }
                BlockPos spawnpoint = spawnpointManager.getSpawnpoint(index);
                if (setup) {
                    setupSpawnpoint(world, spawnpoint);
                }
                player.setMotion(0, 0, 0);
                player.teleport(world, spawnpoint.getX() + 0.5, spawnpoint.getY(), spawnpoint.getZ() + 0.5, player.rotationYaw, player.rotationPitch);
                fleetingManager.setStage(player, FleetingStage.NONE);
                gardener.getNextSeeds().clear();
                playerManager.updateSeeds(player);
            });
        });
    }

    //TODO the biggest todo
    private void setupSpawnpoint(World world, BlockPos pos) {
        for (int y = pos.getY() - 8; y < pos.getY(); y++) {
            for (int x = pos.getX() - 2; x <= pos.getX() + 2; x++) {
                for (int z = pos.getZ() - 2; z <= pos.getZ() + 2; z++) {
                    world.setBlockState(new BlockPos(x, y, z), greyFormedBlock.getDefaultState());
                }
            }
        }
    }

    public ServerWorld getWorld(MinecraftServer server) {
        return DimensionManager.getWorld(server, dimensions.getHubDimensionType(), false, true);
    }
}
