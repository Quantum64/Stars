package co.q64.stars;

import co.q64.stars.block.AirDecayBlock;
import co.q64.stars.block.AirDecayEdgeBlock;
import co.q64.stars.block.BaseBlock;
import co.q64.stars.block.BlueFormedBlock;
import co.q64.stars.block.BlueFormedBlock.BlueFormedBlockHard;
import co.q64.stars.block.BrownFormedBlock;
import co.q64.stars.block.BrownFormedBlock.BrownFormedBlockHard;
import co.q64.stars.block.ChallengeDoorBlock;
import co.q64.stars.block.ChallengeEntranceBlock;
import co.q64.stars.block.ChallengeExitBlock;
import co.q64.stars.block.CyanFormedBlock;
import co.q64.stars.block.CyanFormedBlock.CyanFormedBlockHard;
import co.q64.stars.block.DarkAirBlock;
import co.q64.stars.block.DarknessBlock;
import co.q64.stars.block.DarknessEdgeBlock;
import co.q64.stars.block.DecayBlock;
import co.q64.stars.block.DecayBlock.DecayBlockSolid;
import co.q64.stars.block.DecayEdgeBlock;
import co.q64.stars.block.DecayEdgeBlock.DecayEdgeBlockSolid;
import co.q64.stars.block.DecayingBlock;
import co.q64.stars.block.DecayingBlock.DecayingBlockHard;
import co.q64.stars.block.DoorBlock;
import co.q64.stars.block.FormingBlock;
import co.q64.stars.block.GatewayBlock;
import co.q64.stars.block.GreenFormedBlock;
import co.q64.stars.block.GreenFormedBlock.GreenFormedBlockHard;
import co.q64.stars.block.GreenFruitBlock;
import co.q64.stars.block.GreenFruitBlock.GreenFruitBlockHard;
import co.q64.stars.block.GreyFormedBlock;
import co.q64.stars.block.OrangeFormedBlock;
import co.q64.stars.block.OrangeFormedBlock.OrangeFormedBlockHard;
import co.q64.stars.block.PinkFormedBlock;
import co.q64.stars.block.PurpleFormedBlock;
import co.q64.stars.block.PurpleFormedBlock.PurpleFormedBlockHard;
import co.q64.stars.block.RedFormedBlock;
import co.q64.stars.block.RedFormedBlock.RedFormedBlockHard;
import co.q64.stars.block.RedPrimedBlock;
import co.q64.stars.block.RedPrimedBlock.RedPrimedBlockHard;
import co.q64.stars.block.SeedBlock;
import co.q64.stars.block.SeedBlock.SeedBlockHard;
import co.q64.stars.block.SpecialAirBlock;
import co.q64.stars.block.SpecialDecayBlock;
import co.q64.stars.block.SpecialDecayEdgeBlock;
import co.q64.stars.block.TealFormedBlock;
import co.q64.stars.block.TrophyBlock;
import co.q64.stars.block.TubeAirBlock;
import co.q64.stars.block.TubeDarknessBlock;
import co.q64.stars.block.YellowFormedBlock;
import co.q64.stars.block.YellowFormedBlock.YellowFormedBlockHard;
import co.q64.stars.capability.GardenerCapability;
import co.q64.stars.capability.HubCapability;
import co.q64.stars.capability.gardener.GardenerCapabilityImpl;
import co.q64.stars.capability.hub.HubCapabilityImpl;
import co.q64.stars.dimension.fleeting.FleetingBiome;
import co.q64.stars.dimension.fleeting.FleetingDimension.FleetingDimensionTemplate;
import co.q64.stars.dimension.fleeting.FleetingSolidBiome;
import co.q64.stars.dimension.fleeting.FleetingSolidDimension.FleetingSolidDimensionTemplate;
import co.q64.stars.dimension.fleeting.feature.DecayBlobFeature;
import co.q64.stars.dimension.fleeting.feature.SolidDecayBlobFeature;
import co.q64.stars.dimension.fleeting.placement.DecayBlobPlacement;
import co.q64.stars.dimension.hub.HubBiome;
import co.q64.stars.dimension.hub.HubDimension.HubDimensionTemplate;
import co.q64.stars.entity.PickupEntity;
import co.q64.stars.entity.PickupEntityFactory;
import co.q64.stars.item.ArrowItem;
import co.q64.stars.item.BaseItem;
import co.q64.stars.item.BlueSeedItem;
import co.q64.stars.item.BlueSeedItem.BlueSeedItemRobust;
import co.q64.stars.item.BrownSeedItem;
import co.q64.stars.item.BrownSeedItem.BrownSeedItemRobust;
import co.q64.stars.item.ChallengeStarItem;
import co.q64.stars.item.CyanSeedItem;
import co.q64.stars.item.CyanSeedItem.CyanSeedItemRobust;
import co.q64.stars.item.GreenSeedItem;
import co.q64.stars.item.GreenSeedItem.GreenSeedItemRobust;
import co.q64.stars.item.HeartItem;
import co.q64.stars.item.KeyItem;
import co.q64.stars.item.OrangeSeedItem;
import co.q64.stars.item.OrangeSeedItem.OrangeSeedItemRobust;
import co.q64.stars.item.PinkSeedItem;
import co.q64.stars.item.PinkSeedItem.PinkSeedItemRobust;
import co.q64.stars.item.PurpleSeedItem;
import co.q64.stars.item.PurpleSeedItem.PurpleSeedItemRobust;
import co.q64.stars.item.RedSeedItem;
import co.q64.stars.item.RedSeedItem.RedSeedItemRobust;
import co.q64.stars.item.StarItem;
import co.q64.stars.item.TealSeedItem;
import co.q64.stars.item.TealSeedItem.TealSeedItemRobust;
import co.q64.stars.item.YellowSeedItem;
import co.q64.stars.item.YellowSeedItem.YellowSeedItemRobust;
import co.q64.stars.level.Level;
import co.q64.stars.level.levels.CyanLevel;
import co.q64.stars.level.levels.RedLevel;
import co.q64.stars.listener.InitializationListener;
import co.q64.stars.listener.Listener;
import co.q64.stars.listener.PlayerListener;
import co.q64.stars.listener.RegistryListener;
import co.q64.stars.listener.WorldUnloadListener;
import co.q64.stars.qualifier.ConstantQualifiers.Author;
import co.q64.stars.qualifier.ConstantQualifiers.ModId;
import co.q64.stars.qualifier.ConstantQualifiers.Name;
import co.q64.stars.qualifier.ConstantQualifiers.Version;
import co.q64.stars.qualifier.SoundQualifiers.Blue;
import co.q64.stars.qualifier.SoundQualifiers.Brown;
import co.q64.stars.qualifier.SoundQualifiers.Bubble;
import co.q64.stars.qualifier.SoundQualifiers.Cyan;
import co.q64.stars.qualifier.SoundQualifiers.Dark;
import co.q64.stars.qualifier.SoundQualifiers.DarkAir;
import co.q64.stars.qualifier.SoundQualifiers.Door;
import co.q64.stars.qualifier.SoundQualifiers.Empty;
import co.q64.stars.qualifier.SoundQualifiers.Explode;
import co.q64.stars.qualifier.SoundQualifiers.ExplodeDark;
import co.q64.stars.qualifier.SoundQualifiers.Green;
import co.q64.stars.qualifier.SoundQualifiers.Key;
import co.q64.stars.qualifier.SoundQualifiers.Misc;
import co.q64.stars.qualifier.SoundQualifiers.Pink;
import co.q64.stars.qualifier.SoundQualifiers.Pop;
import co.q64.stars.qualifier.SoundQualifiers.Purple;
import co.q64.stars.qualifier.SoundQualifiers.Red;
import co.q64.stars.qualifier.SoundQualifiers.Seed;
import co.q64.stars.qualifier.SoundQualifiers.Teal;
import co.q64.stars.qualifier.SoundQualifiers.Thunder;
import co.q64.stars.qualifier.SoundQualifiers.Ticking;
import co.q64.stars.qualifier.SoundQualifiers.Yellow;
import co.q64.stars.tile.type.AirDecayEdgeTileType;
import co.q64.stars.tile.type.ChallengeExitTileType;
import co.q64.stars.tile.type.DarknessEdgeTileType;
import co.q64.stars.tile.type.DecayEdgeTileType;
import co.q64.stars.tile.type.DecayingTileType;
import co.q64.stars.tile.type.DoorTileType;
import co.q64.stars.tile.type.ForceRenderCullTileType;
import co.q64.stars.tile.type.FormingTileType;
import co.q64.stars.tile.type.SeedTileType;
import co.q64.stars.tile.type.SpecialDecayEdgeTileType;
import co.q64.stars.tile.type.TrophyTileType;
import co.q64.stars.tile.type.TubeTileType;
import co.q64.stars.type.FormingBlockType;
import co.q64.stars.type.forming.BlueFormingBlockType;
import co.q64.stars.type.forming.BrownFormingBlockType;
import co.q64.stars.type.forming.CyanFormingBlockType;
import co.q64.stars.type.forming.GreenFormingBlockType;
import co.q64.stars.type.forming.GreyFormingBlockType;
import co.q64.stars.type.forming.OrangeFormingBlockType;
import co.q64.stars.type.forming.PinkFormingBlockType;
import co.q64.stars.type.forming.PurpleFormingBlockType;
import co.q64.stars.type.forming.RedFormingBlockType;
import co.q64.stars.type.forming.TealFormingBlockType;
import co.q64.stars.type.forming.YellowFormingBlockType;
import co.q64.stars.util.Identifiers;
import co.q64.stars.util.UnfortunateForgeBlackMagic;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Singleton;
import java.util.Set;

@Module
public interface CommonModule {
    // @formatter:off

    @Binds GardenerCapability bindGardenerCapability(GardenerCapabilityImpl gardenerCapability);
    @Binds HubCapability bindHubCapability(HubCapabilityImpl hubCapability);

    @Binds @IntoSet FormingBlockType bindYellowFormingBlockType(YellowFormingBlockType type);
    @Binds @IntoSet FormingBlockType bindPurpleFormingBlockType(PurpleFormingBlockType type);
    @Binds @IntoSet FormingBlockType bindBlueFormingBlockType(BlueFormingBlockType type);
    @Binds @IntoSet FormingBlockType bindPinkFormingBlockType(PinkFormingBlockType type);
    @Binds @IntoSet FormingBlockType bindCyanFormingBlockType(CyanFormingBlockType type);
    @Binds @IntoSet FormingBlockType bindRedFormingBlockType(RedFormingBlockType type);
    @Binds @IntoSet FormingBlockType bindGreenFormingBlockType(GreenFormingBlockType type);
    @Binds @IntoSet FormingBlockType bindBrownFormingBlockType(BrownFormingBlockType type);
    @Binds @IntoSet FormingBlockType bindOrangeFormingBlockType(OrangeFormingBlockType type);
    @Binds @IntoSet FormingBlockType bindGreyFormingBlockType(GreyFormingBlockType type);
    @Binds @IntoSet FormingBlockType bindTealFormingBlockType(TealFormingBlockType type);

    @Binds @IntoSet Level bindRedLevel(RedLevel redLevel);
    @Binds @IntoSet Level bindCyanLevel(CyanLevel cyanLevel);

    @Binds @IntoSet BaseBlock bindFormingBlock(FormingBlock formingBlock);
    @Binds @IntoSet BaseBlock bindDecayBlock(DecayBlock decayBlock);
    @Binds @IntoSet BaseBlock bindDecayBlockSolid(DecayBlockSolid decayBlockSolid);
    @Binds @IntoSet BaseBlock bindDecayEdgeBlock(DecayEdgeBlock decayEdgeBlock);
    @Binds @IntoSet BaseBlock bindDecayEdgeBlockSolid(DecayEdgeBlockSolid decayEdgeBlockSolid);
    @Binds @IntoSet BaseBlock bindDecayingBlock(DecayingBlock decayingBlock);
    @Binds @IntoSet BaseBlock bindDecayingBlockHard(DecayingBlockHard decayingBlockHard);
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
    @Binds @IntoSet BaseBlock bindSeedBlockHard(SeedBlockHard seedBlock);
    @Binds @IntoSet BaseBlock bindYellowFormedBlock(YellowFormedBlock yellowFormedBlock);
    @Binds @IntoSet BaseBlock bindYellowFormedBlockHard(YellowFormedBlockHard yellowFormedBlockHard);
    @Binds @IntoSet BaseBlock bindPurpleFormedBlock(PurpleFormedBlock purpleFormedBlock);
    @Binds @IntoSet BaseBlock bindPurpleFormedBlockHard(PurpleFormedBlockHard purpleFormedBlockHard);
    @Binds @IntoSet BaseBlock bindBlueFormedBlock(BlueFormedBlock blueFormedBlock);
    @Binds @IntoSet BaseBlock bindBlueFormedBlockHard(BlueFormedBlockHard blueFormedBlockHard);
    @Binds @IntoSet BaseBlock bindRedFormedBlock(RedFormedBlock redFormedBlock);
    @Binds @IntoSet BaseBlock bindRedFormedBlockHard(RedFormedBlockHard redFormedBlockHard);
    @Binds @IntoSet BaseBlock bindRedPrimedBlock(RedPrimedBlock redPrimedBlock);
    @Binds @IntoSet BaseBlock bindRedPrimedBlockHard(RedPrimedBlockHard redPrimedBlockHard);
    @Binds @IntoSet BaseBlock bindGreenFormedBlock(GreenFormedBlock greenFormedBlock);
    @Binds @IntoSet BaseBlock bindGreenFormedBlockHard(GreenFormedBlockHard greenFormedBlockHard);
    @Binds @IntoSet BaseBlock bindGreenFruitBlock(GreenFruitBlock greenFruitBlock);
    @Binds @IntoSet BaseBlock bindGreenFruitBlockHard(GreenFruitBlockHard greenFruitBlockHard);
    @Binds @IntoSet BaseBlock bindCyanFormedBlock(CyanFormedBlock cyanFormedBlock);
    @Binds @IntoSet BaseBlock bindCyanFormedBlockHard(CyanFormedBlockHard cyanFormedBlockHard);
    @Binds @IntoSet BaseBlock bindPinkFormedBlock(PinkFormedBlock pinkFormedBlock);
    @Binds @IntoSet BaseBlock bindBrownFormedBlock(BrownFormedBlock brownFormedBlock);
    @Binds @IntoSet BaseBlock bindBrownFormedBlockHard(BrownFormedBlockHard brownFormedBlockHard);
    @Binds @IntoSet BaseBlock bindOrangeFormedBlock(OrangeFormedBlock orangeFormedBlock);
    @Binds @IntoSet BaseBlock bindOrangeFormedBlockHard(OrangeFormedBlockHard orangeFormedBlockHard);
    @Binds @IntoSet BaseBlock bindGreyFormedBlock(GreyFormedBlock orangeFormedBlock);
    @Binds @IntoSet BaseBlock bindTealFormedBlock(TealFormedBlock tealFormedBlock);
    @Binds @IntoSet BaseBlock bindChallengeDoorBlock(ChallengeDoorBlock challengeDoorBlock);
    @Binds @IntoSet BaseBlock bindGatewayBlock(GatewayBlock gatewayBlock);
    @Binds @IntoSet BaseBlock bindTubeDarknessBlock(TubeDarknessBlock tubeDarknessBlock);
    @Binds @IntoSet BaseBlock bindTubeAirBlock(TubeAirBlock tubeAirBlock);
    @Binds @IntoSet BaseBlock bindChallengeExitBlock(ChallengeExitBlock challengeExitBlock);
    @Binds @IntoSet BaseBlock bindChallengeEntranceBlock(ChallengeEntranceBlock challengeEntranceBlock);
    @Binds @IntoSet BaseBlock bindTrophyBlock(TrophyBlock trophyBlock);

    @Binds @IntoSet BaseItem bindPinkSeedItem(PinkSeedItem pinkSeedItem);
    @Binds @IntoSet BaseItem bindPinkSeedItemRobust(PinkSeedItemRobust pinkSeedItemRobust);
    @Binds @IntoSet BaseItem bindBlueSeedItem(BlueSeedItem blueSeedItem);
    @Binds @IntoSet BaseItem bindBlueSeedItemRobust(BlueSeedItemRobust blueSeedItemRobust);
    @Binds @IntoSet BaseItem bindPurpleSeedItem(PurpleSeedItem purpleSeedItem);
    @Binds @IntoSet BaseItem bindPurpleSeedItemRobust(PurpleSeedItemRobust purpleSeedItemRobust);
    @Binds @IntoSet BaseItem bindYellowSeedItem(YellowSeedItem yellowSeedItem);
    @Binds @IntoSet BaseItem bindYellowSeedItemRobust(YellowSeedItemRobust yellowSeedItemRobust);
    @Binds @IntoSet BaseItem bindCyanSeedItem(CyanSeedItem cyanSeedItem);
    @Binds @IntoSet BaseItem bindCyanSeedItemRobust(CyanSeedItemRobust cyanSeedItemRobust);
    @Binds @IntoSet BaseItem bindGreenSeedItem(GreenSeedItem greenSeedItem);
    @Binds @IntoSet BaseItem bindGreenSeedItemRobust(GreenSeedItemRobust greenSeedItemRobust);
    @Binds @IntoSet BaseItem bindBrownSeedItem(BrownSeedItem brownSeedItem);
    @Binds @IntoSet BaseItem bindBrownSeedItemRobust(BrownSeedItemRobust brownSeedItemRobust);
    @Binds @IntoSet BaseItem bindTealSeedItem(TealSeedItem tealSeedItem);
    @Binds @IntoSet BaseItem bindTealSeedItemRobust(TealSeedItemRobust tealSeedItemRobust);
    @Binds @IntoSet BaseItem bindRedSeedItem(RedSeedItem redSeedItem);
    @Binds @IntoSet BaseItem bindRedSeedItemRobust(RedSeedItemRobust redSeedItemRobust);
    @Binds @IntoSet BaseItem bindOrangeSeedItem(OrangeSeedItem orangeSeedItem);
    @Binds @IntoSet BaseItem bindOrangeSeedItemRobust(OrangeSeedItemRobust orangeSeedItemRobust);
    @Binds @IntoSet BaseItem bindHeartItem(HeartItem heartItem);
    @Binds @IntoSet BaseItem bindKeyItem(KeyItem keyItem);
    @Binds @IntoSet BaseItem bindStarItem(StarItem starItem);
    @Binds @IntoSet BaseItem bindArrowItem(ArrowItem arrowItem);
    @Binds @IntoSet BaseItem bindChallengeStarItem(ChallengeStarItem challengeStarItem);

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
    @Binds @IntoSet TileEntityType<?> bindTubeTileType(TubeTileType type);
    @Binds @IntoSet TileEntityType<?> bindChallengeExitTileType(ChallengeExitTileType type);
    @Binds @IntoSet TileEntityType<?> bindTrophyTileType(TrophyTileType type);

    @Binds @IntoSet EntityType<?> bindPickupEntityType(EntityType<PickupEntity> pickupEntityEntityType);

    @Binds @IntoSet Set<SoundEvent> bindPinkSoundEvents(@Pink Set<SoundEvent> pinkSoundEvents);
    @Binds @IntoSet Set<SoundEvent> bindRedSoundEvents(@Red Set<SoundEvent> redSoundEvents);
    @Binds @IntoSet Set<SoundEvent> bindGreenSoundEvents(@Green Set<SoundEvent> greenSoundEvents);
    @Binds @IntoSet Set<SoundEvent> bindBlueSoundEvents(@Blue Set<SoundEvent> blueSoundEvents);
    @Binds @IntoSet Set<SoundEvent> bindPurpleSoundEvents(@Purple Set<SoundEvent> purpleSoundEvents);
    @Binds @IntoSet Set<SoundEvent> bindBrownSoundEvents(@Brown Set<SoundEvent> brownSoundEvents);
    @Binds @IntoSet Set<SoundEvent> bindYellowSoundEvents(@Yellow Set<SoundEvent> yellowSoundEvents);
    @Binds @IntoSet Set<SoundEvent> bindCyanSoundEvents(@Cyan Set<SoundEvent> cyanSoundEvents);
    @Binds @IntoSet Set<SoundEvent> bindTealSoundEvents(@Teal Set<SoundEvent> tealSoundEvents);
    @Binds @IntoSet Set<SoundEvent> bindExplodeSoundEvents(@Explode Set<SoundEvent> explodeSoundEvents);
    @Binds @IntoSet Set<SoundEvent> bindDarkSoundEvents(@Dark Set<SoundEvent> darkSoundEvents);
    @Binds @IntoSet Set<SoundEvent> bindSeedSoundEvents(@Seed Set<SoundEvent> seedSoundEvents);
    @Binds @IntoSet Set<SoundEvent> bindThunderSoundEvents(@Thunder Set<SoundEvent> thunderSoundEvents);
    @Binds @IntoSet Set<SoundEvent> bindMiscSoundEvents(@Misc Set<SoundEvent> miscSoundEvents);

    @Binds @IntoSet Biome bindFleetingBiome(FleetingBiome fleetingBiome);
    @Binds @IntoSet Biome bindFleetingSolidBiome(FleetingSolidBiome fleetingSolidBiome);
    @Binds @IntoSet Biome bindHubBiome(HubBiome hubBiome);

    @Binds @IntoSet ModDimension bindFleetingModDimension(FleetingDimensionTemplate fleetingDimensionTemplate);
    @Binds @IntoSet ModDimension bindFleetingSolidModDimension(FleetingSolidDimensionTemplate fleetingSolidDimensionTemplate);
    @Binds @IntoSet ModDimension bindHubModDimension(HubDimensionTemplate hubDimensionTemplate);

    @Binds @IntoSet Feature<?> bindDecayBlobFeature(DecayBlobFeature decayBlobFeature);
    @Binds @IntoSet Feature<?> bindSolidDecayBlobFeature(SolidDecayBlobFeature decayBlobFeature);
    @Binds @IntoSet Placement<?> bindDecayBlobPlacement(DecayBlobPlacement decayBlobPlacement);

    static @Provides Capability<GardenerCapability> provideGardenerCapability(UnfortunateForgeBlackMagic blackMagic) { return blackMagic.getGardenerCapability(); }
    static @Provides Capability<HubCapability> provideHubCapability(UnfortunateForgeBlackMagic blackMagic) { return blackMagic.getHubCapability(); }

    static @Provides @Singleton FMLJavaModLoadingContext provideFMLModLoadingContext() { return FMLJavaModLoadingContext.get(); }
    static @Provides @Singleton Logger provideLogger() { return LogManager.getLogger(); }

    static @Provides @ModId String provideModId() { return ModInformation.ID; }
    static @Provides @Name String provideName() { return ModInformation.NAME; }
    static @Provides @Version String provideAuthor() { return ModInformation.VERSION; }
    static @Provides @Author String provideVersion() { return ModInformation.AUTHOR; }

    static @Provides @Singleton EntityType<PickupEntity> providePickupEntityType(PickupEntityFactory pickupEntityFactory, Identifiers identifiers) {
        EntityType<PickupEntity> result = EntityType.Builder.<PickupEntity>create((type, world) -> pickupEntityFactory.create(world), EntityClassification.MISC)
                .disableSerialization().size(0.5f, 0.5f).setCustomClientFactory((packet, world) -> pickupEntityFactory.create(world)).build("pickup");
        result.setRegistryName(identifiers.get("pickup"));
        return result;
    }

    static @Provides @IntoSet @Singleton @Pink SoundEvent providePinkSound1(Identifiers identifiers) { return new SoundEvent(identifiers.get("grow_pink_1")); }
    static @Provides @IntoSet @Singleton @Pink SoundEvent providePinkSound2(Identifiers identifiers) { return new SoundEvent(identifiers.get("grow_pink_2")); }
    static @Provides @IntoSet @Singleton @Pink SoundEvent providePinkSound3(Identifiers identifiers) { return new SoundEvent(identifiers.get("grow_pink_3")); }
    static @Provides @IntoSet @Singleton @Pink SoundEvent providePinkSound4(Identifiers identifiers) { return new SoundEvent(identifiers.get("grow_pink_4")); }
    
    static @Provides @IntoSet @Singleton @Red SoundEvent provideRedSound1(Identifiers identifiers) { return new SoundEvent(identifiers.get("grow_red_1")); }
    static @Provides @IntoSet @Singleton @Red SoundEvent provideRedSound2(Identifiers identifiers) { return new SoundEvent(identifiers.get("grow_red_2")); }
    static @Provides @IntoSet @Singleton @Red SoundEvent provideRedSound3(Identifiers identifiers) { return new SoundEvent(identifiers.get("grow_red_3")); }
    static @Provides @IntoSet @Singleton @Red SoundEvent provideRedSound4(Identifiers identifiers) { return new SoundEvent(identifiers.get("grow_red_4")); }
    
    static @Provides @IntoSet @Singleton @Blue SoundEvent provideBlueSound1(Identifiers identifiers) { return new SoundEvent(identifiers.get("grow_blue_1")); }
    static @Provides @IntoSet @Singleton @Blue SoundEvent provideBlueSound2(Identifiers identifiers) { return new SoundEvent(identifiers.get("grow_blue_2")); }
    static @Provides @IntoSet @Singleton @Blue SoundEvent provideBlueSound3(Identifiers identifiers) { return new SoundEvent(identifiers.get("grow_blue_3")); }
    static @Provides @IntoSet @Singleton @Blue SoundEvent provideBlueSound4(Identifiers identifiers) { return new SoundEvent(identifiers.get("grow_blue_4")); }
    
    static @Provides @IntoSet @Singleton @Cyan SoundEvent provideCyanSound1(Identifiers identifiers) { return new SoundEvent(identifiers.get("grow_cyan_1")); }
    static @Provides @IntoSet @Singleton @Cyan SoundEvent provideCyanSound2(Identifiers identifiers) { return new SoundEvent(identifiers.get("grow_cyan_2")); }
    static @Provides @IntoSet @Singleton @Cyan SoundEvent provideCyanSound3(Identifiers identifiers) { return new SoundEvent(identifiers.get("grow_cyan_3")); }
    static @Provides @IntoSet @Singleton @Cyan SoundEvent provideCyanSound4(Identifiers identifiers) { return new SoundEvent(identifiers.get("grow_cyan_4")); }
    
    static @Provides @IntoSet @Singleton @Green SoundEvent provideGreenSound1(Identifiers identifiers) { return new SoundEvent(identifiers.get("grow_green_1")); }
    static @Provides @IntoSet @Singleton @Green SoundEvent provideGreenSound2(Identifiers identifiers) { return new SoundEvent(identifiers.get("grow_green_2")); }
    static @Provides @IntoSet @Singleton @Green SoundEvent provideGreenSound3(Identifiers identifiers) { return new SoundEvent(identifiers.get("grow_green_3")); }
    static @Provides @IntoSet @Singleton @Green SoundEvent provideGreenSound4(Identifiers identifiers) { return new SoundEvent(identifiers.get("grow_green_4")); }
    
    static @Provides @IntoSet @Singleton @Purple SoundEvent providePurpleSound1(Identifiers identifiers) { return new SoundEvent(identifiers.get("grow_purple_1")); }
    static @Provides @IntoSet @Singleton @Purple SoundEvent providePurpleSound2(Identifiers identifiers) { return new SoundEvent(identifiers.get("grow_purple_2")); }
    static @Provides @IntoSet @Singleton @Purple SoundEvent providePurpleSound3(Identifiers identifiers) { return new SoundEvent(identifiers.get("grow_purple_3")); }
    static @Provides @IntoSet @Singleton @Purple SoundEvent providePurpleSound4(Identifiers identifiers) { return new SoundEvent(identifiers.get("grow_purple_4")); }
    
    static @Provides @IntoSet @Singleton @Yellow SoundEvent provideYellowSound1(Identifiers identifiers) { return new SoundEvent(identifiers.get("grow_yellow_1")); }
    static @Provides @IntoSet @Singleton @Yellow SoundEvent provideYellowSound2(Identifiers identifiers) { return new SoundEvent(identifiers.get("grow_yellow_2")); }
    static @Provides @IntoSet @Singleton @Yellow SoundEvent provideYellowSound3(Identifiers identifiers) { return new SoundEvent(identifiers.get("grow_yellow_3")); }
    static @Provides @IntoSet @Singleton @Yellow SoundEvent provideYellowSound4(Identifiers identifiers) { return new SoundEvent(identifiers.get("grow_yellow_4")); }
    
    static @Provides @IntoSet @Singleton @Brown SoundEvent provideBrownSound1(Identifiers identifiers) { return new SoundEvent(identifiers.get("grow_brown_1")); }
    static @Provides @IntoSet @Singleton @Brown SoundEvent provideBrownSound2(Identifiers identifiers) { return new SoundEvent(identifiers.get("grow_brown_2")); }
    static @Provides @IntoSet @Singleton @Brown SoundEvent provideBrownSound3(Identifiers identifiers) { return new SoundEvent(identifiers.get("grow_brown_3")); }
    static @Provides @IntoSet @Singleton @Brown SoundEvent provideBrownSound4(Identifiers identifiers) { return new SoundEvent(identifiers.get("grow_brown_4")); }

    static @Provides @IntoSet @Singleton @Teal SoundEvent provideTealSound1(Identifiers identifiers) { return new SoundEvent(identifiers.get("grow_teal_1")); }
    static @Provides @IntoSet @Singleton @Teal SoundEvent provideTealSound2(Identifiers identifiers) { return new SoundEvent(identifiers.get("grow_teal_2")); }
    static @Provides @IntoSet @Singleton @Teal SoundEvent provideTealSound3(Identifiers identifiers) { return new SoundEvent(identifiers.get("grow_teal_3")); }
    static @Provides @IntoSet @Singleton @Teal SoundEvent provideTealSound4(Identifiers identifiers) { return new SoundEvent(identifiers.get("grow_teal_4")); }
    
    static @Provides @IntoSet @Singleton @Explode SoundEvent provideExplodeSound1(Identifiers identifiers) { return new SoundEvent(identifiers.get("explode_1")); }
    static @Provides @IntoSet @Singleton @Explode SoundEvent provideExplodeSound2(Identifiers identifiers) { return new SoundEvent(identifiers.get("explode_2")); }
    static @Provides @IntoSet @Singleton @Explode SoundEvent provideExplodeSound3(Identifiers identifiers) { return new SoundEvent(identifiers.get("explode_3")); }
    static @Provides @IntoSet @Singleton @Explode SoundEvent provideExplodeSound4(Identifiers identifiers) { return new SoundEvent(identifiers.get("explode_4")); }
    
    static @Provides @IntoSet @Singleton @Dark SoundEvent provideDarkSound1(Identifiers identifiers) { return new SoundEvent(identifiers.get("dark_1")); }
    static @Provides @IntoSet @Singleton @Dark SoundEvent provideDarkSound2(Identifiers identifiers) { return new SoundEvent(identifiers.get("dark_2")); }
    static @Provides @IntoSet @Singleton @Dark SoundEvent provideDarkSound3(Identifiers identifiers) { return new SoundEvent(identifiers.get("dark_3")); }
    static @Provides @IntoSet @Singleton @Dark SoundEvent provideDarkSound4(Identifiers identifiers) { return new SoundEvent(identifiers.get("dark_4")); }
    
    static @Provides @IntoSet @Singleton @Seed SoundEvent provideSeedSound1(Identifiers identifiers) { return new SoundEvent(identifiers.get("seed_1")); }
    static @Provides @IntoSet @Singleton @Seed SoundEvent provideSeedSound2(Identifiers identifiers) { return new SoundEvent(identifiers.get("seed_2")); }
    static @Provides @IntoSet @Singleton @Seed SoundEvent provideSeedSound3(Identifiers identifiers) { return new SoundEvent(identifiers.get("seed_3")); }
    static @Provides @IntoSet @Singleton @Seed SoundEvent provideSeedSound4(Identifiers identifiers) { return new SoundEvent(identifiers.get("seed_4")); }
    
    static @Provides @IntoSet @Singleton @Thunder SoundEvent provideThunderSound1(Identifiers identifiers) { return new SoundEvent(identifiers.get("thunder_1")); }
    static @Provides @IntoSet @Singleton @Thunder SoundEvent provideThunderSound2(Identifiers identifiers) { return new SoundEvent(identifiers.get("thunder_2")); }
    static @Provides @IntoSet @Singleton @Thunder SoundEvent provideThunderSound3(Identifiers identifiers) { return new SoundEvent(identifiers.get("thunder_3")); }

    static @Provides @Singleton @ExplodeDark SoundEvent provideExplodeDarkSound(Identifiers identifiers) { return new SoundEvent(identifiers.get("explode_dark")); }
    static @Provides @Singleton @Door SoundEvent provideDoorSound(Identifiers identifiers) { return new SoundEvent(identifiers.get("door")); }
    static @Provides @Singleton @Ticking SoundEvent provideTickingSound(Identifiers identifiers) { return new SoundEvent(identifiers.get("ticking")); }
    static @Provides @Singleton @Empty SoundEvent provideEmptySound(Identifiers identifiers) { return new SoundEvent(identifiers.get("empty")); }
    static @Provides @Singleton @DarkAir SoundEvent provideDarkAirSound(Identifiers identifiers) { return new SoundEvent(identifiers.get("dark_air")); }
    static @Provides @Singleton @Key SoundEvent provideKeySound(Identifiers identifiers) { return new SoundEvent(identifiers.get("key")); }
    static @Provides @Singleton @Bubble SoundEvent provideBubbleSound(Identifiers identifiers) { return new SoundEvent(identifiers.get("bubble")); }
    static @Provides @Singleton @Pop SoundEvent providePopSound(Identifiers identifiers) { return new SoundEvent(identifiers.get("pop")); }

    @Binds @IntoSet @Misc SoundEvent bindExplodeDarkSound(@ExplodeDark SoundEvent event);
    @Binds @IntoSet @Misc SoundEvent bindDoorSound(@Door SoundEvent event);
    @Binds @IntoSet @Misc SoundEvent bindTickingSound(@Ticking SoundEvent event);
    @Binds @IntoSet @Misc SoundEvent bindEmptySound(@Empty SoundEvent event);
    @Binds @IntoSet @Misc SoundEvent bindDarkAirSound(@DarkAir SoundEvent event);
    @Binds @IntoSet @Misc SoundEvent bindKeySound(@Key SoundEvent event);
    @Binds @IntoSet @Misc SoundEvent bindBubbleSound(@Bubble SoundEvent event);
    @Binds @IntoSet @Misc SoundEvent bindPopSound(@Pop SoundEvent event);

    // @formatter:on
}
