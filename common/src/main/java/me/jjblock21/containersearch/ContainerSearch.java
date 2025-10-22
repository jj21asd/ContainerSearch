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
        return MidnightConfig.getScreen(parent, MOD_ID);
    }
}
