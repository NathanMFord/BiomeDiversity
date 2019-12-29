package ommina.biomediversity.blocks.sappers.wither;

import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fluids.FluidStack;
import ommina.biomediversity.blocks.ModTileEntities;
import ommina.biomediversity.fluids.BdFluidTank;
import ommina.biomediversity.fluids.ModFluids;

import javax.annotation.Nullable;

import java.util.List;
import java.util.Random;

import static net.minecraftforge.fluids.capability.IFluidHandler.FluidAction.EXECUTE;

public class TileEntityWitherEssenceSapper extends TileEntity implements ITickableTileEntity, INamedContainerProvider {
    private static final int FLUID_PER_SAP = 10;

    // Does capacity need to be grabbed from a config?
    private final BdFluidTank tank = new BdFluidTank(0, 8000) {
        @Override
        protected void onFill(int amount) {
            // TODO Update GUI display since it doesn't do that automagically apparently. Or I'm doing something wrong.
            super.onContentsChanged();
        }
    };

    public TileEntityWitherEssenceSapper() {
        super(ModTileEntities.WITHER_ESSENCE_SAPPER);

        tank.setCanFill(false);
        tank.setCanDrain(false);
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
        if (world.isRemote)
            return;

        // Check for presence of wither or wither skeleton within a 7x7x7 area
        AxisAlignedBB area = new AxisAlignedBB(pos.getX() + 4, pos.getY() + 4, pos.getZ() + 4, pos.getX() - 3, pos.getY() - 3, pos.getZ() - 3);
        List<Entity> entities = world.getEntitiesWithinAABB(WitherEntity.class, area);
        entities.addAll(world.getEntitiesWithinAABB(WitherSkeletonEntity.class, area)); // Add wither skeletons

        if (entities.isEmpty())
            return;

        // Decrease health of one of the wither entities
        Random random = new Random(); // TODO Find out if this is the optimal way to get random numbers in a mod
        Entity entity = entities.get(random.nextInt(entities.size()));
        // TODO Should we do this every tick, or have a cooldown so you can't just fill the search area with mobs and get loads of essence?
        //      Should we drop items when they die? (I want to say no, so it prevents people using it as a mob farm, unless we want that. Config option?)
        //      Should we damage regardless of if there's room in tank? (Config option?)
        //      Should we deal different damage and give different amounts for wither and wither skeleton?
        //          As it is, withers give 5980mb, skeletons give 200mb.
        if (!entity.attackEntityFrom(DamageSource.MAGIC, 1))
            return; // Don't continue if we didn't do damage to the entity

        // TODO Maybe use another fluid (water probably?) to convert the raw essence into a usable fluid essence

        // TODO Make the whole process use some power & add the control to the GUI

        int fluidAdded = tank.add(new FluidStack(ModFluids.WITHERESSENCE, FLUID_PER_SAP), EXECUTE);
        // TODO Do I need to do anything special when there is fluid successfully added, or if it adds the correct amount?
        //  Probably do if I'm adding any sort of delay, based on what the rain barrel does.
        if (fluidAdded == FLUID_PER_SAP) {
            markDirty();
        } else if (fluidAdded > 0) {
            markDirty();
        }
    }

    // endregion Overrides

    public BdFluidTank getTank() {
        return tank;
    }
}
