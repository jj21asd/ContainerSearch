package me.jjblock21.containersearch.mixins.gui;

import me.jjblock21.containersearch.extensions.EntryListWidgetExtension;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EntryListWidget.class)
public class EntryListWidgetMixin implements EntryListWidgetExtension {
    @Unique
    private Identifier container_search$background;

    @Unique
    @Override
    public void container_search$setBackground(Identifier texture) {
        container_search$background = texture;
    }

    @ModifyArg(method = "render",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIFFIIII)V"),
        index = 0)
    private Identifier getOptionsScreenBackground(Identifier texture) {
        boolean defaultBg = texture.getNamespace().equals(Screen.OPTIONS_BACKGROUND_TEXTURE.getNamespace()) &&
            texture.getPath().equals(Screen.OPTIONS_BACKGROUND_TEXTURE.getPath());

        if (defaultBg && container_search$background != null) {
            return container_search$background;
        }
        return texture;
    }
}
