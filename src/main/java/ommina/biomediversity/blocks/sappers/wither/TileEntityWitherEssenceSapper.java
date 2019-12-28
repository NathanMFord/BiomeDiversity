package ommina.biomediversity.blocks.sappers.wither;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import ommina.biomediversity.blocks.ModTileEntities;
import ommina.biomediversity.fluids.BdFluidTank;

import javax.annotation.Nullable;

public class TileEntityWitherEssenceSapper extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    // Does capacity need to be grabbed from a config?
    private final BdFluidTank TANK = new BdFluidTank(0, 8000);

    public TileEntityWitherEssenceSapper() {
        super(ModTileEntities.WITHER_ESSENCE_SAPPER);

        TANK.setCanFill(false);
        TANK.setCanDrain(false);
    }

    // region Overrides

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(getType().getRegistryName().getPath());
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new WitherEssenceSapperContainer(i, world, pos, playerInventory, playerEntity);
    }

    @Override
    public void tick() {
        // TODO
    }

    // endregion Overrides
}
