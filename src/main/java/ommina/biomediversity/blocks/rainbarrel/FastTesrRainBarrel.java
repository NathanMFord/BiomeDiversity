
package ommina.biomediversity.blocks.rainbarrel;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraftforge.client.model.animation.TileEntityRendererFast;
import net.minecraftforge.fluids.FluidStack;
import ommina.biomediversity.blocks.tile.RenderHelper;

import java.util.EnumSet;

public class FastTesrRainBarrel extends TileEntityRendererFast<TileEntityRainBarrel> {

    @Override
    public void renderTileEntityFast( TileEntityRainBarrel te, double x, double y, double z, float partialTicks, int destroyStage, BufferBuilder buffer ) {

        final float BASE = 0.1f;
        float HEIGHT = 13f / 16f;

        float low = 1f / 16f;
        float high = low * 15f;

        FluidStack fluid = null;//new FluidStack( Fluids.WATER, 1000 );  te.getTank().getFluid();

        if ( fluid != null && fluid.getAmount() > 0 ) {

            EnumSet<RenderHelper.Faces> faces = EnumSet.of( RenderHelper.Faces.TOP );

            float posY = BASE + (HEIGHT * ((float) fluid.getAmount() / (float) 1000));// Config.rainBarrelCapacity));

            RenderHelper.renderFluidCube( buffer, x, y, z, 1f / 16f, posY, high, fluid, faces );

        }

    }

}