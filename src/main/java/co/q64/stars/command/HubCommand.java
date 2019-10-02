package co.q64.stars.command;

import co.q64.stars.capability.GardenerCapability;
import co.q64.stars.capability.HubCapability;
import co.q64.stars.dimension.hub.HubDimension;
import co.q64.stars.util.Capabilities;
import co.q64.stars.util.HubManager;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import jdk.nashorn.internal.ir.Block;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class HubCommand {
    protected @Inject HubManager hubManager;
    protected @Inject Capabilities capabilities;

    protected @Inject HubCommand() {}

    public ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("hub")
                .requires(cs -> cs.hasPermissionLevel(2))
                .then(Commands.literal("setnext").then(Commands.argument("id", IntegerArgumentType.integer()).executes(this::setNext)))
                .then(Commands.literal("update").then(Commands.argument("player", EntityArgument.player()).then(Commands.argument("id", IntegerArgumentType.integer()).executes(this::update))))
                .then(Commands.literal("info").then(Commands.argument("player", EntityArgument.player()).executes(this::info)))
                .then(Commands.literal("enter").executes(this::enter));
    }

    private int setNext(CommandContext<CommandSource> context) {
        int id = IntegerArgumentType.getInteger(context, "id");
        for (ServerWorld world : context.getSource().getServer().getWorlds()) {
            if (world.getDimension() instanceof HubDimension) {
                capabilities.hub(world, hub -> {
                    hub.setNextIndex(id);
                    context.getSource().sendFeedback(new StringTextComponent("Next hub index for world '" + world.getDimension().getType().getRegistryName() + "' updated to " + id + "."), true);
                });
            }
        }
        return 0;
    }

    private int update(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = EntityArgument.getPlayer(context, "player");
        int id = IntegerArgumentType.getInteger(context, "id");
        capabilities.gardener(player, gardener -> {
            gardener.setHubIndex(id);
            context.getSource().sendFeedback(new StringTextComponent("Updated hub index for '" + player.getName().getFormattedText() + "' to " + id + "."), true);
        });
        return 0;
    }

    private int info(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = EntityArgument.getPlayer(context, "player");
        capabilities.gardener(player, gardener -> {
            context.getSource().sendFeedback(new StringTextComponent("Hub index for '" + player.getName().getFormattedText() + "' is " + gardener.getHubIndex() + "."), true);
        });
        return 0;
    }

    private int enter(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().asPlayer();
        capabilities.gardener(player, gardener -> {
            if (gardener.getHubEntryPosition().equals(BlockPos.ZERO)) {
                gardener.setHubEntryPosition(player.getPosition());
            }
        });
        hubManager.enter(player);
        return 0;
    }
}
