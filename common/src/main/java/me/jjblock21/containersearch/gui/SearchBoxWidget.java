package me.jjblock21.containersearch.gui;

import me.jjblock21.containersearch.ContainerSearch;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class SearchBoxWidget extends TextFieldWidget {
    public SearchBoxWidget(TextRenderer font, int i, int j, int k, int l, Text component) {
        super(font, i, j, k, l, component);
    }

    private void drawSearchIcon(DrawContext context, boolean focused) {
        int u = focused ? 8 : 0;
        int x = getX() + getWidth() - 10;
        int y = getY() + getHeight() / 2 - 4;
        context.drawTexture(ContainerSearch.SEARCH_ICON, x, y, u, 0, 8, 8, 16, 8);
    }

    @Override
    public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderButton(context, mouseX, mouseY, delta);
        drawSearchIcon(context, isFocused());
    }
}
