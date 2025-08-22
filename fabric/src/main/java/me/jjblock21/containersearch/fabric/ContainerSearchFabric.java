package me.jjblock21.containersearch.fabric;

import me.jjblock21.containersearch.ContainerSearch;
import net.fabricmc.api.ClientModInitializer;

public class ContainerSearchFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ContainerSearch.init();
    }
}