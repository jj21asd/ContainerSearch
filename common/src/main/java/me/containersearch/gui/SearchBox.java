package me.containersearch.gui;

import me.containersearch.ContainerSearchMain;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;

public class SearchBox extends EditBox {
    public SearchBox(Font font, int i, int j, int k, int l, Component component) {
        super(font, i, j, k, l, component);
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
        super.renderWidget(guiGraphics, i, j, f);

        int right = getRectangle().right() - 8 - 2;
        int top = getY() + getRectangle().height() / 2 - 4;

        if (isFocused()) {
            guiGraphics.blit(ContainerSearchMain.SEARCH_ICON_LOCATION, right, top, 8, 0, 8, 8, 16, 8);
        }
        else {
            guiGraphics.blit(ContainerSearchMain.SEARCH_ICON_LOCATION, right, top, 0, 0, 8, 8, 16, 8);
        }
    }
}
