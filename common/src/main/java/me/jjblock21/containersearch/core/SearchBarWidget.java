package me.jjblock21.containersearch.core;

import me.jjblock21.containersearch.ContainerSearch;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class SearchBarWidget extends TextFieldWidget {
    public SearchBarWidget(int x, int y, int width, int height, Text text) {
        super(MinecraftClient.getInstance().textRenderer, x, y, width, height, text);
    }

    @Override
    public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderButton(context, mouseX, mouseY, delta);
        renderSearchIcon(context, isFocused());
    }

    private void renderSearchIcon(DrawContext context, boolean focused) {
        int v = focused ? 8 : 0;
        int x = getX() + getWidth() - 10;
        int y = getY() + getHeight() / 2 - 4;
        context.drawTexture(ContainerSearch.WIDGET_SPRITES, x, y, 16, v, 8, 8, 32, 32);
    }
}
