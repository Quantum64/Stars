package co.q64.stars;

import co.q64.stars.binders.ConstantBinders.Author;
import co.q64.stars.binders.ConstantBinders.ModId;
import co.q64.stars.binders.ConstantBinders.Name;
import co.q64.stars.binders.ConstantBinders.Version;
import co.q64.stars.block.AirDecayBlock;
import co.q64.stars.block.AirDecayEdgeBlock;
import co.q64.stars.block.BaseBlock;
import co.q64.stars.block.BlueFormedBlock;
import co.q64.stars.block.BrownFormedBlock;
import co.q64.stars.block.CyanFormedBlock;
import co.q64.stars.block.DarkAirBlock;
import co.q64.stars.block.DarknessBlock;
import co.q64.stars.block.DarknessEdgeBlock;
import co.q64.stars.block.DecayBlock;
import co.q64.stars.block.DecayEdgeBlock;
import co.q64.stars.block.DecayingBlock;
import co.q64.stars.block.DoorBlock;
import co.q64.stars.block.FormingBlock;
import co.q64.stars.block.GreenFormedBlock;
import co.q64.stars.block.OrangeFormedBlock;
import co.q64.stars.block.PinkFormedBlock;
import co.q64.stars.block.PurpleFormedBlock;
import co.q64.stars.block.RedFormedBlock;
import co.q64.stars.block.RedPrimedBlock;
import co.q64.stars.block.SeedBlock;
import co.q64.stars.block.SpecialAirBlock;
import co.q64.stars.block.SpecialDecayBlock;
import co.q64.stars.block.SpecialDecayEdgeBlock;
import co.q64.stars.block.YellowFormedBlock;
import co.q64.stars.item.BaseItem;
import co.q64.stars.item.BlueSeedItem;
import co.q64.stars.item.BrownSeedItem;
import co.q64.stars.item.CyanSeedItem;
import co.q64.stars.item.GreenSeedItem;
import co.q64.stars.item.PinkSeedItem;
import co.q64.stars.item.PurpleSeedItem;
import co.q64.stars.item.RedSeedItem;
import co.q64.stars.item.YellowSeedItem;
import co.q64.stars.listener.InitializationListener;
import co.q64.stars.listener.Listener;
import co.q64.stars.listener.PlayerListener;
import co.q64.stars.listener.RegistryListener;
import co.q64.stars.listener.WorldUnloadListener;
import co.q64.stars.tile.type.AirDecayEdgeTileType;
import co.q64.stars.tile.type.DarknessEdgeTileType;
import co.q64.stars.tile.type.DecayEdgeTileType;
import co.q64.stars.tile.type.DecayingTileType;
import co.q64.stars.tile.type.DoorTileType;
import co.q64.stars.tile.type.ForceRenderCullTileType;
import co.q64.stars.tile.type.FormingTileType;
import co.q64.stars.tile.type.SeedTileType;
import co.q64.stars.tile.type.SpecialDecayEdgeTileType;
import co.q64.stars.type.FormingBlockType;
import co.q64.stars.type.forming.BlueFormingBlockType;
import co.q64.stars.type.forming.BrownFormingBlockType;
import co.q64.stars.type.forming.CyanFormingBlockType;
import co.q64.stars.type.forming.GreenFormingBlockType;
import co.q64.stars.type.forming.OrangeFormingBlockType;
import co.q64.stars.type.forming.PinkFormingBlockType;
import co.q64.stars.type.forming.PurpleFormingBlockType;
import co.q64.stars.type.forming.RedFormingBlockType;
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
    @Binds @IntoSet FormingBlockType bindBlueFormingBlockType(BlueFormingBlockType type);
    @Binds @IntoSet FormingBlockType bindPinkFormingBlockType(PinkFormingBlockType type);
    @Binds @IntoSet FormingBlockType bindCyanFormingBlockType(CyanFormingBlockType type);
    @Binds @IntoSet FormingBlockType bindRedFormingBlockType(RedFormingBlockType type);
    @Binds @IntoSet FormingBlockType bindGreenFormingBlockType(GreenFormingBlockType type);
    @Binds @IntoSet FormingBlockType bindBrownFormingBlockType(BrownFormingBlockType type);
    @Binds @IntoSet FormingBlockType bindOrangeFormingBlockType(OrangeFormingBlockType type);

    @Binds @IntoSet BaseBlock bindFormingBlock(FormingBlock formingBlock);
    @Binds @IntoSet BaseBlock bindDecayBlock(DecayBlock decayBlock);
    @Binds @IntoSet BaseBlock bindDecayEdgeBlock(DecayEdgeBlock decayEdgeBlock);
    @Binds @IntoSet BaseBlock bindDecayingBlock(DecayingBlock decayBlock);
    @Binds @IntoSet BaseBlock bindDarkAirBlock(DarkAirBlock decayBlock);
    @Binds @IntoSet BaseBlock bindSpecialAirBlock(SpecialAirBlock specialAirBlock);
    @Binds @IntoSet BaseBlock bindAirDecayBlock(AirDecayBlock airDecayBlock);
    @Binds @IntoSet BaseBlock bindAirDecayEdgeBlock(AirDecayEdgeBlock airDecayEdgeBlock);
    @Binds @IntoSet BaseBlock bindSpecialDecayBlock(SpecialDecayBlock specialDecayBlock);
    @Binds @IntoSet BaseBlock bindSpecialDecayEdgeBlock(SpecialDecayEdgeBlock specialDecayEdgeBlock);
    @Binds @IntoSet BaseBlock bindDarknessBlock(DarknessBlock darknessBlock);
    @Binds @IntoSet BaseBlock bindDarknessEdgeBlock(DarknessEdgeBlock darknessEdgeBlock);
    @Binds @IntoSet BaseBlock bindDoorBlock(DoorBlock doorBlock);
    @Binds @IntoSet BaseBlock bindSeedBlock(SeedBlock seedBlock);
    @Binds @IntoSet BaseBlock bindYellowFormedBlock(YellowFormedBlock yellowFormedBlock);
    @Binds @IntoSet BaseBlock bindPurpleFormedBlock(PurpleFormedBlock purpleFormedBlock);
    @Binds @IntoSet BaseBlock bindBlueFormedBlock(BlueFormedBlock blueFormedBlock);
    @Binds @IntoSet BaseBlock bindRedFormedBlock(RedFormedBlock redFormedBlock);
    @Binds @IntoSet BaseBlock bindRedPrimedBlock(RedPrimedBlock redPrimedBlock);
    @Binds @IntoSet BaseBlock bindGreenFormedBlock(GreenFormedBlock greenFormedBlock);
    @Binds @IntoSet BaseBlock bindCyanFormedBlock(CyanFormedBlock cyanFormedBlock);
    @Binds @IntoSet BaseBlock bindPinkFormedBlock(PinkFormedBlock pinkFormedBlock);
    @Binds @IntoSet BaseBlock bindBrownFormedBlock(BrownFormedBlock brownFormedBlock);
    @Binds @IntoSet BaseBlock bindOrangeFormedBlock(OrangeFormedBlock orangeFormedBlock);

    @Binds @IntoSet BaseItem bindPinkSeedItem(PinkSeedItem pinkSeedItem);
    @Binds @IntoSet BaseItem bindBlueSeedItem(BlueSeedItem blueSeedItem);
    @Binds @IntoSet BaseItem bindPurpleSeedItem(PurpleSeedItem purpleSeedItem);
    @Binds @IntoSet BaseItem bindYellowSeedItem(YellowSeedItem yellowSeedItem);
    @Binds @IntoSet BaseItem bindCyanSeedItem(CyanSeedItem cyanSeedItem);
    @Binds @IntoSet BaseItem bindGreenSeedItem(GreenSeedItem greenSeedItem);
    @Binds @IntoSet BaseItem bindBrownSeedItem(BrownSeedItem brownSeedItem);
    @Binds @IntoSet BaseItem bindRedSeedItem(RedSeedItem redSeedItem);

    @Binds @IntoSet Listener bindRegistryListener(RegistryListener serverStartListener);
    @Binds @IntoSet Listener bindInitializationListener(InitializationListener initializationListener);
    @Binds @IntoSet Listener bindPlayerLoadListener(PlayerListener playerLoadListener);
    @Binds @IntoSet Listener bindWorldUnloadListener(WorldUnloadListener worldUnloadListener);

    @Binds @IntoSet TileEntityType<?> bindFormingTileType(FormingTileType type);
    @Binds @IntoSet TileEntityType<?> bindDecayEdgeTileType(DecayEdgeTileType type);
    @Binds @IntoSet TileEntityType<?> bindDecayingTileType(DecayingTileType type);
    @Binds @IntoSet TileEntityType<?> bindAirDecayEdgeTileType(AirDecayEdgeTileType type);
    @Binds @IntoSet TileEntityType<?> bindDarknessEdgeTileType(DarknessEdgeTileType type);
    @Binds @IntoSet TileEntityType<?> bindForceRenderCullTileType(ForceRenderCullTileType type);
    @Binds @IntoSet TileEntityType<?> bindSpecialDecayEdgeTileType(SpecialDecayEdgeTileType type);
    @Binds @IntoSet TileEntityType<?> bindDoorTileType(DoorTileType type);
    @Binds @IntoSet TileEntityType<?> bindSeedTileType(SeedTileType type);

    static @Provides @Singleton FMLJavaModLoadingContext provideFMLModLoadingContext() { return FMLJavaModLoadingContext.get(); }
    static @Provides @Singleton Logger provideLogger() { return LogManager.getLogger(); }

    static @Provides @ModId String provideModId() { return ModInformation.ID; }
    static @Provides @Name String provideName() { return ModInformation.NAME; }
    static @Provides @Version String provideAuthor() { return ModInformation.VERSION; }
    static @Provides @Author String provideVersion() { return ModInformation.AUTHOR; }

    // @formatter:on
}
