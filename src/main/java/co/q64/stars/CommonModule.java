package co.q64.stars;

import co.q64.stars.binders.ConstantBinders.Author;
import co.q64.stars.binders.ConstantBinders.ModId;
import co.q64.stars.binders.ConstantBinders.Name;
import co.q64.stars.binders.ConstantBinders.Version;
import co.q64.stars.block.BaseBlock;
import co.q64.stars.block.FormingBlock;
import co.q64.stars.block.YellowFormedBlock;
import co.q64.stars.listener.InitializationListener;
import co.q64.stars.listener.Listener;
import co.q64.stars.listener.RegistryListener;
import co.q64.stars.tile.type.FormingTileType;
import co.q64.stars.type.FormingBlockType;
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

    @Binds @IntoSet BaseBlock bindFormingBlock(FormingBlock formingBlock);
    @Binds @IntoSet BaseBlock bindYellowFormedBlock(YellowFormedBlock yellowFormedBlock);

    @Binds @IntoSet Listener bindRegistryListener(RegistryListener serverStartListener);
    @Binds @IntoSet Listener bindInitializationListener(InitializationListener initializationListener);

    @Binds @IntoSet TileEntityType<?> bindMachineTileType(FormingTileType type);

    static @Provides @Singleton FMLJavaModLoadingContext provideFMLModLoadingContext() { return FMLJavaModLoadingContext.get(); }
    static @Provides @Singleton Logger provideLogger() { return LogManager.getLogger(); }

    static @Provides @ModId String provideModId() { return ModInformation.ID; }
    static @Provides @Name String provideName() { return ModInformation.NAME; }
    static @Provides @Version String provideAuthor() { return ModInformation.VERSION; }
    static @Provides @Author String provideVersion() { return ModInformation.AUTHOR; }

    // @formatter:on
}
