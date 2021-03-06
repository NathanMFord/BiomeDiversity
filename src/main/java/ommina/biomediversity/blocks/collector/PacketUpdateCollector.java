package ommina.biomediversity.blocks.collector;

import net.minecraft.fluid.Fluid;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

public class PacketUpdateCollector {

    public BlockPos tilePos;
    public int storedEnergy;
    public int uniqueBiomeCount;
    public int releasePerTick;
    public float temperature;
    //public int duplicateBiomeCount;
    //public int unlistedBiomeCount;
    public int EnergyPerTick;

    FluidStack[] fluids = new FluidStack[TileEntityCollector.TANK_COUNT];

    public PacketUpdateCollector() {
    }

    public PacketUpdateCollector( TileEntity tile ) {
        this( (TileEntityCollector) tile );
    }

    public PacketUpdateCollector( TileEntityCollector tile ) {

        tilePos = tile.getPos();
        storedEnergy = tile.BATTERY.getEnergyStored();
        releasePerTick = tile.getRfReleasedPerTick();
        temperature = tile.getTemperature();
        uniqueBiomeCount = tile.getUniqueBiomeCount();

        for ( int n = 0; n < TileEntityCollector.TANK_COUNT; n++ )
            fluids[n] = tile.TANK.get( n ).getFluid();

    }

    public static PacketUpdateCollector fromBytes( PacketBuffer buf ) {

        PacketUpdateCollector packet = new PacketUpdateCollector();

        packet.tilePos = buf.readBlockPos();
        packet.storedEnergy = buf.readInt();
        packet.releasePerTick = buf.readInt();
        packet.temperature = buf.readFloat();
        packet.uniqueBiomeCount = buf.readInt();

        for ( int n = 0; n < TileEntityCollector.TANK_COUNT; n++ )
            if ( !buf.readBoolean() )
                packet.fluids[n] = FluidStack.EMPTY;
            else {
                Fluid fluid = ForgeRegistries.FLUIDS.getValue( buf.readResourceLocation() );
                if ( fluid == null )
                    packet.fluids[n] = FluidStack.EMPTY;
                else
                    packet.fluids[n] = new FluidStack( fluid, buf.readInt() );
            }

        return packet;

    }

    public void toBytes( PacketBuffer buf ) {

        buf.writeBlockPos( tilePos );
        buf.writeInt( storedEnergy );
        buf.writeInt( releasePerTick );
        buf.writeFloat( temperature );
        buf.writeInt( uniqueBiomeCount );

        for ( int n = 0; n < TileEntityCollector.TANK_COUNT; n++ )
            if ( fluids[n].isEmpty() )
                buf.writeBoolean( false );
            else {
                buf.writeBoolean( true );
                buf.writeResourceLocation( fluids[n].getFluid().getRegistryName() );
                buf.writeInt( fluids[n].getAmount() );
            }

    }

}
