package ommina.biomediversity.world.feature;

import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;
import ommina.biomediversity.BiomeDiversity;
import ommina.biomediversity.world.structure.FluidWellStructure;

@ObjectHolder( BiomeDiversity.MODID )
@Mod.EventBusSubscriber( bus = Mod.EventBusSubscriber.Bus.MOD )
public class ModFeatures {

    @ObjectHolder( "jungle_pool" ) public static JunglePoolFeature JUNGLE_POOL = new JunglePoolFeature( "jungle_pool", NoFeatureConfig::deserialize );
    @ObjectHolder( "fluid_well" ) public static Structure<NoFeatureConfig> FLUID_WELL = new FluidWellStructure( "fluid_well", NoFeatureConfig::deserialize );

    @SubscribeEvent
    public static void register( final RegistryEvent.Register<Feature<?>> event ) {

        event.getRegistry().register( JUNGLE_POOL );
        event.getRegistry().register( FLUID_WELL );

        ModStructurePieceType.init();

    }

}

