package co.q64.stars.util;

import co.q64.stars.block.DarknessEdgeBlock;
import co.q64.stars.block.OrangeFormedBlock;
import co.q64.stars.block.SpecialDecayBlock;
import co.q64.stars.block.SpecialDecayEdgeBlock;
import co.q64.stars.dimension.Dimensions;
import co.q64.stars.net.PacketManager;
import co.q64.stars.net.packets.PlayClientEffectPacket.ClientEffectType;
import co.q64.stars.tile.SpecialDecayEdgeTile;
import co.q64.stars.tile.SpecialDecayEdgeTile.SpecialDecayType;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Singleton
public class EntryManager {
    private static final int SPREAD_DISTANCE = 2000;

    protected @Inject Dimensions dimensions;
    protected @Inject OrangeFormedBlock orangeFormedBlock;
    protected @Inject SpecialDecayEdgeBlock specialDecayEdgeBlock;
    protected @Inject DarknessEdgeBlock darknessEdgeBlock;
    protected @Inject PacketManager packetManager;

    private Map<UUID, AdventureStage> stages = new HashMap<>();
    private int index = 0;

    protected @Inject EntryManager() {}

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
        stages.put(player.getUniqueID(), AdventureStage.LIGHT);
    }

    public AdventureStage getStage(ServerPlayerEntity player) {
        return stages.getOrDefault(player.getUniqueID(), AdventureStage.NONE);
    }

    public void clearStage(ServerPlayerEntity player) {
        stages.remove(player.getUniqueID());
    }

    public void updateJumpStatus(ServerPlayerEntity player, boolean jumping) {
        if (getStage(player) == AdventureStage.DARK) {
            // TODO check
            if (jumping) {
                player.addPotionEffect(new EffectInstance(Effects.LEVITATION, 200, 1, true, false));
            } else {
                player.removePotionEffect(Effects.LEVITATION);
            }
        }
    }

    public void createDarkness(ServerPlayerEntity player) {
        stages.put(player.getUniqueID(), AdventureStage.DARK);
        World world = player.getServerWorld();
        BlockPos playerPosition = player.getPosition();
        packetManager.getChannel().send(PacketDistributor.PLAYER.with(() -> player), packetManager.getPlayClientEffectPacketFactory().create(ClientEffectType.DARKNESS));
        world.setBlockState(playerPosition, darknessEdgeBlock.getDefaultState());
        player.teleport((ServerWorld) world, playerPosition.getX() + 0.5, playerPosition.getY() + 0.1, playerPosition.getZ() + 0.5, player.rotationYaw, player.rotationPitch);
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
        SpecialDecayEdgeTile tile = (SpecialDecayEdgeTile) world.getTileEntity(door);
    }

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

    public static enum AdventureStage {
        LIGHT, DARK, NONE
    }
}
