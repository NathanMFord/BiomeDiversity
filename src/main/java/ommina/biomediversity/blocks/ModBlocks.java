package ommina.biomediversity.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;
import ommina.biomediversity.BiomeDiversity;
import ommina.biomediversity.blocks.blocks.BlockNocifiedUndamaged;
import ommina.biomediversity.blocks.cluster.ClusterBlock;
import ommina.biomediversity.blocks.collector.Collector;
import ommina.biomediversity.blocks.crops.ColzaBlock;
import ommina.biomediversity.blocks.crops.FakePlantBlock;
import ommina.biomediversity.blocks.crops.PomegranateBlock;
import ommina.biomediversity.blocks.peltier.Peltier;
import ommina.biomediversity.blocks.plug.energy.PlugEnergy;
import ommina.biomediversity.blocks.plug.energy.PlugEnergyContainer;
import ommina.biomediversity.blocks.rainbarrel.RainBarrel;
import ommina.biomediversity.blocks.receiver.Receiver;
import ommina.biomediversity.blocks.receiver.ReceiverContainer;
import ommina.biomediversity.blocks.transmitter.Transmitter;
import ommina.biomediversity.blocks.transmitter.TransmitterContainer;


@ObjectHolder( BiomeDiversity.MODID )
@Mod.EventBusSubscriber( bus = Mod.EventBusSubscriber.Bus.MOD )
public class ModBlocks {

    // Generic Blocks
    @ObjectHolder( "orinocite_ore" ) public static Block ORINOCITE_ORE;
    @ObjectHolder( "orinocite_block" ) public static Block ORINOCITE_BLOCK;
    @ObjectHolder( "nocified_stone_undamaged" ) public static Block STONE_NOCIFIED_UNDAMAGED;
    @ObjectHolder( "nocified_stone_fractured" ) public static Block STONE_NOCIFIED_FRACTURED;

    // Cluster Blocks
    @ObjectHolder( "cluster_block_generic" ) public static ClusterBlock CLUSTER_BLOCK_GENERIC;
    @ObjectHolder( "cluster_block_tank" ) public static ClusterBlock CLUSTER_BLOCK_TANK;
    @ObjectHolder( "cluster_block_sturdy" ) public static ClusterBlock CLUSTER_BLOCK_STURDY;

    // Crops
    @ObjectHolder( "colza" ) public static Block COLZA;
    @ObjectHolder( "pomegranate" ) public static Block POMEGRANATE;
    @ObjectHolder( "world_colza" ) public static Block WORLD_COLZA;
    @ObjectHolder( "world_pomegranate" ) public static Block WORLD_POMEGRANATE;

    // Tile Entity Blocks
    @ObjectHolder( "collector" ) public static Collector COLLECTOR;
    @ObjectHolder( "peltier" ) public static Peltier PELTIER;
    @ObjectHolder( "rainbarrel" ) public static RainBarrel RAIN_BARREL;
    @ObjectHolder( "receiver" ) public static Receiver RECEIVER;
    @ObjectHolder( "transmitter" ) public static Transmitter TRANSMITTER;
    @ObjectHolder( "plug_energy" ) public static PlugEnergy PLUG_ENERGY;

    // Containers
    @ObjectHolder( "receiver" ) public static ContainerType<ReceiverContainer> RECEIVER_CONTAINER;
    @ObjectHolder( "plug_energy" ) public static ContainerType<PlugEnergyContainer> PLUG_ENERGY_CONTAINER;
    @ObjectHolder( "transmitter" ) public static ContainerType<TransmitterContainer> TRANSMITTER_CONTAINER;

    // Fluid Blocks  (Only those that we care about)
    @ObjectHolder( "mineralwater" ) public static FlowingFluidBlock MINERALWATER;
    @ObjectHolder( "junglewater" ) public static FlowingFluidBlock JUNGLEWATER;

    @SubscribeEvent
    public static void onContainerRegistry( final RegistryEvent.Register<ContainerType<?>> event ) {

        event.getRegistry().register( IForgeContainerType.create( ( windowId, inv, data ) -> {
            BlockPos pos = data.readBlockPos();
            return new ReceiverContainer( windowId, BiomeDiversity.PROXY.getClientWorld(), pos, inv, BiomeDiversity.PROXY.getClientPlayer() );
        } ).setRegistryName( "receiver" ) );

        event.getRegistry().register( IForgeContainerType.create( ( windowId, inv, data ) -> {
            BlockPos pos = data.readBlockPos();
            return new TransmitterContainer( windowId, BiomeDiversity.PROXY.getClientWorld(), pos, inv, BiomeDiversity.PROXY.getClientPlayer() );
        } ).setRegistryName( "transmitter" ) );

        event.getRegistry().register( IForgeContainerType.create( ( windowId, inv, data ) -> {
            BlockPos pos = data.readBlockPos();
            return new PlugEnergyContainer( windowId, BiomeDiversity.PROXY.getClientWorld(), pos, inv, BiomeDiversity.PROXY.getClientPlayer() );
        } ).setRegistryName( "plug_energy" ) );

    }

    @SubscribeEvent
    public static void register( final RegistryEvent.Register<Block> event ) {

        register( event, "orinocite_ore", new Block( Block.Properties.create( Material.ROCK ).hardnessAndResistance( 3.0f ) ) );
        register( event, "orinocite_block", new Block( Block.Properties.create( Material.ROCK ).hardnessAndResistance( 3.0f ) ) );
        register( event, "nocified_stone_undamaged", new BlockNocifiedUndamaged( Block.Properties.create( Material.ROCK ).hardnessAndResistance( 15f ) ) );
        register( event, "nocified_stone_fractured", new Block( Block.Properties.create( Material.ROCK ).hardnessAndResistance( 15f ) ) );

        register( event, "cluster_block_generic", new ClusterBlock( Block.Properties.create( Material.ROCK ).hardnessAndResistance( 2.8f ) ) );
        register( event, "cluster_block_tank", new ClusterBlock( Block.Properties.create( Material.ROCK ).hardnessAndResistance( 2.8f ) ) );
        register( event, "cluster_block_sturdy", new ClusterBlock( Block.Properties.create( Material.ROCK ).hardnessAndResistance( 3.2f ) ) );

        register( event, "colza", new ColzaBlock( Block.Properties.create( Material.PLANTS ).doesNotBlockMovement().tickRandomly().hardnessAndResistance( 0.05f ).sound( SoundType.CROP ) ) );
        register( event, "pomegranate", new PomegranateBlock( Block.Properties.create( Material.PLANTS ).doesNotBlockMovement().tickRandomly().hardnessAndResistance( 0.05f ).sound( SoundType.CROP ) ) );
        register( event, "world_colza", new FakePlantBlock( Block.Properties.create( Material.PLANTS ).doesNotBlockMovement().hardnessAndResistance( 0.05f ).sound( SoundType.PLANT ) ) );
        register( event, "world_pomegranate", new FakePlantBlock( Block.Properties.create( Material.PLANTS ).doesNotBlockMovement().hardnessAndResistance( 0.05f ).sound( SoundType.PLANT ) ) );

        register( event, "collector", new Collector() );
        register( event, "peltier", new Peltier() );
        register( event, "rainbarrel", new RainBarrel() );
        register( event, "receiver", new Receiver() );
        register( event, "transmitter", new Transmitter() );
        register( event, "plug_energy", new PlugEnergy() );

    }

    private static void register( final RegistryEvent.Register<Block> event, final String name, final Block block ) {

        block.setRegistryName( BiomeDiversity.getId( name ) );

        event.getRegistry().register( block );

    }

}
