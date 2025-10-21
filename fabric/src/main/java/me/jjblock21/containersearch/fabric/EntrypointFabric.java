package me.jjblock21.containersearch.fabric;

import me.jjblock21.containersearch.ContainerSearch;
import net.fabricmc.api.ClientModInitializer;

public class EntrypointFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ContainerSearch.init();
    }
}