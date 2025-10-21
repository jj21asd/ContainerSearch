package me.jjblock21.containersearch;

import eu.midnightdust.lib.config.MidnightConfig;
import me.jjblock21.containersearch.extensions.EntryListWidgetExtension;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Identifier;
import org.slf4j.LoggerFactory;

public class ContainerSearch {
    public static final String MOD_ID = "container_search";
    public static final String MOD_NAME = "ContainerSearch";
    public static final Identifier WIDGET_SPRITES = Identifier.of(MOD_ID, "textures/widgets.png");
    public static final Identifier CONFIG_BACKGROUND = Identifier.of(Identifier.DEFAULT_NAMESPACE, "textures/block/tuff.png");

	public static void init() {
        try {
            MidnightConfig.init(MOD_ID, ConfigModel.class);
        } catch (Exception e) {
            LoggerFactory.getLogger(MOD_NAME).error("Failed to load config, consider deleting" +
                " {minecraft_folder}/config/{}.json to restore default values", MOD_ID);
            throw e;
        }
    }

    public static Screen getConfigScreen(Screen parent) {
        return new CustomConfigScreen(parent, MOD_ID);
    }

    private static class CustomConfigScreen extends MidnightConfig.MidnightConfigScreen {
        protected CustomConfigScreen(Screen parent, String modid) {
            super(parent, modid);
        }

        @Override
        public void init() {
            super.init();
            ((EntryListWidgetExtension)list).container_search$setBackground(CONFIG_BACKGROUND);
        }

        @Override
        public void renderBackgroundTexture(DrawContext context) {
            context.setShaderColor(0.25F, 0.25f, 0.25f, 1);
            context.drawTexture(CONFIG_BACKGROUND, 0, 0, 0, 0, 0, width, height, 32, 32);
            context.setShaderColor(1, 1, 1, 1);
        }
    }
}
