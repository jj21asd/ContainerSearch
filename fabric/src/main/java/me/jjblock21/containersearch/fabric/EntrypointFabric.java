package me.jjblock21.containersearch.fabric;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.jjblock21.containersearch.Entrypoint;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.Screen;

public class EntrypointFabric implements ClientModInitializer, ModMenuApi {
    @Override
    public void onInitializeClient() {
        Entrypoint.init();
    }

    public ConfigScreenFactory<Screen> getModConfigScreenFactory() {
        return Entrypoint::getConfigScreen;
    }
}