package ommina.biomediversity.blocks.sappers.wither;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ommina.biomediversity.blocks.ModBlocks;
import ommina.biomediversity.gui.ModContainer;

public class WitherEssenceSapperContainer extends ModContainer {
    public WitherEssenceSapperContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
        super(ModBlocks.WITHER_ESSENCE_SAPPER_CONTAINER, windowId, world, pos, playerInventory, player);
    }

    // region Overrides

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(this.tileEntity.getWorld(), tileEntity.getPos()), playerEntity, ModBlocks.WITHER_ESSENCE_SAPPER);
    }

    // endregion Overrides
}
