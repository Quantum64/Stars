package co.q64.stars.listener;

import co.q64.stars.block.BaseBlock;
import co.q64.stars.block.BlueFormedBlock;
import co.q64.stars.block.BrownFormedBlock;
import co.q64.stars.block.DarkAirBlock;
import co.q64.stars.block.FormedBlock;
import co.q64.stars.block.FormingBlock;
import co.q64.stars.block.GreyFormedBlock;
import co.q64.stars.block.PinkFormedBlock;
import co.q64.stars.block.RedPrimedBlock;
import co.q64.stars.block.SeedBlock;
import co.q64.stars.capability.gardener.GardenerCapabilityProvider;
import co.q64.stars.capability.hub.HubCapabilityProvider;
import co.q64.stars.dimension.StarsDimension;
import co.q64.stars.dimension.fleeting.ChallengeDimension;
import co.q64.stars.dimension.fleeting.FleetingDimension;
import co.q64.stars.dimension.hub.HubDimension;
import co.q64.stars.level.LevelType;
import co.q64.stars.qualifier.SoundQualifiers.Seed;
import co.q64.stars.tile.FormingTile;
import co.q64.stars.tile.SeedTile;
import co.q64.stars.type.FleetingStage;
import co.q64.stars.type.forming.RedFormingBlockType;
import co.q64.stars.util.Capabilities;
import co.q64.stars.util.DecayManager;
import co.q64.stars.util.FleetingManager;
import co.q64.stars.util.HubManager;
import co.q64.stars.util.Identifiers;
import co.q64.stars.util.PlayerManager;
import co.q64.stars.util.Sounds;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.EntityEvent.EyeHeight;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.EntityPlaceEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

@Singleton
public class PlayerListener implements Listener {
    private static final double TOLERANCE = 0.15;
    private static final Direction[] DIRECTIONS = Direction.values();

    protected @Inject PlayerManager playerManager;
    protected @Inject FleetingManager fleetingManager;
    protected @Inject DarkAirBlock darkAirBlock;
    protected @Inject DecayManager decayManager;
    protected @Inject Identifiers identifiers;
    protected @Inject SeedBlock seedBlock;
    protected @Inject RedFormingBlockType redFormingBlockType;
    protected @Inject Provider<GardenerCapabilityProvider> gardenerCapabilityProvider;
    protected @Inject Provider<HubCapabilityProvider> hubCapabilityProvider;
    protected @Inject Sounds sounds;
    protected @Inject @Seed Set<SoundEvent> seedSounds;
    protected @Inject HubManager hubManager;
    protected @Inject Capabilities capabilities;

    private EntitySize size = new EntitySize(0.6f, 0.85f, false);
    private AttributeModifier reach = new AttributeModifier("stars_reach", -3.6, Operation.ADDITION);
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

    // TODO remove?
    @SubscribeEvent
    public void onPlayerLoad(PlayerEvent.LoadFromFile event) {
        if (event.getEntityPlayer().dimension == null) {
            // In unregistered dimension
            event.getEntityPlayer().dimension = DimensionType.OVERWORLD;
        }
    }

    @SubscribeEvent
    public void onCapabilityAttachEntity(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof ServerPlayerEntity) {
            event.addCapability(identifiers.get("gardener"), gardenerCapabilityProvider.get());
        }
    }

    @SubscribeEvent
    public void onCapabilityAttachWorld(AttachCapabilitiesEvent<World> event) {
        if (event.getObject().getDimension() instanceof HubDimension) {
            event.addCapability(identifiers.get("hub"), hubCapabilityProvider.get());
        }
    }


    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent event) throws IllegalAccessException {
        PlayerEntity entity = event.player;
        if (entity.getEntityWorld().getDimension() instanceof StarsDimension) {
            if (entity.getBoundingBox().getYSize() < size.height - 0.02 || entity.getBoundingBox().getYSize() > size.height + 0.02) {
                sizeField.set(entity, size);
                AxisAlignedBB aabb = entity.getBoundingBox();
                entity.setBoundingBox(new AxisAlignedBB(aabb.minX, aabb.minY, aabb.minZ, aabb.minX + size.width, aabb.minY + size.height, aabb.minZ + size.width));
            }
        }
        if (event.side == LogicalSide.SERVER) {
            World world = event.player.getEntityWorld();
            ServerPlayerEntity player = (ServerPlayerEntity) event.player;
            if (world.getDimension() instanceof StarsDimension) {
                if (player.posY < 25) {
                    capabilities.gardener(player, gardener -> {
                        if (world.getDimension() instanceof FleetingDimension) {
                            if (!gardener.isEnteringHub()) {
                                if (gardener.isOpenChallengeDoor()) {
                                    gardener.setEnteringHub(true);
                                    fleetingManager.enter(player, true);
                                } else {
                                    hubManager.fall(player);
                                }
                            }
                        } else {
                            hubManager.exit(player);
                        }
                    });
                    return;
                }
                Block block = entity.getEntityWorld().getBlockState(entity.getPosition().offset(Direction.DOWN)).getBlock();
                player.removePotionEffect(Effects.JUMP_BOOST);
                if (playerManager.shouldApplyJump(player)) {
                    capabilities.gardener(player, gardener -> {
                        if (gardener.getFleetingStage() == FleetingStage.DARK) {
                            player.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 2, 1, true, false));
                        } else {
                            if (gardener.getLevelType() == LevelType.YELLOW) {
                                if (player.getEntityWorld().getDimension() instanceof ChallengeDimension) {
                                    player.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 2, -2, true, false));
                                } else {
                                    player.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 2, 1, true, false));
                                }
                            } else if (gardener.getLevelType() == LevelType.BLUE) {
                                player.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 2, 8, true, false));
                            } else {
                                if (block instanceof BrownFormedBlock) {
                                    player.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 2, -2, true, false));
                                } else if (block instanceof BlueFormedBlock) {
                                    player.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 2, 9, true, false));
                                } else {
                                    player.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 2, 3, true, false));
                                }
                            }
                        }
                    });
                }
            }
            if (world.getDimension() instanceof FleetingDimension) {
                fleetingManager.tickPlayer(player);
                if (decayManager.isDecayBlock((ServerWorld) world, player.getPosition())) {
                    double x = player.posX, y = player.posY, z = player.posZ;
                    double offsetX = x - Math.floor(x);
                    double offsetY = y - Math.floor(y);
                    double offsetZ = z - Math.floor(z);
                    boolean inToleranceMinusX = offsetX > TOLERANCE, inTolerancePlusX = offsetX < 1 - TOLERANCE;
                    boolean inToleranceMinusY = offsetY > -1, inTolerancePlusY = offsetY < 1 - TOLERANCE;
                    boolean inToleranceMinusZ = offsetZ > TOLERANCE, inTolerancePlusZ = offsetZ < 1 - TOLERANCE;
                    for (Direction direction : DIRECTIONS) {
                        if (decayManager.isDecayBlock((ServerWorld) world, player.getPosition().offset(direction))) {
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
                        fleetingManager.createDarkness(player);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityDamage(LivingDamageEvent event) {
        if (event.getEntity().getEntityWorld().getDimension() instanceof StarsDimension) {
            event.setAmount(0);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onBlockBreak(BreakEvent event) {
        IWorld world = event.getWorld();
        if (world.getDimension() instanceof StarsDimension) {
            if (world.getBlockState(event.getPos()).getBlock() instanceof RedPrimedBlock) {
                world.setBlockState(event.getPos(), seedBlock.getDefaultState(), 3);
                Optional.ofNullable((SeedTile) world.getTileEntity(event.getPos())).ifPresent(tile -> {
                    tile.setPrimed(true);
                    tile.setFormingBlockType(redFormingBlockType);
                    tile.setSeedType(redFormingBlockType);
                    tile.setCalculated(true);
                });
                event.setCanceled(true);
                return;
            } else if (world.getBlockState(event.getPos()).getBlock() instanceof SeedBlock) {
                SeedTile tile = (SeedTile) world.getTileEntity(event.getPos());
                if (tile.isPrimed()) {
                    event.setCanceled(true);
                    return;
                }
            } else {
                if (!world.isRemote()) {
                    if (world.getBlockState(event.getPos()).getBlock() instanceof PinkFormedBlock) {
                        sounds.playSound((ServerWorld) world, event.getPos(), seedSounds, 1f);
                        playerManager.addSeed((ServerPlayerEntity) event.getPlayer());
                    }
                    for (Direction direction : DIRECTIONS) {
                        if (world.getBlockState(event.getPos().offset(direction)).getBlock() instanceof FormingBlock) {
                            Optional.ofNullable((FormingTile) world.getTileEntity(event.getPos().offset(direction))).ifPresent(tile -> {
                                if (tile.getDirection() == direction) {
                                    world.setBlockState(event.getPos().offset(direction), Blocks.AIR.getDefaultState(), 3);
                                }
                            });
                        }
                    }
                }
                if (world.getDimension() instanceof FleetingDimension) {
                    world.setBlockState(event.getPos(), darkAirBlock.getDefaultState(), 3);
                    capabilities.gardener(event.getPlayer(), gardener -> {
                        if (gardener.getLevelType() == LevelType.RED) {
                            for (Direction direction : DIRECTIONS) {
                                BlockPos target = event.getPos().offset(direction);
                                Block block = world.getBlockState(target).getBlock();
                                if (block instanceof FormedBlock) {
                                    if (block instanceof GreyFormedBlock) {
                                        continue;
                                    }
                                    world.setBlockState(target, darkAirBlock.getDefaultState(), 3);
                                    if (block instanceof PinkFormedBlock) {
                                        sounds.playSound((ServerWorld) world, event.getPos(), seedSounds, 1f);
                                        playerManager.addSeed((ServerPlayerEntity) event.getPlayer());
                                    }
                                    for (Direction dir : DIRECTIONS) {
                                        if (world.getBlockState(target.offset(dir)).getBlock() instanceof FormingBlock) {
                                            Optional.ofNullable((FormingTile) world.getTileEntity(event.getPos().offset(dir))).ifPresent(tile -> {
                                                if (tile.getDirection() == dir) {
                                                    world.setBlockState(target.offset(dir), Blocks.AIR.getDefaultState(), 3);
                                                }
                                            });
                                        }
                                    }
                                }
                            }
                        } else if (gardener.getLevelType() == LevelType.PINK) {
                            for (boolean down : Arrays.asList(true, false)) {
                                for (int offset = 1; offset < 50; offset++) {
                                    int yOffset = offset * (down ? -1 : 1);
                                    BlockPos target = event.getPos().add(0, yOffset, 0);
                                    if (target.getY() < 0 || target.getY() >= 255) {
                                        break;
                                    }
                                    if (!(world.getBlockState(target).getBlock() instanceof PinkFormedBlock)) {
                                        break;
                                    }
                                    world.setBlockState(target, darkAirBlock.getDefaultState(), 3);
                                    sounds.playSound((ServerWorld) world, event.getPos(), seedSounds, 1f);
                                    playerManager.addSeed((ServerPlayerEntity) event.getPlayer());
                                    for (Direction dir : DIRECTIONS) {
                                        if (world.getBlockState(target.offset(dir)).getBlock() instanceof FormingBlock) {
                                            Optional.ofNullable((FormingTile) world.getTileEntity(target.offset(dir))).ifPresent(tile -> {
                                                if (tile.getDirection() == dir) {
                                                    world.setBlockState(target.offset(dir), Blocks.AIR.getDefaultState(), 3);
                                                }
                                            });
                                        }
                                    }
                                }
                            }
                        }
                    });
                } else {
                    world.setBlockState(event.getPos(), Blocks.AIR.getDefaultState(), 3);
                }
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onBlockPlace(EntityPlaceEvent event) {
        if (event.getWorld().getDimension() instanceof StarsDimension) {
            if (!(event.getPlacedBlock().getBlock() instanceof BaseBlock)) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerChangeDimension(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            for (AttributeModifier modifier : new ArrayList<>(player.getAttribute(PlayerEntity.REACH_DISTANCE).getModifiers())) {
                player.getAttribute(PlayerEntity.REACH_DISTANCE).removeModifier(modifier);
            }
            if (event.getWorld().getDimension() instanceof StarsDimension) {
                player.getAttribute(PlayerEntity.REACH_DISTANCE).applyModifier(reach);
                if (!player.getEntityWorld().isRemote) {
                    playerManager.syncCapability((ServerPlayerEntity) player);
                }
            } else {
                player.getAttribute(PlayerEntity.REACH_DISTANCE).removeModifier(reach);
            }
        }
    }

    @SubscribeEvent
    public void onEyeHeight(EyeHeight event) throws IllegalAccessException {
        if (event.getEntity() instanceof PlayerEntity) {
            PlayerEntity entity = (PlayerEntity) event.getEntity();
            if (event.getEntity().getEntityWorld().getDimension() instanceof StarsDimension) {
                event.setNewHeight(0.5f);
            }
        }
    }
}
