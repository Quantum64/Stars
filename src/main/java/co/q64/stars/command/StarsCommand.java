package co.q64.stars.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class StarsCommand {
    protected @Inject TpxCommand tpxCommand;
    protected @Inject TestEnterCommand testEnterCommand;

    protected @Inject StarsCommand() {}

    public void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(LiteralArgumentBuilder.<CommandSource>literal("stars")
                .then(tpxCommand.register())
                .then(testEnterCommand.register()));
    }
}