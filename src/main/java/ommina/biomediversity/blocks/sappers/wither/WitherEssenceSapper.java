package ommina.biomediversity.blocks.sappers.wither;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.network.NetworkHooks;
import ommina.biomediversity.BiomeDiversity;
import ommina.biomediversity.blocks.BlockTileEntity;
import ommina.biomediversity.config.Constants;
import ommina.biomediversity.fluids.BdFluidTank;

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

    @Override
    public boolean onBlockActivated(BlockState blockState, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
        if (world.isRemote)
            return true;

        TileEntityWitherEssenceSapper tileEntity = (TileEntityWitherEssenceSapper) world.getTileEntity(pos);
        if (tileEntity == null)
            return super.onBlockActivated(blockState, world, pos, player, hand, rayTraceResult);

        ItemStack heldItem = player.getHeldItem(hand);
        if (!heldItem.isEmpty() && heldItem.getItem() == Items.CARROT) {
            debuggingCarrot(tileEntity);
            return true;
        }

        NetworkHooks.openGui((ServerPlayerEntity) player, tileEntity, tileEntity.getPos());
        return true;
    }

    // endregion Overrides

    private static void debuggingCarrot(TileEntityWitherEssenceSapper tile) {
        FluidStack fluidStack = tile.getTank().getFluid();
        BiomeDiversity.LOGGER.info(" Fluid: " + fluidStack.getFluid().getRegistryName().toString() + " (" + fluidStack.getAmount() + ")");
    }
}
