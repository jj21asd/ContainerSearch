package me.jjblock21.containersearch.forge;

import me.jjblock21.containersearch.Constants;
import me.jjblock21.containersearch.Entrypoint;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
public class EntrypointForge {
    public EntrypointForge(FMLJavaModLoadingContext context) {
        Entrypoint.init();

        // register config screen
        context.registerExtensionPoint(
            ConfigScreenHandler.ConfigScreenFactory.class,
            () -> new ConfigScreenHandler.ConfigScreenFactory(
                (client, parent) -> Entrypoint.getConfigScreen(parent)
            )
        );
    }
}