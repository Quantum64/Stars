package co.q64.stars;

import co.q64.stars.binders.ConstantBinders.Author;
import co.q64.stars.binders.ConstantBinders.ModId;
import co.q64.stars.binders.ConstantBinders.Name;
import co.q64.stars.binders.ConstantBinders.Version;
import co.q64.stars.block.AirDecayBlock;
import co.q64.stars.block.AirDecayEdgeBlock;
import co.q64.stars.block.BaseBlock;
import co.q64.stars.block.DarkAirBlock;
import co.q64.stars.block.DarknessBlock;
import co.q64.stars.block.DarknessEdgeBlock;
import co.q64.stars.block.DecayBlock;
import co.q64.stars.block.DecayEdgeBlock;
import co.q64.stars.block.DecayingBlock;
import co.q64.stars.block.FormingBlock;
import co.q64.stars.block.PurpleFormedBlock;
import co.q64.stars.block.YellowFormedBlock;
import co.q64.stars.listener.InitializationListener;
import co.q64.stars.listener.Listener;
import co.q64.stars.listener.PlayerListener;
import co.q64.stars.listener.RegistryListener;
import co.q64.stars.listener.WorldUnloadListener;
import co.q64.stars.tile.type.AirDecayEdgeTileType;
import co.q64.stars.tile.type.DarknessEdgeTileType;
import co.q64.stars.tile.type.DecayEdgeTileType;
import co.q64.stars.tile.type.DecayingTileType;
import co.q64.stars.tile.type.FormingTileType;
import co.q64.stars.type.FormingBlockType;
import co.q64.stars.type.forming.PurpleFormingBlockType;
import co.q64.stars.type.forming.YellowFormingBlockType;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Singleton;

@Module
public interface CommonModule {
    // @formatter:off

    @Binds @IntoSet FormingBlockType bindYellowFormingBlockType(YellowFormingBlockType type);
    @Binds @IntoSet FormingBlockType bindPurpleFormingBlockType(PurpleFormingBlockType type);

    @Binds @IntoSet BaseBlock bindFormingBlock(FormingBlock formingBlock);
    @Binds @IntoSet BaseBlock bindDecayBlock(DecayBlock decayBlock);
    @Binds @IntoSet BaseBlock bindDecayEdgeBlock(DecayEdgeBlock decayEdgeBlock);
    @Binds @IntoSet BaseBlock bindDecayingBlock(DecayingBlock decayBlock);
    @Binds @IntoSet BaseBlock bindDarkAirBlock(DarkAirBlock decayBlock);
    @Binds @IntoSet BaseBlock bindAirDecayBlock(AirDecayBlock airDecayBlock);
    @Binds @IntoSet BaseBlock bindAirDecayEdgeBlock(AirDecayEdgeBlock airDecayEdgeBlock);
    @Binds @IntoSet BaseBlock bindDarknessBlock(DarknessBlock darknessBlock);
    @Binds @IntoSet BaseBlock bindDarknessEdgeBlock(DarknessEdgeBlock darknessEdgeBlock);
    @Binds @IntoSet BaseBlock bindYellowFormedBlock(YellowFormedBlock yellowFormedBlock);
    @Binds @IntoSet BaseBlock bindPurpleFormedBlock(PurpleFormedBlock purpleFormedBlock);

    @Binds @IntoSet Listener bindRegistryListener(RegistryListener serverStartListener);
    @Binds @IntoSet Listener bindInitializationListener(InitializationListener initializationListener);
    @Binds @IntoSet Listener bindPlayerLoadListener(PlayerListener playerLoadListener);
    @Binds @IntoSet Listener bindWorldUnloadListener(WorldUnloadListener worldUnloadListener);

    @Binds @IntoSet TileEntityType<?> bindFormingTileType(FormingTileType type);
    @Binds @IntoSet TileEntityType<?> bindDecayEdgeTileType(DecayEdgeTileType type);
    @Binds @IntoSet TileEntityType<?> bindDecayingTileType(DecayingTileType type);
    @Binds @IntoSet TileEntityType<?> bindAirDecayEdgeTileType(AirDecayEdgeTileType type);
    @Binds @IntoSet TileEntityType<?> bindDarknessEdgeTileType(DarknessEdgeTileType type);

    static @Provides @Singleton FMLJavaModLoadingContext provideFMLModLoadingContext() { return FMLJavaModLoadingContext.get(); }
    static @Provides @Singleton Logger provideLogger() { return LogManager.getLogger(); }

    static @Provides @ModId String provideModId() { return ModInformation.ID; }
    static @Provides @Name String provideName() { return ModInformation.NAME; }
    static @Provides @Version String provideAuthor() { return ModInformation.VERSION; }
    static @Provides @Author String provideVersion() { return ModInformation.AUTHOR; }

    // @formatter:on
}
