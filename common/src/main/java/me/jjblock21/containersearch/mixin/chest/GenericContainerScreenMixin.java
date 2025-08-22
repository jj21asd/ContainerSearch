package me.jjblock21.containersearch.mixin.chest;

import me.jjblock21.containersearch.gui.SearchBoxWidget;
import me.jjblock21.containersearch.mixin.HandledScreenAccessor;
import me.jjblock21.containersearch.mixin.HandledScreenMixin;
import me.jjblock21.containersearch.mixin.ScreenAccessor;
import me.jjblock21.containersearch.search.SearchComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GenericContainerScreen.class)
public class GenericContainerScreenMixin extends HandledScreenMixin<GenericContainerScreenHandler> {
    @Override
    protected void init() {
        super.init();
        HandledScreenAccessor accessor = (HandledScreenAccessor)this;
        ScreenAccessor accessor1 = (ScreenAccessor)this;

        SearchBoxWidget searchBox = new SearchBoxWidget(MinecraftClient.getInstance().textRenderer, accessor1.getWidth() / 2 - 60,
                accessor.getY() - 4 - 12, 120, 12, Text.empty());
        addDrawableChild(searchBox);

        containersearch$searchHandler = new SearchComponent(getScreenHandler(), getScreenHandler().getInventory(), searchBox);
        searchBox.setChangedListener(containersearch$searchHandler::setSearchQuery);
    }


    // Dummy constructor
    protected GenericContainerScreenMixin(Text component) {
        super(component);
    }
}
