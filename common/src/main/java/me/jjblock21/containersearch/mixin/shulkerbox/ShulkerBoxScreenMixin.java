package me.jjblock21.containersearch.mixin.shulkerbox;

import me.jjblock21.containersearch.gui.SearchBoxWidget;
import me.jjblock21.containersearch.mixin.HandledScreenAccessor;
import me.jjblock21.containersearch.mixin.HandledScreenMixin;
import me.jjblock21.containersearch.mixin.ScreenAccessor;
import me.jjblock21.containersearch.search.SearchComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.ShulkerBoxScreen;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ShulkerBoxScreen.class)
public class ShulkerBoxScreenMixin extends HandledScreenMixin<ShulkerBoxScreenHandler> {

    @Override
    protected void init() {
        super.init();
        HandledScreenAccessor accessor = (HandledScreenAccessor)this;
        ScreenAccessor accessor1 = (ScreenAccessor)this;
        ShulkerBoxScreenHandlerAccessor accessor2 = (ShulkerBoxScreenHandlerAccessor)getScreenHandler();

        SearchBoxWidget searchBox = new SearchBoxWidget(MinecraftClient.getInstance().textRenderer, accessor1.getWidth() / 2 - 60,
                accessor.getY() - 4 - 12, 120, 12, Text.empty());
        addDrawableChild(searchBox);

        containersearch$searchHandler = new SearchComponent(getScreenHandler(), accessor2.getInventory(), searchBox);
        searchBox.setChangedListener(containersearch$searchHandler::setSearchQuery);
    }

    // Dummy constructor
    protected ShulkerBoxScreenMixin(Text component) {
        super(component);
    }
}
