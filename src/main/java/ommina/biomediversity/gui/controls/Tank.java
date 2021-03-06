package ommina.biomediversity.gui.controls;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;
import ommina.biomediversity.blocks.tile.RenderHelper;
import ommina.biomediversity.fluids.BdFluidTank;
import ommina.biomediversity.gui.Control;
import ommina.biomediversity.gui.UV;
import ommina.biomediversity.util.MathUtil;
import ommina.biomediversity.util.Translator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@OnlyIn( Dist.CLIENT )
public class Tank extends Control {

    public static UV FG = new UV( 0, 0, Sizes.TANK.width, Sizes.TANK.height );

    private final BdFluidTank tank;

    public Tank( BdFluidTank tank ) {
        super( Sizes.TANK );

        this.tank = tank;

    }

    //region Overrides
    @Override
    public void drawBackgroundLayer( int x, int y ) {

        if ( tank.getCapacity() <= 0 )
            return;

        FluidStack fluidStack = tank.getFluid();

        if ( fluidStack.isEmpty() || fluidStack.getFluid() == null || fluidStack.getAmount() <= 0 )
            return;

        GlStateManager.disableBlend();
        Minecraft.getInstance().getTextureManager().bindTexture( AtlasTexture.LOCATION_BLOCKS_TEXTURE );

        Fluid fluid = fluidStack.getFluid();

        TextureAtlasSprite fluidStillSprite = RenderHelper.getSprite( fluid.getAttributes().getStillTexture() );

        final int heightTexture = MathUtil.clamp( (int) ((float) fluidStack.getAmount() / (float) tank.getCapacity() * height), 1, height );
        final float[] rgba = RenderHelper.getRGBA( fluid.getAttributes().getColor() );

        GlStateManager.color4f( rgba[0], rgba[1], rgba[2], rgba[3] );

        Control.drawSprite( x + position.x, y + position.y + height - heightTexture, 0, width, heightTexture, fluidStillSprite ); //TODO: Needs to be tiled; it's all squishy in the GUI

        GlStateManager.color4f( 1f, 1f, 1f, 1f );

    }


    @Override
    public void drawForegroundLayer() {

        float f = 1f / 256f;

        Minecraft.getInstance().getTextureManager().bindTexture( OVERLAY_RESOURCE );

        Control.drawSprite( f, (float) position.x, (float) position.y, FG.minU, FG.minV, FG.maxU, FG.maxV );

    }

    @Override
    public List<String> getTooltip( boolean isShiftKeyDown ) {

        if ( tank.getCapacity() <= 0 )
            return null;

        FluidStack fluidStack = tank.getFluid();

        if ( fluidStack.isEmpty() || fluidStack.getFluid() == null || fluidStack.getAmount() <= 0 )
            return null;

        if ( !isShiftKeyDown )
            return Collections.singletonList( Translator.translateToLocal( fluidStack.getTranslationKey() ) );

        return Arrays.asList( Translator.translateToLocal( fluidStack.getTranslationKey() ), format.format( fluidStack.getAmount() ) + "mb" );

    }
//endregion Overrides

}
