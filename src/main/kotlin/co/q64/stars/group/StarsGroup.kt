package co.q64.stars.group

import co.q64.stars.item.BlueSeedItem
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack

import net.minecraft.util.NonNullList

import net.minecraftforge.api.distmarker.Dist

import net.minecraftforge.api.distmarker.OnlyIn
import java.util.function.Consumer

object StarsGroup : ItemGroup("stars") {
    @OnlyIn(Dist.CLIENT)
    override fun createIcon(): ItemStack? {
        return ItemStack(BlueSeedItem)
    }

    override fun fill(items: NonNullList<ItemStack>) {
        super.fill(items)
        println("STARS GROUP: ")
        items.forEach(Consumer { i: ItemStack -> println(i.item.registryName.toString()) })
    }
}