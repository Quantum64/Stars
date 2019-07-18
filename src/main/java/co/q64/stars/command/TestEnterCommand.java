package co.q64.stars.command;

import co.q64.stars.dimension.Dimensions;
import co.q64.stars.util.SpawnpointManager;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.DimensionManager;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TestEnterCommand {
    protected @Inject Dimensions dimensions;
    protected @Inject SpawnpointManager spawnpointManager;

    protected @Inject TestEnterCommand() {}

    public ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("testenter")
                .requires(cs -> cs.hasPermissionLevel(0))
                .executes(this::execute);
    }

    private int execute(CommandContext<CommandSource> context) {
        try {
            ServerPlayerEntity player = context.getSource().asPlayer();
            BlockPos pos = player.getPosition();
            BlockPos spawnpoint = spawnpointManager.getNext();
            player.teleport(DimensionManager.getWorld(player.getServer(), dimensions.getAdventureDimensionType(), false, true),
                    spawnpoint.getX(), spawnpoint.getY(), spawnpoint.getZ(), player.getYaw(1.0f), player.getPitch(1.0f));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
