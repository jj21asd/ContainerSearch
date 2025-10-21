package me.jjblock21.containersearch.fabric;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.jjblock21.containersearch.ContainerSearch;
import net.minecraft.client.gui.screen.Screen;

public class ModMenuImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<Screen> getModConfigScreenFactory() {
        return ContainerSearch::getConfigScreen;
    }
}
