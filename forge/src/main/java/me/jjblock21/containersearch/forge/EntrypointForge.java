package me.jjblock21.containersearch.forge;

import me.jjblock21.containersearch.ContainerSearch;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ContainerSearch.MOD_ID)
public class EntrypointForge {
    public EntrypointForge(FMLJavaModLoadingContext context) {
        ContainerSearch.init();

        // register config screen
        context.registerExtensionPoint(
            ConfigScreenHandler.ConfigScreenFactory.class,
            () -> new ConfigScreenHandler.ConfigScreenFactory(
                (client, parent) -> ContainerSearch.getConfigScreen(parent)
            )
        );
    }
}