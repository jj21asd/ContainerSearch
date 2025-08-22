package me.jjblock21.containersearch.forge;

import me.jjblock21.containersearch.ContainerSearch;
import net.minecraftforge.fml.common.Mod;

@Mod(ContainerSearch.MOD_ID)
public class ContainerSearchForge {
    public ContainerSearchForge() {
        ContainerSearch.init();
    }
}