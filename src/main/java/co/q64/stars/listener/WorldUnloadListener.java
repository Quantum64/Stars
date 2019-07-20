package co.q64.stars.listener;

import co.q64.stars.binders.ConstantBinders.ModId;
import co.q64.stars.util.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Singleton
public class WorldUnloadListener implements Listener {
    private static final int MAX_DISTANCE_SQUARED = 1000 * 1000;

    protected @Inject @ModId String modId;
    protected @Inject Logger logger;

    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    protected @Inject WorldUnloadListener() {}

    /*
    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {

        IWorld world = event.getWorld();
        service.schedule(() -> {
            if (event.getWorld().isRemote()) {
                Files.walk(event.getWorld().getWorld().sa)
                        .sorted(Comparator.reverseOrder())
                        .forEach(t -> {
                            try {
                                Files.delete(t);
                            } catch (IOException e) {
                                e.printStackTrace();

                            }
                        });
            }
        }, 5L, TimeUnit.SECONDS);


    }

    @SubscribeEvent
    public void dimensionRegistryLoadEvent(RegisterDimensionsEvent event) {
        for (Iterator<ResourceLocation> itr = event.getMissingNames().iterator(); itr.hasNext(); ) {
            ResourceLocation missing = itr.next();
            if (missing.getNamespace().equals(modId)) {
                itr.remove();
            }
        }
    }

    private void unsupportedUnregisterDimension(Dimension dimension) {
        ClearableRegistry<DimensionType> types = DimensionManager.getRegistry();
    }

    /*
    @SubscribeEvent
    public void onChunkLoad(ChunkEvent.Load loadEvent) {
        if (loadEvent.getChunk() instanceof Chunk) {
            Chunk chunk = (Chunk) loadEvent.getChunk();
            if (chunk.getWorld().getDimension() instanceof FleetingDimension && !chunk.getWorld().isRemote()) {
                checkForcedChunk(chunk, "chunk load");
            }
        }
    }

    @SubscribeEvent
    public void onChunkUnload(ChunkEvent.Unload unloadEvent) {
        if (unloadEvent.getChunk() instanceof Chunk) {
            Chunk chunk = (Chunk) unloadEvent.getChunk();
            if (chunk.getWorld().getDimension() instanceof FleetingDimension) {
                if (!chunk.getWorldForge().isRemote()) {
                    chunk.setLastSaveTime(chunk.getWorld().getGameTime());
                    chunk.setModified(false);
                    logger.info("Forced chunk to unload without save");
                }
            }
        }
    }

    @SubscribeEvent
    public void onChunkModify(PlayerInteractEvent.RightClickBlock event) {
        if (!event.getWorld().isRemote && event.getWorld().getDimension() instanceof FleetingDimension) {
            IChunk chunk = event.getWorld().getChunk(event.getPos());
            ((ServerWorld) event.getWorld()).forceChunk(chunk.getPos().x, chunk.getPos().z, true);
            logger.info("Added force on interact");
        }
    }

    @SubscribeEvent
    public void onServerTick(WorldTickEvent event) {
        if (!event.world.isRemote() && event.world.getDimension() instanceof FleetingDimension) {
            World world = event.world;
            for (long l : ((ServerWorld) world).getForcedChunks()) {
                long x = ChunkPos.getX(l) << 4;
                long z = ChunkPos.getZ(l) << 4;
                boolean force = false;
                for (ServerPlayerEntity player : ((ServerWorld) world).getPlayers()) {
                    if (player.getPosition().distanceSq(x, 100, z, false) < MAX_DISTANCE_SQUARED) {
                        force = true;
                        break;
                    }
                }
                checkForcedChunk(world.getChunk(ChunkPos.getX(l), ChunkPos.getZ(l)), "world tick");
            }

        }
    }

    private void checkForcedChunk(Chunk chunk, String when) {
        ServerWorld world = (ServerWorld) chunk.getWorld();
        BlockPos targetPosition = chunk.getPos().asBlockPos();
        boolean force = false;
        for (ServerPlayerEntity player : world.getPlayers()) {
            BlockPos playerPosition = player.getPosition();
            if (playerPosition.distanceSq(targetPosition) < MAX_DISTANCE_SQUARED) {
                force = true;
                break;
            }
        }
        if (force) {

        } else {
            if (world.getForcedChunks().contains(chunk.getPos().asLong())) {
                logger.info("Removed forced chunk on " + when);
                world.forceChunk(chunk.getPos().x, chunk.getPos().z, false);
            }
        }
    }
     */

}
