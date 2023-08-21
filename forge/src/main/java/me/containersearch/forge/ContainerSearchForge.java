package me.containersearch.forge;

import me.containersearch.ContainerSearchMain;
import net.minecraftforge.fml.common.Mod;

@Mod(ContainerSearchMain.MOD_ID)
public class ContainerSearchForge {
    public ContainerSearchForge() {
        ContainerSearchMain.init();
    }
}