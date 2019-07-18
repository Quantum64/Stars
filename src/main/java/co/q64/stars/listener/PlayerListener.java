package co.q64.stars.listener;

import co.q64.stars.block.BaseBlock;
import co.q64.stars.block.BlueFormedBlock;
import co.q64.stars.block.DarkAirBlock;
import co.q64.stars.block.DarknessBlock;
import co.q64.stars.block.DarknessEdgeBlock;
import co.q64.stars.block.DecayBlock;
import co.q64.stars.block.DecayEdgeBlock;
import co.q64.stars.block.FormingBlock;
import co.q64.stars.dimension.AdventureDimension;
import co.q64.stars.tile.FormingTile;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.event.entity.EntityEvent.EyeHeight;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.EntityPlaceEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.Field;

@Singleton
public class PlayerListener implements Listener {
    private static final double TOLERANCE = 0.15;
    private static final Direction[] DIRECTIONS = Direction.values();

    protected @Inject DarkAirBlock darkAirBlock;
    protected @Inject DecayBlock decayBlock;
    protected @Inject DecayEdgeBlock decayEdgeBlock;
    protected @Inject DarknessEdgeBlock darknessEdgeBlock;

    private EntitySize size = new EntitySize(0.6f, 0.5f, false);
    private Field sizeField;

    @Inject
    protected PlayerListener() {
        for (Field field : Entity.class.getDeclaredFields()) {
            if (field.getType().isAssignableFrom(EntitySize.class)) {
                if (sizeField != null) {
                    throw new IllegalStateException("Too many size fields");
                }
                sizeField = field;
            }
        }
        sizeField.setAccessible(true);
    }

    @SubscribeEvent
    public void onPlayerLoad(PlayerEvent.LoadFromFile event) {
        if (event.getEntityPlayer().dimension == null) {
            // In unregistered dimension
            // TODO
            event.getEntityPlayer().dimension = DimensionType.OVERWORLD;
        }
    }

    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent event) throws IllegalAccessException {
        PlayerEntity entity = event.player;
        if (entity.getEntityWorld().getDimension() instanceof AdventureDimension) {
            if (entity.getBoundingBox().getYSize() < size.height - 0.02 || entity.getBoundingBox().getYSize() > size.height + 0.02) {
                sizeField.set(entity, size);
                AxisAlignedBB aabb = entity.getBoundingBox();
                entity.setBoundingBox(new AxisAlignedBB(aabb.minX, aabb.minY, aabb.minZ, aabb.minX + size.width, aabb.minY + size.height, aabb.minZ + size.width));
            }
        }
        if (event.side == LogicalSide.SERVER) {
            World world = event.player.getEntityWorld();
            if (world.getDimension() instanceof AdventureDimension) {
                ServerPlayerEntity player = (ServerPlayerEntity) event.player;
                boolean hasNoJump = false;
                for (EffectInstance instance : player.getActivePotionEffects()) {
                    if (instance.getPotion() == Effects.JUMP_BOOST && instance.getAmplifier() == -50) {
                        hasNoJump = true;
                        break;
                    }
                }
                if (!hasNoJump) {
                    Block block = entity.getEntityWorld().getBlockState(entity.getPosition().offset(Direction.DOWN)).getBlock();
                    player.removePotionEffect(Effects.JUMP_BOOST);
                    if (block instanceof DarknessBlock) {
                        player.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 10000000, -50, true, false));
                    } else if (block instanceof BlueFormedBlock) {
                        player.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 2, 9, true, false));
                    } else {
                        player.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 2, 3, true, false));
                    }
                }

                Block currentBlock = world.getBlockState(player.getPosition()).getBlock();
                if (currentBlock == decayBlock || currentBlock == decayEdgeBlock) {
                    double x = player.posX, y = player.posY, z = player.posZ;
                    double offsetX = x - Math.floor(x);
                    double offsetY = y - Math.floor(y);
                    double offsetZ = z - Math.floor(z);
                    boolean inToleranceMinusX = offsetX > TOLERANCE, inTolerancePlusX = offsetX < 1 - TOLERANCE;
                    boolean inToleranceMinusY = offsetY > TOLERANCE, inTolerancePlusY = offsetY < 1 - TOLERANCE;
                    boolean inToleranceMinusZ = offsetZ > TOLERANCE, inTolerancePlusZ = offsetZ < 1 - TOLERANCE;
                    for (Direction direction : DIRECTIONS) {
                        Block offsetBlock = world.getBlockState(player.getPosition().offset(direction)).getBlock();
                        if (offsetBlock == decayBlock || offsetBlock == decayEdgeBlock) {
                            switch (direction.getAxis()) {
                                case X:
                                    switch (direction.getAxisDirection()) {
                                        case POSITIVE:
                                            inTolerancePlusX = true;
                                            break;
                                        case NEGATIVE:
                                            inToleranceMinusX = true;
                                            break;
                                    }
                                    break;
                                case Y:
                                    switch (direction.getAxisDirection()) {
                                        case POSITIVE:
                                            inTolerancePlusY = true;
                                            break;
                                        case NEGATIVE:
                                            inToleranceMinusY = true;
                                            break;
                                    }
                                    break;
                                case Z:
                                    switch (direction.getAxisDirection()) {
                                        case POSITIVE:
                                            inTolerancePlusZ = true;
                                            break;
                                        case NEGATIVE:
                                            inToleranceMinusZ = true;
                                            break;
                                    }
                                    break;
                            }
                        }
                    }
                    if (inToleranceMinusX && inTolerancePlusX && inToleranceMinusY && inTolerancePlusY && inToleranceMinusZ && inTolerancePlusZ) {
                        BlockPos playerPosition = player.getPosition();
                        world.setBlockState(playerPosition, darknessEdgeBlock.getDefaultState());
                        player.teleport((ServerWorld) world, playerPosition.getX() + 0.5, playerPosition.getY() + 0.1, playerPosition.getZ() + 0.5, player.rotationYaw, player.rotationPitch);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityDamage(LivingDamageEvent event) {
        if (event.getEntity().getEntityWorld().getDimension() instanceof AdventureDimension) {
            event.setAmount(0);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onBlockBreak(BreakEvent event) {
        IWorld world = event.getWorld();
        if (world.getDimension() instanceof AdventureDimension) {
            world.setBlockState(event.getPos(), darkAirBlock.getDefaultState(), 3);
            event.setCanceled(true);
            if (!world.isRemote()) {
                for (Direction direction : DIRECTIONS) {
                    if (world.getBlockState(event.getPos().offset(direction)).getBlock() instanceof FormingBlock) {
                        FormingTile tile = (FormingTile) world.getTileEntity(event.getPos().offset(direction));
                        if (tile != null) {
                            if (tile.getDirection() == direction) {
                                world.setBlockState(event.getPos().offset(direction), Blocks.AIR.getDefaultState(), 3);
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onBlockPlace(EntityPlaceEvent event) {
        if (event.getWorld().getDimension() instanceof AdventureDimension) {
            if (!(event.getPlacedBlock().getBlock() instanceof BaseBlock)) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerChangeDimension(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            if (event.getWorld().getDimension() instanceof AdventureDimension) {
            }
        }
    }

    @SubscribeEvent
    public void onEyeHeight(EyeHeight event) throws IllegalAccessException {
        if (event.getEntity() instanceof PlayerEntity) {
            PlayerEntity entity = (PlayerEntity) event.getEntity();
            if (event.getEntity().getEntityWorld().getDimension() instanceof AdventureDimension) {
                event.setNewHeight(0.5f);
            }
        }
    }
}
