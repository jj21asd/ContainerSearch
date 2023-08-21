package me.containersearch.mixin.chest;

import me.containersearch.gui.SearchBox;
import me.containersearch.mixin.AbstractContainerScreenAccessor;
import me.containersearch.mixin.AbstractContainerScreenMixin;
import me.containersearch.mixin.ScreenAccessor;
import me.containersearch.search.ContainerSearchHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.ChestMenu;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ContainerScreen.class)
public class ContainerScreenMixin extends AbstractContainerScreenMixin<ChestMenu> {

    @Override
    protected void init() {
        super.init();
        AbstractContainerScreenAccessor accessor = (AbstractContainerScreenAccessor)this;
        ScreenAccessor accessor1 = (ScreenAccessor)this;

        SearchBox searchBox = new SearchBox(Minecraft.getInstance().font, accessor1.getWidth() / 2 - 60,
                accessor.getTopPos() - 4 - 10, 120, 10, Component.empty());
        addRenderableWidget(searchBox);

        searchHandler = new ContainerSearchHandler(getMenu(), getMenu().getContainer(), searchBox);
        searchBox.setResponder(searchHandler::setSearchQuery);
    }


    // Dummy constructor
    protected ContainerScreenMixin(Component component) {
        super(component);
    }
}
