package me.jjblock21.containersearch;

import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.client.gui.screen.Screen;
import org.slf4j.LoggerFactory;

public class Entrypoint {
	public static void init() {
        try {
            MidnightConfig.init(Constants.MOD_ID, ModConfig.class);
        } catch (Exception exception) {
            LoggerFactory.getLogger(Constants.MOD_NAME).error("Failed to load config, consider deleting" +
                " {minecraft_folder}/config/{}.json to reset it to default values", Constants.MOD_ID);
            throw exception;
        }
    }

    public static Screen getConfigScreen(Screen parent) {
        return MidnightConfig.getScreen(parent, Constants.MOD_ID);
    }
}
