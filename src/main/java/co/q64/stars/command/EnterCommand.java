package co.q64.stars.command;

import co.q64.stars.dimension.Dimensions;
import co.q64.stars.util.FleetingManager;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class EnterCommand {
    protected @Inject Dimensions dimensions;
    protected @Inject FleetingManager spawnpointManager;

    protected @Inject EnterCommand() {}

    public ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("enter")
                .requires(cs -> cs.hasPermissionLevel(0))
                .then(Commands.argument("effect", BoolArgumentType.bool()).executes(this::execute))
                .executes(this::execute);
    }

    private int execute(CommandContext<CommandSource> context) throws CommandSyntaxException {
        boolean effect = false;
        try {
            effect = BoolArgumentType.getBool(context, "effect");
        } catch (IllegalArgumentException e) {}
        ServerPlayerEntity player = context.getSource().asPlayer();
        spawnpointManager.enter(player, effect);
        return 0;
    }
}
