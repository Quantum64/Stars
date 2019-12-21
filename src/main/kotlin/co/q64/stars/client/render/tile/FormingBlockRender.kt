package co.q64.stars.client.render.tile

import co.q64.stars.tile.FormingTile
import co.q64.stars.type.FormingBlockType
import com.mojang.blaze3d.platform.GlStateManager
import net.minecraft.client.renderer.BufferBuilder
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.tileentity.TileEntityRenderer
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.util.Direction
import org.lwjgl.opengl.GL11


object FormingBlockRender : TileEntityRenderer<FormingTile>() {
    private const val width = 0.0625f

    override fun render(tile: FormingTile, x: Double, y: Double, z: Double, partialTicks: Float, destroyStage: Int) {
        if (!tile.data.ready) {
            return
        }
        val type: FormingBlockType = tile.data.type
        val direction: Direction = tile.data.direction
        val buildTime: Int = tile.data.buildTime
        var timeSincePlace: Float = (System.currentTimeMillis() - tile.data.placed).toFloat()
        if (timeSincePlace > buildTime) {
            timeSincePlace = buildTime.toFloat()
        }
        val progress = timeSincePlace / buildTime
        var bottomCapProgress = progress * 5
        var sideProgress = (progress - 0.2f) * 1.666f
        var topCapProgress = (progress - 0.8f) * 5
        bottomCapProgress = if (bottomCapProgress > 1) 1f else bottomCapProgress
        sideProgress = if (sideProgress > 1) 1f else sideProgress
        topCapProgress = if (topCapProgress > 1) 1f else topCapProgress
        GlStateManager.disableLighting()
        GlStateManager.disableCull()
        GlStateManager.enableBlend()
        GlStateManager.disableTexture()
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO)
        GlStateManager.color4f(type.red / 256f, type.green / 256f, type.blue / 256f, 1f)
        GlStateManager.polygonOffset(-1f, -1f)
        GlStateManager.enablePolygonOffset()
        GlStateManager.pushMatrix()
        GlStateManager.translated(x, y, z)
        when (direction) {
            Direction.DOWN -> {
                GlStateManager.translated(0.0, 1.0, 1.0)
                GlStateManager.rotated(180.0, 1.0, 0.0, 0.0)
            }
            Direction.NORTH -> {
                GlStateManager.translated(0.0, 0.0, 1.0)
                GlStateManager.rotated(90.0, -1.0, 0.0, 0.0)
            }
            Direction.SOUTH -> {
                GlStateManager.translated(0.0, 1.0, 0.0)
                GlStateManager.rotated(90.0, 1.0, 0.0, 0.0)
            }
            Direction.WEST -> {
                GlStateManager.translated(1.0, 0.0, 0.0)
                GlStateManager.rotated(90.0, 0.0, 0.0, 1.0)
            }
            Direction.EAST -> {
                GlStateManager.translated(0.0, 1.0, 0.0)
                GlStateManager.rotated(90.0, 0.0, 0.0, -1.0)
            }
            Direction.UP -> {
            }
        }
        val tessellator = Tessellator.getInstance()
        val builder = tessellator.buffer
        builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION)
        renderBottomCap(builder, bottomCapProgress)
        if (sideProgress > 0) {
            renderSides(builder, sideProgress)
        }
        if (topCapProgress > 0) {
            renderTopCap(builder, topCapProgress)
        }
        tessellator.draw()
        GlStateManager.popMatrix()
        GlStateManager.enableCull()
        GlStateManager.enableTexture()
        GlStateManager.enableLighting()
        GlStateManager.disableBlend()
        GlStateManager.disablePolygonOffset()
    }

    private fun renderSides(builder: BufferBuilder, progress: Float) = with(builder) {
        pos(0.0, 0.0, 0.0).endVertex()
        pos(width.toDouble(), 0.0, 0.0).endVertex()
        pos(width.toDouble(), progress.toDouble(), 0.0).endVertex()
        pos(0.0, progress.toDouble(), 0.0).endVertex()
        pos(0.0, 0.0, 0.0).endVertex()
        pos(0.0, 0.0, width.toDouble()).endVertex()
        pos(0.0, progress.toDouble(), width.toDouble()).endVertex()
        pos(0.0, progress.toDouble(), 0.0).endVertex()
        pos(1.0, 0.0, 0.0).endVertex()
        pos(1 - width.toDouble(), 0.0, 0.0).endVertex()
        pos(1 - width.toDouble(), progress.toDouble(), 0.0).endVertex()
        pos(1.0, progress.toDouble(), 0.0).endVertex()
        pos(1.0, 0.0, 0.0).endVertex()
        pos(1.0, 0.0, width.toDouble()).endVertex()
        pos(1.0, progress.toDouble(), width.toDouble()).endVertex()
        pos(1.0, progress.toDouble(), 0.0).endVertex()
        pos(0.0, 0.0, 1.0).endVertex()
        pos(width.toDouble(), 0.0, 1.0).endVertex()
        pos(width.toDouble(), progress.toDouble(), 1.0).endVertex()
        pos(0.0, progress.toDouble(), 1.0).endVertex()
        pos(0.0, 0.0, 1.0).endVertex()
        pos(0.0, 0.0, 1 - width.toDouble()).endVertex()
        pos(0.0, progress.toDouble(), 1 - width.toDouble()).endVertex()
        pos(0.0, progress.toDouble(), 1.0).endVertex()
        pos(1.0, 0.0, 1.0).endVertex()
        pos(1 - width.toDouble(), 0.0, 1.0).endVertex()
        pos(1 - width.toDouble(), progress.toDouble(), 1.0).endVertex()
        pos(1.0, progress.toDouble(), 1.0).endVertex()
        pos(1.0, 0.0, 1.0).endVertex()
        pos(1.0, 0.0, 1 - width.toDouble()).endVertex()
        pos(1.0, progress.toDouble(), 1 - width.toDouble()).endVertex()
        pos(1.0, progress.toDouble(), 1.0).endVertex()
    }

    private fun renderBottomCap(builder: BufferBuilder, progress: Float) = with(builder) {
        pos(0.0, 0.0, 0.0).endVertex()
        pos(width.toDouble(), 0.0, 0.0).endVertex()
        pos(width.toDouble(), 0.0, progress.toDouble()).endVertex()
        pos(0.0, 0.0, progress.toDouble()).endVertex()
        pos(0.0, 0.0, 0.0).endVertex()
        pos(0.0, width.toDouble(), 0.0).endVertex()
        pos(0.0, width.toDouble(), progress.toDouble()).endVertex()
        pos(0.0, 0.0, progress.toDouble()).endVertex()
        pos(1.0, 0.0, 1.0).endVertex()
        pos(1 - width.toDouble(), 0.0, 1.0).endVertex()
        pos(1 - width.toDouble(), 0.0, 1 - progress.toDouble()).endVertex()
        pos(1.0, 0.0, 1 - progress.toDouble()).endVertex()
        pos(1.0, 0.0, 1.0).endVertex()
        pos(1.0, width.toDouble(), 1.0).endVertex()
        pos(1.0, width.toDouble(), 1 - progress.toDouble()).endVertex()
        pos(1.0, 0.0, 1 - progress.toDouble()).endVertex()
        pos(0.0, 0.0, 1.0).endVertex()
        pos(0.0, width.toDouble(), 1.0).endVertex()
        pos(progress.toDouble(), width.toDouble(), 1.0).endVertex()
        pos(progress.toDouble(), 0.0, 1.0).endVertex()
        pos(0.0, 0.0, 1.0).endVertex()
        pos(0.0, 0.0, 1 - width.toDouble()).endVertex()
        pos(progress.toDouble(), 0.0, 1 - width.toDouble()).endVertex()
        pos(progress.toDouble(), 0.0, 1.0).endVertex()
        pos(1.0, 0.0, 0.0).endVertex()
        pos(1.0, width.toDouble(), 0.0).endVertex()
        pos(1 - progress.toDouble(), width.toDouble(), 0.0).endVertex()
        pos(1 - progress.toDouble(), 0.0, 0.0).endVertex()
        pos(1.0, 0.0, 0.0).endVertex()
        pos(1.0, 0.0, width.toDouble()).endVertex()
        pos(1 - progress.toDouble(), 0.0, width.toDouble()).endVertex()
        pos(1 - progress.toDouble(), 0.0, 0.0).endVertex()
    }

    private fun renderTopCap(builder: BufferBuilder, progress: Float) = with(builder) {
        pos(0.0, 1.0, 0.0).endVertex()
        pos(width.toDouble(), 1.0, 0.0).endVertex()
        pos(width.toDouble(), 1.0, progress.toDouble()).endVertex()
        pos(0.0, 1.0, progress.toDouble()).endVertex()
        pos(0.0, 1.0, 0.0).endVertex()
        pos(0.0, 1 - width.toDouble(), 0.0).endVertex()
        pos(0.0, 1 - width.toDouble(), progress.toDouble()).endVertex()
        pos(0.0, 1.0, progress.toDouble()).endVertex()
        pos(1.0, 1.0, 1.0).endVertex()
        pos(1 - width.toDouble(), 1.0, 1.0).endVertex()
        pos(1 - width.toDouble(), 1.0, 1 - progress.toDouble()).endVertex()
        pos(1.0, 1.0, 1 - progress.toDouble()).endVertex()
        pos(1.0, 1.0, 1.0).endVertex()
        pos(1.0, 1 - width.toDouble(), 1.0).endVertex()
        pos(1.0, 1 - width.toDouble(), 1 - progress.toDouble()).endVertex()
        pos(1.0, 1.0, 1 - progress.toDouble()).endVertex()
        pos(0.0, 1.0, 1.0).endVertex()
        pos(0.0, 1 - width.toDouble(), 1.0).endVertex()
        pos(progress.toDouble(), 1 - width.toDouble(), 1.0).endVertex()
        pos(progress.toDouble(), 1.0, 1.0).endVertex()
        pos(0.0, 1.0, 1.0).endVertex()
        pos(0.0, 1.0, 1 - width.toDouble()).endVertex()
        pos(progress.toDouble(), 1.0, 1 - width.toDouble()).endVertex()
        pos(progress.toDouble(), 1.0, 1.0).endVertex()
        pos(1.0, 1.0, 0.0).endVertex()
        pos(1.0, 1 - width.toDouble(), 0.0).endVertex()
        pos(1 - progress.toDouble(), 1 - width.toDouble(), 0.0).endVertex()
        pos(1 - progress.toDouble(), 1.0, 0.0).endVertex()
        pos(1.0, 1.0, 0.0).endVertex()
        pos(1.0, 1.0, width.toDouble()).endVertex()
        pos(1 - progress.toDouble(), 1.0, width.toDouble()).endVertex()
        pos(1 - progress.toDouble(), 1.0, 0.0).endVertex()
    }
}