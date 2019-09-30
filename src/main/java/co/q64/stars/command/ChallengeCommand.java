package co.q64.stars.command;

import co.q64.stars.level.LevelType;
import co.q64.stars.util.Capabilities;
import co.q64.stars.util.FleetingManager;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.server.command.EnumArgument;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ChallengeCommand {
    protected @Inject FleetingManager fleetingManager;
    protected @Inject Capabilities capabilities;

    protected @Inject ChallengeCommand() {}

    public ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("challenge")
                .requires(cs -> cs.hasPermissionLevel(2))
                .then(Commands.argument("level", EnumArgument.enumArgument(LevelType.class)).executes(this::execute));
    }

    private int execute(CommandContext<CommandSource> context) throws CommandSyntaxException {
        LevelType level = LevelType.WHITE;
        try {
            level = context.getArgument("level", LevelType.class);
        } catch (IllegalArgumentException e) {}
        ServerPlayerEntity player = context.getSource().asPlayer();
        final LevelType lock = level;
        capabilities.gardener(player, gardener -> {
            gardener.setEnteringFleeting(false);
            gardener.setOpenChallengeDoor(true);
            gardener.setOpenDoor(false);
            gardener.setLevelType(lock);
            gardener.setSeeds(25);
            fleetingManager.enter(player, false);
        });
        return 0;
    }
}
