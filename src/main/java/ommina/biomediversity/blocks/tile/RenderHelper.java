
package ommina.biomediversity.blocks.tile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.util.EnumSet;

public class RenderHelper {

    public enum Faces {
        TOP,
        BOTTOM,
        NORTH,
        SOUTH,
        WEST,
        EAST
    }

    ;

/*

    public static void renderPowerBar( BufferBuilder buffer, TextureAtlasSprite texturePower, float height, float barCount, float originX, float originZ, float lengthX, float lengthZ ) {

        float w = texturePower.getIconWidth() / (texturePower.getMaxU() - texturePower.getMinU());

        final float BAR_WIDTH = 1.0f / w;

        float pbMinU = texturePower.getMinU() + barCount * BAR_WIDTH;
        float pbMaxU = texturePower.getMinU() + barCount * BAR_WIDTH + BAR_WIDTH;

        buffer.pos( originX, 0, originZ ).color( 1f, 1f, 1f, 1f ).tex( pbMinU, texturePower.getMaxV() ).lightmap( 0, 176 ).endVertex();
        buffer.pos( originX, height, originZ ).color( 1f, 1f, 1f, 1f ).tex( pbMinU, texturePower.getMinV() ).lightmap( 0, 176 ).endVertex();
        buffer.pos( originX + lengthX, height, originZ + lengthZ ).color( 1f, 1f, 1f, 1f ).tex( pbMaxU, texturePower.getMinV() ).lightmap( 0, 176 ).endVertex();
        buffer.pos( originX + lengthX, 0, originZ + lengthZ ).color( 1f, 1f, 1f, 1f ).tex( pbMaxU, texturePower.getMaxV() ).lightmap( 0, 176 ).endVertex();

    }

*/

    public static void renderFluidCube( BufferBuilder buffer, double x, double y, double z, float w, float h, float l, FluidStack fluid, EnumSet<Faces> faces ) {

        final float BASE = 0.1f;
        final float HEIGHT = 13f / 16f;

        final float low = 1f / 16f;
        final float high = low * 15f;

        buffer.setTranslation( x, y, z );

        TextureAtlasSprite still = ((AtlasTexture) Minecraft.getInstance().getTextureManager().getTexture( new ResourceLocation( "minecraft:water_still" ) )).getAtlasSprite( "minecraft:water_still" );

        //TextureAtlasSprite still = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite( fluid.getFluid().getStill().toString() );

        int color = fluid.getFluid().getColor();

        float[] rgba = new float[]{ ((color >> 16) & 0xFF) / 255f, ((color >> 8) & 0xFF) / 255f, ((color >> 0) & 0xFF) / 255f, ((color >> 24) & 0xFF) / 255f };

        double posY = h;//BASE + (HEIGHT * ((float) fluid.amount / (float) Config.rainBarrelCapacity));

        if ( faces.contains( Faces.TOP ) ) {
            buffer.pos( low, posY, low ).color( rgba[0], rgba[1], rgba[2], rgba[3] ).tex( still.getMinU(), still.getMinV() ).lightmap( 0, 176 ).endVertex();
            buffer.pos( low, posY, high ).color( rgba[0], rgba[1], rgba[2], rgba[3] ).tex( still.getMaxU(), still.getMinV() ).lightmap( 0, 176 ).endVertex();
            buffer.pos( high, posY, high ).color( rgba[0], rgba[1], rgba[2], rgba[3] ).tex( still.getMaxU(), still.getMaxV() ).lightmap( 0, 176 ).endVertex();
            buffer.pos( high, posY, low ).color( rgba[0], rgba[1], rgba[2], rgba[3] ).tex( still.getMinU(), still.getMaxV() ).lightmap( 0, 176 ).endVertex();
        }

    }

    public static void renderCube( BufferBuilder buffer, double x, double y, double z, float w, float h, float l, String texture, EnumSet<Faces> faces ) {

        final float low = w;
        final float high = w + l;

        buffer.setTranslation( x, y, z );

        TextureAtlasSprite text = ((AtlasTexture) Minecraft.getInstance().getTextureManager().getTexture( new ResourceLocation( "minecraft:water_still" ) )).getAtlasSprite( "minecraft:water_still" );


        //TextureAtlasSprite text = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite( texture );

        double posY = h;

        if ( faces.contains( Faces.TOP ) ) {
            buffer.pos( low, posY, low ).color( 1f, 1f, 1f, 1f ).tex( text.getMinU(), text.getMinV() ).lightmap( 0, 176 ).endVertex();
            buffer.pos( low, posY, high ).color( 1f, 1f, 1f, 1f ).tex( text.getMaxU(), text.getMinV() ).lightmap( 0, 176 ).endVertex();
            buffer.pos( high, posY, high ).color( 1f, 1f, 1f, 1f ).tex( text.getMaxU(), text.getMaxV() ).lightmap( 0, 176 ).endVertex();
            buffer.pos( high, posY, low ).color( 1f, 1f, 1f, 1f ).tex( text.getMinU(), text.getMaxV() ).lightmap( 0, 176 ).endVertex();
        }

    }

}
