package co.q64.stars.command

import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.CommandSyntaxException
import net.minecraft.command.CommandSource
import net.minecraft.command.Commands
import net.minecraft.command.arguments.DimensionArgument
import net.minecraftforge.common.DimensionManager

val tpxCommand = Commands.literal("tpx")
        .requires { it.hasPermissionLevel(2) }
        .then(Commands.argument("dim", DimensionArgument.getDimension()).executes(::execute));

@Throws(CommandSyntaxException::class)
private fun execute(context: CommandContext<CommandSource>): Int {
    val dim = DimensionArgument.func_212592_a(context, "dim")
    val player = context.source.asPlayer()
    val pos = player.position
    player.teleport(DimensionManager.getWorld(player.getServer(), dim, false, true), pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), player.getYaw(1.0f), player.getPitch(1.0f))
    return 0
}