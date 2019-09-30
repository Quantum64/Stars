package co.q64.stars.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.DimensionArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TpxCommand {
    protected @Inject TpxCommand() {}

    public ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("tpx")
                .requires(cs -> cs.hasPermissionLevel(2))
                .then(Commands.argument("dim", DimensionArgument.getDimension()).executes(this::execute));
    }

    private int execute(CommandContext<CommandSource> context) throws CommandSyntaxException {
        DimensionType dim = DimensionArgument.func_212592_a(context, "dim");
        ServerPlayerEntity player = context.getSource().asPlayer();
        BlockPos pos = player.getPosition();
        player.teleport(DimensionManager.getWorld(player.getServer(), dim, false, true), pos.getX(), pos.getY(), pos.getZ(), player.getYaw(1.0f), player.getPitch(1.0f));
        return 0;
    }
}
