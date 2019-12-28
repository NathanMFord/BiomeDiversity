package ommina.biomediversity.blocks.sappers.wither;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import ommina.biomediversity.BiomeDiversity;
import ommina.biomediversity.blocks.ModBlocks;
import ommina.biomediversity.gui.BaseContainerScreen;
import ommina.biomediversity.gui.controls.Tank;
import ommina.biomediversity.gui.controls.Text;
import ommina.biomediversity.util.Translator;

import java.awt.*;

@OnlyIn(Dist.CLIENT)
public class WitherEssenceSapperScreen extends BaseContainerScreen<WitherEssenceSapperContainer> {
    private static final Point TANK_INPUT = new Point(8, 15);

    public WitherEssenceSapperScreen(WitherEssenceSapperContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);

        TileEntityWitherEssenceSapper sapper = (TileEntityWitherEssenceSapper) container.getTileEntity();
        GUI = BiomeDiversity.getId("textures/gui/gui_blank.png");

        Text guiName = new Text(Translator.translateToLocal(ModBlocks.WITHER_ESSENCE_SAPPER.getTranslationKey()), Text.Justification.CENTRE, xSize);
        guiName.setPostion(TITLE_TEXT);
        controls.add(guiName);

        Text guiInventory = new Text(inv.getName().getString(), Text.Justification.LEFT, xSize);
        guiInventory.setPostion(new Point(8, ySize - 94));
        controls.add(guiInventory);

        Tank tank = new Tank(sapper.getTank());
        tank.setPostion(TANK_INPUT);
        controls.add(tank);
    }
}
