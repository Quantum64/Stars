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
import co.q64.stars.block.StarboundGatewayBlock;
import co.q64.stars.block.TealFormedBlock;
import co.q64.stars.block.TrophyBlock;
import co.q64.stars.block.TrophyBlock.TrophyVariant;
import co.q64.stars.block.TrophyBlockFactory;
import co.q64.stars.block.TubeAirBlock;
import co.q64.stars.block.TubeDarknessBlock;
import co.q64.stars.block.WhiteFormedBlock;
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
import co.q64.stars.dimension.overworld.feature.GatewayFeature;
import co.q64.stars.dimension.overworld.placement.GatewayPlacement;
import co.q64.stars.entity.PickupEntity;
import co.q64.stars.entity.PickupEntityFactory;
import co.q64.stars.item.ArrowItem;
import co.q64.stars.item.BaseItem;
import co.q64.stars.item.BasicBlockItemFactory;
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
import co.q64.stars.item.TrophyBlockItem;
import co.q64.stars.item.TrophyBlockItemFactory;
import co.q64.stars.item.WhiteSeedItem;
import co.q64.stars.item.WhiteSeedItem.WhiteSeedItemRobust;
import co.q64.stars.item.YellowSeedItem;
import co.q64.stars.item.YellowSeedItem.YellowSeedItemRobust;
import co.q64.stars.level.Level;
import co.q64.stars.level.levels.BlueLevel;
import co.q64.stars.level.levels.CyanLevel;
import co.q64.stars.level.levels.GreenLevel;
import co.q64.stars.level.levels.OrangeLevel;
import co.q64.stars.level.levels.PinkLevel;
import co.q64.stars.level.levels.PurpleLevel;
import co.q64.stars.level.levels.RedLevel;
import co.q64.stars.level.levels.TealLevel;
import co.q64.stars.level.levels.WhiteLevel;
import co.q64.stars.level.levels.YellowLevel;
import co.q64.stars.link.LinkInformation;
import co.q64.stars.link.jei.JEILinkInformation;
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
import co.q64.stars.qualifier.SoundQualifiers.Complete;
import co.q64.stars.qualifier.SoundQualifiers.Cyan;
import co.q64.stars.qualifier.SoundQualifiers.Dark;
import co.q64.stars.qualifier.SoundQualifiers.DarkAir;
import co.q64.stars.qualifier.SoundQualifiers.Door;
import co.q64.stars.qualifier.SoundQualifiers.Empty;
import co.q64.stars.qualifier.SoundQualifiers.Exit;
import co.q64.stars.qualifier.SoundQualifiers.Explode;
import co.q64.stars.qualifier.SoundQualifiers.ExplodeDark;
import co.q64.stars.qualifier.SoundQualifiers.Fall;
import co.q64.stars.qualifier.SoundQualifiers.Green;
import co.q64.stars.qualifier.SoundQualifiers.Key;
import co.q64.stars.qualifier.SoundQualifiers.Orange;
import co.q64.stars.qualifier.SoundQualifiers.Pink;
import co.q64.stars.qualifier.SoundQualifiers.Pop;
import co.q64.stars.qualifier.SoundQualifiers.Purple;
import co.q64.stars.qualifier.SoundQualifiers.Red;
import co.q64.stars.qualifier.SoundQualifiers.Seed;
import co.q64.stars.qualifier.SoundQualifiers.Teal;
import co.q64.stars.qualifier.SoundQualifiers.Thunder;
import co.q64.stars.qualifier.SoundQualifiers.Ticking;
import co.q64.stars.qualifier.SoundQualifiers.White;
import co.q64.stars.qualifier.SoundQualifiers.Wind;
import co.q64.stars.qualifier.SoundQualifiers.Yellow;
import co.q64.stars.tile.AirDecayEdgeTile;
import co.q64.stars.tile.ChallengeExitTile;
import co.q64.stars.tile.DarknessEdgeTile;
import co.q64.stars.tile.DecayEdgeTile;
import co.q64.stars.tile.DecayingTile;
import co.q64.stars.tile.DoorTile;
import co.q64.stars.tile.ForceRenderCullTile;
import co.q64.stars.tile.FormingTile;
import co.q64.stars.tile.SeedTile;
import co.q64.stars.tile.SpecialDecayEdgeTile;
import co.q64.stars.tile.TrophyTile;
import co.q64.stars.tile.TubeTile;
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
import co.q64.stars.type.forming.WhiteFormingBlockType;
import co.q64.stars.type.forming.YellowFormingBlockType;
import co.q64.stars.util.Identifiers;
import co.q64.stars.util.UnfortunateForgeBlackMagic;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ElementsIntoSet;
import dagger.multibindings.IntoSet;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
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

import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Module
public interface CommonModule {
    // @formatter:off

    @Binds GardenerCapability bindGardenerCapability(GardenerCapabilityImpl gardenerCapability);
    @Binds HubCapability bindHubCapability(HubCapabilityImpl hubCapability);

    @Binds @IntoSet LinkInformation bindJEILinkInformation(JEILinkInformation jeiLinkInformation);

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
    @Binds @IntoSet FormingBlockType bindWhiteFormingBlockType(WhiteFormingBlockType type);

    @Binds @IntoSet Level bindRedLevel(RedLevel redLevel);
    @Binds @IntoSet Level bindCyanLevel(CyanLevel cyanLevel);
    @Binds @IntoSet Level bindPurpleLevel(PurpleLevel purpleLevel);
    @Binds @IntoSet Level bindBlueLevel(BlueLevel redLevel);
    @Binds @IntoSet Level bindGreenLevel(GreenLevel redLevel);
    @Binds @IntoSet Level bindYellowLevel(YellowLevel redLevel);
    @Binds @IntoSet Level bindPinkLevel(PinkLevel redLevel);
    @Binds @IntoSet Level bindTealLevel(TealLevel redLevel);
    @Binds @IntoSet Level bindWhiteLevel(WhiteLevel redLevel);
    @Binds @IntoSet Level bindOrangeLevel(OrangeLevel redLevel);

    @Binds @ElementsIntoSet Set<Block> bindBlocks(Set<BaseBlock> blocks);
    @Binds @ElementsIntoSet Set<Item> bindItems(Set<BaseItem> items);
    @Binds @ElementsIntoSet Set<Item> bindBlockItems(Set<BlockItem> blockItems);

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
    @Binds @IntoSet BaseBlock bindWhiteFormedBlock(WhiteFormedBlock whiteFormedBlock);
    @Binds @IntoSet BaseBlock bindChallengeDoorBlock(ChallengeDoorBlock challengeDoorBlock);
    @Binds @IntoSet BaseBlock bindGatewayBlock(GatewayBlock gatewayBlock);
    @Binds @IntoSet BaseBlock bindTubeDarknessBlock(TubeDarknessBlock tubeDarknessBlock);
    @Binds @IntoSet BaseBlock bindTubeAirBlock(TubeAirBlock tubeAirBlock);
    @Binds @IntoSet BaseBlock bindChallengeExitBlock(ChallengeExitBlock challengeExitBlock);
    @Binds @IntoSet BaseBlock bindChallengeEntranceBlock(ChallengeEntranceBlock challengeEntranceBlock);
    @Binds @IntoSet BaseBlock bindStarboundGatewayBlock(StarboundGatewayBlock starboundGatewayBlock);
    @Binds @ElementsIntoSet Set<BaseBlock> bindTrophyBlock(Set<TrophyBlock> trophyBlocks);

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
    @Binds @IntoSet BaseItem bindWhiteSeedItem(WhiteSeedItem whiteSeedItem);
    @Binds @IntoSet BaseItem bindWhiteSeedItemRobust(WhiteSeedItemRobust whiteSeedItemRobust);
    @Binds @IntoSet BaseItem bindHeartItem(HeartItem heartItem);
    @Binds @IntoSet BaseItem bindKeyItem(KeyItem keyItem);
    @Binds @IntoSet BaseItem bindStarItem(StarItem starItem);
    @Binds @IntoSet BaseItem bindArrowItem(ArrowItem arrowItem);
    @Binds @IntoSet BaseItem bindChallengeStarItem(ChallengeStarItem challengeStarItem);

    @Binds @ElementsIntoSet Set<BlockItem> bindTrophyBlockItem(Set<TrophyBlockItem> trophyBlockItems);

    static @Provides @IntoSet @Singleton BlockItem provideStarboundGatewayBlockItem(StarboundGatewayBlock block, BasicBlockItemFactory factory) { return factory.create(block); }

    @Binds @IntoSet Listener bindRegistryListener(RegistryListener serverStartListener);
    @Binds @IntoSet Listener bindInitializationListener(InitializationListener initializationListener);
    @Binds @IntoSet Listener bindPlayerLoadListener(PlayerListener playerLoadListener);
    @Binds @IntoSet Listener bindWorldUnloadListener(WorldUnloadListener worldUnloadListener);

    @Binds @IntoSet TileEntityType<?> bindFormingTileType(TileEntityType<FormingTile> type);
    @Binds @IntoSet TileEntityType<?> bindDecayEdgeTileType(TileEntityType<DecayEdgeTile> type);
    @Binds @IntoSet TileEntityType<?> bindDecayingTileType(TileEntityType<DecayingTile> type);
    @Binds @IntoSet TileEntityType<?> bindAirDecayEdgeTileType(TileEntityType<AirDecayEdgeTile> type);
    @Binds @IntoSet TileEntityType<?> bindDarknessEdgeTileType(TileEntityType<DarknessEdgeTile> type);
    @Binds @IntoSet TileEntityType<?> bindForceRenderCullTileType(TileEntityType<ForceRenderCullTile> type);
    @Binds @IntoSet TileEntityType<?> bindSpecialDecayEdgeTileType(TileEntityType<SpecialDecayEdgeTile> type);
    @Binds @IntoSet TileEntityType<?> bindDoorTileType(TileEntityType<DoorTile> type);
    @Binds @IntoSet TileEntityType<?> bindSeedTileType(TileEntityType<SeedTile> type);
    @Binds @IntoSet TileEntityType<?> bindTubeTileType(TileEntityType<TubeTile> type);
    @Binds @IntoSet TileEntityType<?> bindChallengeExitTileType(TileEntityType<ChallengeExitTile> type);
    @Binds @IntoSet TileEntityType<?> bindTrophyTileType(TileEntityType<TrophyTile> type);

    @Binds @IntoSet EntityType<?> bindPickupEntityType(EntityType<PickupEntity> pickupEntityEntityType);

    @Binds @ElementsIntoSet Set<SoundEvent> bindPinkSoundEvents(@Pink Set<SoundEvent> pinkSoundEvents);
    @Binds @ElementsIntoSet Set<SoundEvent> bindRedSoundEvents(@Red Set<SoundEvent> redSoundEvents);
    @Binds @ElementsIntoSet Set<SoundEvent> bindGreenSoundEvents(@Green Set<SoundEvent> greenSoundEvents);
    @Binds @ElementsIntoSet Set<SoundEvent> bindBlueSoundEvents(@Blue Set<SoundEvent> blueSoundEvents);
    @Binds @ElementsIntoSet Set<SoundEvent> bindPurpleSoundEvents(@Purple Set<SoundEvent> purpleSoundEvents);
    @Binds @ElementsIntoSet Set<SoundEvent> bindBrownSoundEvents(@Brown Set<SoundEvent> brownSoundEvents);
    @Binds @ElementsIntoSet Set<SoundEvent> bindYellowSoundEvents(@Yellow Set<SoundEvent> yellowSoundEvents);
    @Binds @ElementsIntoSet Set<SoundEvent> bindCyanSoundEvents(@Cyan Set<SoundEvent> cyanSoundEvents);
    @Binds @ElementsIntoSet Set<SoundEvent> bindTealSoundEvents(@Teal Set<SoundEvent> tealSoundEvents);
    @Binds @ElementsIntoSet Set<SoundEvent> bindExplodeSoundEvents(@Explode Set<SoundEvent> explodeSoundEvents);
    @Binds @ElementsIntoSet Set<SoundEvent> bindDarkSoundEvents(@Dark Set<SoundEvent> darkSoundEvents);
    @Binds @ElementsIntoSet Set<SoundEvent> bindSeedSoundEvents(@Seed Set<SoundEvent> seedSoundEvents);
    @Binds @ElementsIntoSet Set<SoundEvent> bindFallSoundEvents(@Seed Set<SoundEvent> fallSoundEvents);
    @Binds @ElementsIntoSet Set<SoundEvent> bindThunderSoundEvents(@Thunder Set<SoundEvent> thunderSoundEvents);

    @Binds @IntoSet Biome bindFleetingBiome(FleetingBiome fleetingBiome);
    @Binds @IntoSet Biome bindFleetingSolidBiome(FleetingSolidBiome fleetingSolidBiome);
    @Binds @IntoSet Biome bindHubBiome(HubBiome hubBiome);

    @Binds @IntoSet ModDimension bindFleetingModDimension(FleetingDimensionTemplate fleetingDimensionTemplate);
    @Binds @IntoSet ModDimension bindFleetingSolidModDimension(FleetingSolidDimensionTemplate fleetingSolidDimensionTemplate);
    @Binds @IntoSet ModDimension bindHubModDimension(HubDimensionTemplate hubDimensionTemplate);

    @Binds @IntoSet Feature<?> bindDecayBlobFeature(DecayBlobFeature decayBlobFeature);
    @Binds @IntoSet Feature<?> bindSolidDecayBlobFeature(SolidDecayBlobFeature decayBlobFeature);
    @Binds @IntoSet Feature<?> bindGatewayFeature(GatewayFeature gatewayFeature);
    @Binds @IntoSet Placement<?> bindDecayBlobPlacement(DecayBlobPlacement decayBlobPlacement);
    @Binds @IntoSet Placement<?> bindGatewayPlacement(GatewayPlacement gatewayPlacement);

    static @Provides @ElementsIntoSet @Singleton Set<TrophyBlock> bindTrophyBlocks(TrophyBlockFactory factory) { return Arrays.stream(TrophyVariant.values()).map(factory::create).collect(Collectors.toSet()); }
    static @Provides @ElementsIntoSet @Singleton Set<TrophyBlockItem> bindTrophyItems(Set<TrophyBlock> blocks, TrophyBlockItemFactory factory) { return blocks.stream().map(factory::create).collect(Collectors.toSet()); }

    static @Provides Capability<GardenerCapability> provideGardenerCapability(UnfortunateForgeBlackMagic blackMagic) { return blackMagic.getGardenerCapability(); }
    static @Provides Capability<HubCapability> provideHubCapability(UnfortunateForgeBlackMagic blackMagic) { return blackMagic.getHubCapability(); }

    static @Provides @Singleton FMLJavaModLoadingContext provideFMLModLoadingContext() { return FMLJavaModLoadingContext.get(); }
    static @Provides @Singleton Logger provideLogger() { return LogManager.getLogger(); }

    static @Provides @ModId String provideModId() { return ModInformation.ID; }
    static @Provides @Name String provideName() { return ModInformation.NAME; }
    static @Provides @Version String provideAuthor() { return ModInformation.VERSION; }
    static @Provides @Author String provideVersion() { return ModInformation.AUTHOR; }

    static @Provides @Singleton EntityType<PickupEntity> providePickupEntityType(PickupEntityFactory pickupEntityFactory, Identifiers identifiers) { return (EntityType<PickupEntity>) EntityType.Builder.<PickupEntity>create((type, world) -> pickupEntityFactory.create(world), EntityClassification.MISC).disableSerialization().size(0.5f, 0.5f).setCustomClientFactory((packet, world) -> pickupEntityFactory.create(world)).build("pickup").setRegistryName(identifiers.get("pickup")); }

    @Binds TileEntityType<? extends SeedTile> bindSeedTileTypeErasure(TileEntityType<SeedTile> type);
    @Binds TileEntityType<? extends DecayEdgeTile> bindDecayEdgeTypeErasure(TileEntityType<DecayEdgeTile> type);

    static @Provides @Singleton TileEntityType<AirDecayEdgeTile> provideAirDecayEdgeTileType(Provider<AirDecayEdgeTile> provider, AirDecayEdgeBlock block, Identifiers identifiers) { return (TileEntityType<AirDecayEdgeTile>) TileEntityType.Builder.create(provider::get, block).build(null).setRegistryName(identifiers.get("air_decay_edge")); }
    static @Provides @Singleton TileEntityType<ChallengeExitTile> provideChallengeExitTileType(Provider<ChallengeExitTile> provider, ChallengeExitBlock block, Identifiers identifiers) { return (TileEntityType<ChallengeExitTile>) TileEntityType.Builder.create(provider::get, block).build(null).setRegistryName(identifiers.get("challenge_exit")); }
    static @Provides @Singleton TileEntityType<DarknessEdgeTile> provideDarknessEdgeTileType(Provider<DarknessEdgeTile> provider, DarknessEdgeBlock block, Identifiers identifiers) { return (TileEntityType<DarknessEdgeTile>) TileEntityType.Builder.create(provider::get, block).build(null).setRegistryName(identifiers.get("darkness_edge")); }
    static @Provides @Singleton TileEntityType<DecayEdgeTile> provideDecayEdgeTileType(Provider<DecayEdgeTile> provider, DecayEdgeBlock decayEdgeBlock, DecayEdgeBlockSolid decayEdgeBlockSolid, Identifiers identifiers) { return (TileEntityType<DecayEdgeTile>) TileEntityType.Builder.create(provider::get, decayEdgeBlock, decayEdgeBlockSolid).build(null).setRegistryName(identifiers.get("decay_edge")); }
    static @Provides @Singleton TileEntityType<DecayingTile> provideDecayingTileType(Provider<DecayingTile> provider, DecayingBlock decayingBlock, DecayingBlockHard decayingBlockHard, Identifiers identifiers) { return (TileEntityType<DecayingTile>) TileEntityType.Builder.create(provider::get, decayingBlock, decayingBlockHard).build(null).setRegistryName(identifiers.get("decaying")); }
    static @Provides @Singleton TileEntityType<DoorTile> provideDoorTileType(Provider<DoorTile> provider, DoorBlock doorBlock, ChallengeDoorBlock challengeDoorBlock, Identifiers identifiers) { return (TileEntityType<DoorTile>) TileEntityType.Builder.create(provider::get, doorBlock, challengeDoorBlock).build(null).setRegistryName(identifiers.get("door")); }
    static @Provides @Singleton TileEntityType<ForceRenderCullTile> provideForceRenderCullTileType(Provider<ForceRenderCullTile> provider, DarkAirBlock darkAirBlock, AirDecayBlock airDecayBlock, Identifiers identifiers) { return (TileEntityType<ForceRenderCullTile>) TileEntityType.Builder.create(provider::get, darkAirBlock, airDecayBlock).build(null).setRegistryName(identifiers.get("force_render_cull")); }
    static @Provides @Singleton TileEntityType<FormingTile> provideFormingTileType(Provider<FormingTile> provider, FormingBlock block, Identifiers identifiers) { return (TileEntityType<FormingTile>) TileEntityType.Builder.create(provider::get, block).build(null).setRegistryName(identifiers.get("forming")); }
    static @Provides @Singleton TileEntityType<SeedTile> provideSeedTileType(Provider<SeedTile> provider, SeedBlock seedBlock, SeedBlockHard seedBlockHard, Identifiers identifiers) { return (TileEntityType<SeedTile>) TileEntityType.Builder.create(provider::get, seedBlock, seedBlockHard).build(null).setRegistryName(identifiers.get("seed")); }
    static @Provides @Singleton TileEntityType<SpecialDecayEdgeTile> provideSpecialDecayEdgeTileType(Provider<SpecialDecayEdgeTile> provider, SpecialDecayEdgeBlock block, Identifiers identifiers) { return (TileEntityType<SpecialDecayEdgeTile>) TileEntityType.Builder.create(provider::get, block).build(null).setRegistryName(identifiers.get("special_decay_edge")); }
    static @Provides @Singleton TileEntityType<TrophyTile> provideTrophyTileType(Provider<TrophyTile> provider, Set<TrophyBlock> blocks, Identifiers identifiers) { return (TileEntityType<TrophyTile>) TileEntityType.Builder.create(provider::get, blocks.stream().toArray(Block[]::new)).build(null).setRegistryName(identifiers.get("trophy")); }
    static @Provides @Singleton TileEntityType<TubeTile> provideTubeTileType(Provider<TubeTile> provider, TubeDarknessBlock tubeDarknessBlock, TubeAirBlock tubeAirBlock, Identifiers identifiers) { return (TileEntityType<TubeTile>) TileEntityType.Builder.create(provider::get, tubeAirBlock, tubeDarknessBlock).build(null).setRegistryName(identifiers.get("tube")); }

    static @Provides @ElementsIntoSet @Singleton @Pink Set<SoundEvent> providePinkSounds(Identifiers identifiers) { return indexedSounds(identifiers, "grow_pink", 4); }
    static @Provides @ElementsIntoSet @Singleton @Red Set<SoundEvent> provideRedSounds(Identifiers identifiers) { return indexedSounds(identifiers, "grow_red", 4); }
    static @Provides @ElementsIntoSet @Singleton @Blue Set<SoundEvent> provideBlueSounds(Identifiers identifiers) { return indexedSounds(identifiers, "grow_blue", 4); }
    static @Provides @ElementsIntoSet @Singleton @Cyan Set<SoundEvent> provideCyanSounds(Identifiers identifiers) { return indexedSounds(identifiers, "grow_cyan", 4); }
    static @Provides @ElementsIntoSet @Singleton @Green Set<SoundEvent> provideGreenSounds(Identifiers identifiers) { return indexedSounds(identifiers, "grow_green", 4); }
    static @Provides @ElementsIntoSet @Singleton @Purple Set<SoundEvent> providePurpleSounds(Identifiers identifiers) { return indexedSounds(identifiers, "grow_purple", 4); }
    static @Provides @ElementsIntoSet @Singleton @Yellow Set<SoundEvent> provideYellowSounds(Identifiers identifiers) { return indexedSounds(identifiers, "grow_yellow", 4); }
    static @Provides @ElementsIntoSet @Singleton @Brown Set<SoundEvent> provideBrownSounds(Identifiers identifiers) { return indexedSounds(identifiers, "grow_brown", 4); }
    static @Provides @ElementsIntoSet @Singleton @Teal Set<SoundEvent> provideTealSounds(Identifiers identifiers) { return indexedSounds(identifiers, "grow_teal", 4); }
    static @Provides @ElementsIntoSet @Singleton @Orange Set<SoundEvent> provideOrangeSounds(Identifiers identifiers) { return indexedSounds(identifiers, "grow_orange", 4); }
    static @Provides @ElementsIntoSet @Singleton @White Set<SoundEvent> provideWhiteSounds(Identifiers identifiers) { return indexedSounds(identifiers, "grow_white", 4); }
    static @Provides @ElementsIntoSet @Singleton @Explode Set<SoundEvent> provideExplodeSounds(Identifiers identifiers) { return indexedSounds(identifiers, "explode", 4); }
    static @Provides @ElementsIntoSet @Singleton @Dark Set<SoundEvent> provideDarkSounds(Identifiers identifiers) { return indexedSounds(identifiers, "dark", 4); }
    static @Provides @ElementsIntoSet @Singleton @Seed Set<SoundEvent> provideSeedSounds(Identifiers identifiers) { return indexedSounds(identifiers, "seed", 4); }
    static @Provides @ElementsIntoSet @Singleton @Fall Set<SoundEvent> provideFallSounds(Identifiers identifiers) { return indexedSounds(identifiers, "fall", 4); }
    static @Provides @ElementsIntoSet @Singleton @Thunder Set<SoundEvent> provideThunderSounds(Identifiers identifiers) { return indexedSounds(identifiers, "thunder", 2); }

    static @Provides @Singleton @ExplodeDark SoundEvent provideExplodeDarkSound(Identifiers identifiers) { return new SoundEvent(identifiers.get("explode_dark")); }
    static @Provides @Singleton @Door SoundEvent provideDoorSound(Identifiers identifiers) { return new SoundEvent(identifiers.get("door")); }
    static @Provides @Singleton @Ticking SoundEvent provideTickingSound(Identifiers identifiers) { return new SoundEvent(identifiers.get("ticking")); }
    static @Provides @Singleton @Empty SoundEvent provideEmptySound(Identifiers identifiers) { return new SoundEvent(identifiers.get("empty")); }
    static @Provides @Singleton @DarkAir SoundEvent provideDarkAirSound(Identifiers identifiers) { return new SoundEvent(identifiers.get("dark_air")); }
    static @Provides @Singleton @Key SoundEvent provideKeySound(Identifiers identifiers) { return new SoundEvent(identifiers.get("key")); }
    static @Provides @Singleton @Bubble SoundEvent provideBubbleSound(Identifiers identifiers) { return new SoundEvent(identifiers.get("bubble")); }
    static @Provides @Singleton @Pop SoundEvent providePopSound(Identifiers identifiers) { return new SoundEvent(identifiers.get("pop")); }
    static @Provides @Singleton @Complete SoundEvent provideCompleteSound(Identifiers identifiers) { return new SoundEvent(identifiers.get("complete")); }
    static @Provides @Singleton @Exit SoundEvent provideExitSound(Identifiers identifiers) { return new SoundEvent(identifiers.get("exit")); }
    static @Provides @Singleton @Wind SoundEvent provideWindSound(Identifiers identifiers) { return new SoundEvent(identifiers.get("wind")); }

    @Binds @IntoSet SoundEvent bindExplodeDarkSound(@ExplodeDark SoundEvent event);
    @Binds @IntoSet SoundEvent bindDoorSound(@Door SoundEvent event);
    @Binds @IntoSet SoundEvent bindTickingSound(@Ticking SoundEvent event);
    @Binds @IntoSet SoundEvent bindEmptySound(@Empty SoundEvent event);
    @Binds @IntoSet SoundEvent bindDarkAirSound(@DarkAir SoundEvent event);
    @Binds @IntoSet SoundEvent bindKeySound(@Key SoundEvent event);
    @Binds @IntoSet SoundEvent bindBubbleSound(@Bubble SoundEvent event);
    @Binds @IntoSet SoundEvent bindPopSound(@Pop SoundEvent event);
    @Binds @IntoSet SoundEvent bindCompleteSound(@Complete SoundEvent event);
    @Binds @IntoSet SoundEvent bindExitSound(@Exit SoundEvent event);
    @Binds @IntoSet SoundEvent bindWindSound(@Wind SoundEvent event);

    // @formatter:on

    public static Set<SoundEvent> indexedSounds(Identifiers identifiers, String name, int count) {
        return sounds(identifiers, IntStream.rangeClosed(1, count).mapToObj(index -> name + "_" + index).toArray(String[]::new));
    }

    public static Set<SoundEvent> sounds(Identifiers identifiers, String... sounds) {
        return Arrays.stream(sounds).map(name -> new SoundEvent(identifiers.get(name))).collect(Collectors.toSet());
    }
}
