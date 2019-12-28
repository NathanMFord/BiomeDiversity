package ommina.biomediversity.blocks.sappers.wither;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;
import ommina.biomediversity.blocks.BlockTileEntity;
import ommina.biomediversity.config.Constants;

public class WitherEssenceSapper extends BlockTileEntity<TileEntityWitherEssenceSapper> {
    public WitherEssenceSapper() {
        super(Block.Properties.create(Material.ROCK).harvestLevel(2).harvestTool(ToolType.PICKAXE).hardnessAndResistance(Constants.DEFAULT_TILE_ENTITY_HARDNESS));
    }

    // region Overrides

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntityWitherEssenceSapper();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    // endregion Overrides
}
