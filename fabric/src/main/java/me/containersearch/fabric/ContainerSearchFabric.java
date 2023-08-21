package me.containersearch.fabric;

import me.containersearch.ContainerSearchMain;
import net.fabricmc.api.ClientModInitializer;

public class ContainerSearchFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ContainerSearchMain.init();
    }
}