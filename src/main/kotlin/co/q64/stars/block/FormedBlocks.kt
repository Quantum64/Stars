package co.q64.stars.block

import net.minecraft.block.Block
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material

private val earth = Block.Properties.create(Material.EARTH).sound(SoundType.GROUND).hardnessAndResistance(0f, 0f)
private val hard = Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(-1f, 3600000f)

sealed class FormedBlock(id: String, properties: Properties = earth) : BaseBlock(id, properties)
object BlueFormedBlock : FormedBlock("blue_formed")
object BrownFormedBlock : FormedBlock("brown_formed")
object CyanFormedBlock : FormedBlock("cyan_formed")
object GreenFormedBlock : FormedBlock("green_formed")
object GreenFruitBlock : FormedBlock("green_fruit")
object OrangeFormedBlock : FormedBlock("orange_formed")
object PinkFormedBlock : FormedBlock("pink_formed")
object PurpleFormedBlock : FormedBlock("purple_formed")
object RedFormedBlock : FormedBlock("red_formed")
object RedPrimedBlock : FormedBlock("red_primed")
object TealFormedBlock : FormedBlock("teal_formed")
object WhiteFormedBlock : FormedBlock("white_formed")
object YellowFormedBlock : FormedBlock("yellow_formed")

sealed class FormedBlockHard(id: String, properties: Properties = hard) : FormedBlock(id, properties), HardBlock
object GreyFormedBlock : FormedBlockHard("grey_formed")
object BlueFormedBlockHard : FormedBlockHard("blue_formed_hard")
object BrownFormedBlockHard : FormedBlockHard("brown_formed_hard")
object CyanFormedBlockHard : FormedBlockHard("cyan_formed_hard")
object GreenFormedBlockHard : FormedBlockHard("green_formed_hard")
object GreenFruitBlockHard : FormedBlockHard("green_fruit_hard")
object OrangeFormedBlockHard : FormedBlockHard("orange_formed_hard")
object PurpleFormedBlockHard : FormedBlockHard("purple_formed_hard")
object RedFormedBlockHard : FormedBlockHard("red_formed_hard")
object RedPrimedBlockHard : FormedBlockHard("red_primed_hard")
object YellowFormedBlockHard : FormedBlockHard("yellow_formed_hard")