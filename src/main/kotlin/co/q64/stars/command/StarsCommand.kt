package co.q64.stars.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.minecraft.command.CommandSource

fun CommandDispatcher<CommandSource>.stars() {
    register(LiteralArgumentBuilder.literal<CommandSource>("stars")
            .then(tpxCommand)
            .then(enterCommand.register())
            .then(hubCommand.register())
            .then(challengeCommand.register()))
}