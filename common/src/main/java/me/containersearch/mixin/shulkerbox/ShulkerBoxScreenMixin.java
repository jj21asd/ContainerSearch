package me.containersearch.mixin.shulkerbox;

import me.containersearch.gui.SearchBox;
import me.containersearch.mixin.AbstractContainerScreenAccessor;
import me.containersearch.mixin.AbstractContainerScreenMixin;
import me.containersearch.mixin.ScreenAccessor;
import me.containersearch.search.ContainerSearchHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.ShulkerBoxScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.ShulkerBoxMenu;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ShulkerBoxScreen.class)
public class ShulkerBoxScreenMixin extends AbstractContainerScreenMixin<ShulkerBoxMenu> {

    @Override
    protected void init() {
        super.init();
        AbstractContainerScreenAccessor accessor = (AbstractContainerScreenAccessor)this;
        ScreenAccessor accessor1 = (ScreenAccessor)this;
        ShulkerBoxMenuAccessor accessor2 = (ShulkerBoxMenuAccessor)getMenu();

        SearchBox searchBox = new SearchBox(Minecraft.getInstance().font, accessor1.getWidth() / 2 - 60,
                accessor.getTopPos() - 4 - 10, 120, 10, Component.empty());
        addRenderableWidget(searchBox);

        searchHandler = new ContainerSearchHandler(getMenu(), accessor2.getContainer(), searchBox);
        searchBox.setResponder(searchHandler::setSearchQuery);
    }

    // Dummy constructor
    protected ShulkerBoxScreenMixin(Component component) {
        super(component);
    }
}
